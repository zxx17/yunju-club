package com.zsyj.circle.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.zsyj.circle.api.common.PageResult;
import com.zsyj.circle.api.common.Result;
import com.zsyj.circle.api.req.GetShareMomentReq;
import com.zsyj.circle.api.req.RemoveShareMomentReq;
import com.zsyj.circle.api.req.SaveMomentCircleReq;
import com.zsyj.circle.api.vo.ShareMomentVO;
import com.zsyj.circle.server.entity.po.ShareCircle;
import com.zsyj.circle.server.sensitive.WordFilter;
import com.zsyj.circle.server.service.ShareCircleService;
import com.zsyj.circle.server.service.ShareMomentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 动态信息 前端控制器
 * </p>
 *
 * @author Xinxuan Zhuo
 * @since 2024/05/16
 */
@Slf4j
@RestController
@RequestMapping("/circle/share/moment")
public class ShareMomentController {

    @Resource
    private ShareMomentService shareMomentService;
    @Resource
    private ShareCircleService shareCircleService;
    @Resource
    private WordFilter wordFilter;

    /**
     * 发布内容
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody SaveMomentCircleReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("发布内容入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleId()), "圈子ID不能为空！");
            ShareCircle data = shareCircleService.getById(req.getCircleId());
            Preconditions.checkArgument((Objects.nonNull(data) && data.getParentId() != -1), "非法圈子ID！");
            Preconditions.checkArgument((Objects.nonNull(req.getContent()) || Objects.nonNull(req.getPicUrlList())), "不能为空！");
            wordFilter.check(req.getContent());
            Boolean result = shareMomentService.saveMoment(req);
            if (log.isInfoEnabled()) {
                log.info("发布内容{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("发布内容异常！错误原因{}", e.getMessage(), e);
            return Result.fail("发布内容异常！");
        }
    }


    /**
     * 分页查询内容
     */
    @PostMapping(value = "/getMoments")
    public Result<PageResult<ShareMomentVO>> getMoments(@RequestBody GetShareMomentReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("内容入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            PageResult<ShareMomentVO> result = shareMomentService.getMoments(req);
            if (log.isInfoEnabled()) {
                log.info("内容出参{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("内容异常！错误原因{}", e.getMessage(), e);
            return Result.fail("内容异常！");
        }
    }


    /**
     * 删除内容
     */
    @PostMapping(value = "/remove")
    public Result<Boolean> remove(@RequestBody RemoveShareMomentReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("删除内容入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "内容ID不能为空！");
            Boolean result = shareMomentService.removeMoment(req);
            if (log.isInfoEnabled()) {
                log.info("删除内容{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除内容异常！错误原因{}", e.getMessage(), e);
            return Result.fail("删除内容异常！");
        }
    }

}
