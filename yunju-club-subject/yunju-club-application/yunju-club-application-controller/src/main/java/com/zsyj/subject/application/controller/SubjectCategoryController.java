package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectCategoryDTOConverter;
import com.zsyj.subject.application.dto.SubjectCategoryDTO;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.service.ISubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.zsyj.subject.common.enums.NotifyEnum.*;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/26
 * <p>
 * 刷题分类controller
 * </p>
 */

@Slf4j
@RestController
@RequestMapping("/subject/category")
public class SubjectCategoryController {

    @Resource
    private ISubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 分类大类
     */
    private static final Integer CATEGORY_TYPE_BIG = 1;

    /**
     * 分类小类
     */
    private static final Integer CATEGORY_TYPE_COMMON = 2;


    /**
     * 新增分类
     *
     * @param subjectCategoryDTO dto
     * @return boolean
     */
    @PostMapping("/add")
    public Result<Object> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            // tips: log.isInfoEnabled(){doPrint} 高并发场景下的日志优化
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto{}", JSONObject.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级Id不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryName(), "分类名称不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error{}", e.getMessage());
            return Result.fail(INSERT_FAIL + e.getMessage());
        }
    }


    /**
     * 查询分类大类
     *
     * @return json
     */
    @GetMapping("/queryPrimaryCategory")
    public Result<Object> queryPrimaryCategory() {
        try {
            SubjectCategoryBO subjectCategoryBO = new SubjectCategoryBO();
            subjectCategoryBO.setCategoryType(CATEGORY_TYPE_BIG);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBOToSubjectCategoryDTO(subjectCategoryBOList);
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryPrimaryCategory.subjectCategoryDTOList:{}", subjectCategoryDTOList);
            }
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error{}", e.getMessage());
            return Result.fail("查询失败");
        }
    }


    /**
     * 查询分类小类
     *
     * @param subjectCategoryDTO dto
     * @return json
     */
    @PostMapping("/queryCategoryByPrimary")
    public Result<Object> queryCategoryByPrimary(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryByPrimary.subjectCategoryDTO:{}", subjectCategoryDTO);
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE
                    .convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE
                    .convertBOToSubjectCategoryDTO(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryCategoryByPrimary.error{}", e.getMessage());
            return Result.fail(QUERY_FAIL.getNotify() + e.getMessage());
        }
    }

    /**
     * 更新分类
     *
     * @param subjectCategoryDTO dto
     * @return boolean
     */
    @PostMapping("/update")
    public Result<Object> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.subjectCategoryDTO:{}", subjectCategoryDTO);
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            Boolean isUpdate = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.ok(isUpdate);
        } catch (Exception e) {
            log.error("SubjectCategoryController.update.error{}", e.getMessage());
            return Result.fail(UPDATE_FAIL.getNotify() + e.getMessage());
        }
    }

    /**
     * 删除分类
     *
     * @param subjectCategoryDTO dto
     * @return boolean
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.delete.subjectCategoryDTO:{}", subjectCategoryDTO);
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            Boolean isDelete = subjectCategoryDomainService.delete(subjectCategoryBO);
            return Result.ok(isDelete);
        } catch (Exception e) {
            log.error("SubjectCategoryController.delete.error{}", e.getMessage());
            return Result.fail(UPDATE_FAIL.getNotify() + e.getMessage());
        }
    }


}
