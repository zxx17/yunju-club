package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectCategoryDTOConverter;
import com.zsyj.subject.application.dto.SubjectCategoryDTO;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.service.ISubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/add")
    public Result<Object> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            // tips: log.isInfoEnabled(){doPrint} 高并发场景下的日志优化
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto{}", JSONObject.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryName(), "分类名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级Id不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryName(), "分类名称不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error{}", e.getMessage());
            return Result.fail(e.getMessage());
        }
    }


    @GetMapping("/queryPrimaryCategory")
    public Result<Object> queryPrimaryCategory() {
        try {
            SubjectCategoryBO subjectCategoryBO = new SubjectCategoryBO();
            subjectCategoryBO.setParentId(0);
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
            return Result.fail("查询失败: " + e.getMessage());
        }
    }

//    @PostMapping("/update")
}