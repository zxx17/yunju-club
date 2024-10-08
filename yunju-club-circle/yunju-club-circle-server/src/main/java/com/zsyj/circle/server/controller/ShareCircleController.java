package com.zsyj.circle.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.zsyj.circle.api.common.Result;
import com.zsyj.circle.api.req.RemoveShareCircleReq;
import com.zsyj.circle.api.req.SaveShareCircleReq;
import com.zsyj.circle.api.req.UpdateShareCircleReq;
import com.zsyj.circle.api.vo.ShareCircleVO;
import com.zsyj.circle.server.entity.po.ShareCircle;
import com.zsyj.circle.server.service.ShareCircleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 社区圈子信息.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/8
 */
@Slf4j
@RestController
@RequestMapping("/circle/share/circle")
public class ShareCircleController {

    @Resource
    private ShareCircleService shareCircleService;


    /**
     * 圈子查询
     */
    @GetMapping(value = "/list")
    public Result<List<ShareCircleVO>> listResult() {
        try {
            List<ShareCircleVO> result = shareCircleService.listResult();
            if (log.isInfoEnabled()) {
                log.info("圈子查询出参{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (Exception e) {
            log.error("圈子查询异常！错误原因{}", e.getMessage(), e);
            return Result.fail("圈子查询异常！");
        }
    }


    /**
     * 新增圈子
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody SaveShareCircleReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("新增圈子入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleName()), "圈子名称不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getIcon()), "圈子图标不能为空！");
            if (req.getParentId() != -1) {
                ShareCircle circle = shareCircleService.getById(req.getParentId());
                Preconditions.checkArgument(Objects.nonNull(circle), "父级不存在！");
            }
            Boolean result = shareCircleService.saveCircle(req);
            if (log.isInfoEnabled()) {
                log.info("新增圈子{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("新增圈子异常！错误原因{}", e.getMessage(), e);
            return Result.fail("新增圈子异常！");
        }
    }

    /**
     * 修改圈子
     */
    @PostMapping(value = "/update")
    public Result<Boolean> update(@RequestBody UpdateShareCircleReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("修改圈子入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleName()), "圈子名称不能为空！");
            if (Objects.nonNull(req.getParentId()) && req.getParentId() != -1) {
                ShareCircle circle = shareCircleService.getById(req.getParentId());
                Preconditions.checkArgument(Objects.nonNull(circle), "父级不存在！");
            }
            Boolean result = shareCircleService.updateCircle(req);
            if (log.isInfoEnabled()) {
                log.info("修改圈子{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("修改圈子异常！错误原因{}", e.getMessage(), e);
            return Result.fail("修改圈子异常！");
        }
    }


    /**
     * 删除圈子
     */
    @PostMapping(value = "/remove")
    public Result<Boolean> remove(@RequestBody RemoveShareCircleReq req) {
        try {
            if (log.isInfoEnabled()) {
                log.info("删除圈子入参{}", JSON.toJSONString(req));
            }
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "圈子ID！");
            Boolean result = shareCircleService.removeCircle(req);
            if (log.isInfoEnabled()) {
                log.info("删除圈子{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除圈子异常！错误原因{}", e.getMessage(), e);
            return Result.fail("删除圈子异常！");
        }
    }





}
