package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectCategoryDTOConverter;
import com.zsyj.subject.application.convert.SubjectLabelDTOConverter;
import com.zsyj.subject.application.dto.SubjectCategoryDTO;
import com.zsyj.subject.application.dto.SubjectLabelDTO;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.service.ISubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
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
            log.error("SubjectCategoryController.add.error", e);
            return Result.fail(INSERT_FAIL.getNotify() + e.getMessage());
        }
    }


    /**
     * 查询分类大类
     *
     * @return json result List<SubjectCategoryDTO>
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<Object> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            subjectCategoryBO.setCategoryType(CATEGORY_TYPE_BIG);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBOToSubjectCategoryDTOList(subjectCategoryBOList);
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryPrimaryCategory.subjectCategoryDTOList:{}", subjectCategoryDTOList);
            }
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error", e);
            return Result.fail(QUERY_FAIL.getNotify() + e.getMessage());
        }
    }


    /**
     * 查询分类小类
     *
     * @param subjectCategoryDTO dto
     * @return json result List<SubjectCategoryDTO>
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
                    .convertBOToSubjectCategoryDTOList(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryCategoryByPrimary.error", e);
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
            log.error("SubjectCategoryController.update.error", e);
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
            log.error("SubjectCategoryController.delete.error", e);
            return Result.fail(UPDATE_FAIL.getNotify() + e.getMessage());
        }
    }

    /**
     * 根据大分类的ID查出小分类和标签
     * @param subjectCategoryDTO 分类ID
     * @return 一次性返回分类和标签数据，后端组装树形结构
     */
    @PostMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryAndLabel.dto:{}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDTOToSubjectCategoryBO(subjectCategoryDTO);
            // 根据分类ID查询标签信息
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = new LinkedList<>();
            // 组装标签信息到CategoryDTO中
            subjectCategoryBOList.forEach(bo -> {
                SubjectCategoryDTO dto = SubjectCategoryDTOConverter.INSTANCE.convertSubjectCategoryBOToDTO(bo);
                List<SubjectLabelDTO> labelDTOList = SubjectLabelDTOConverter.INSTANCE.convertBOToSubjectLabelDTOList(bo.getLabelBOList());
                dto.setLabelDTOList(labelDTOList);
                dtoList.add(dto);
            });
            return Result.ok(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail();
        }
    }


}
