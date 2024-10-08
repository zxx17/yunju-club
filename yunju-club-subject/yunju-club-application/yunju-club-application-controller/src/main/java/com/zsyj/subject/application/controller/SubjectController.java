package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectAnswerDTOConvert;
import com.zsyj.subject.application.convert.SubjectInfoDTOConvert;
import com.zsyj.subject.application.dto.SubjectInfoDTO;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.service.ISubjectInfoDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.zsyj.subject.common.enums.NotifyEnum.*;


/**
 * @author Xinxuan Zhuo
 * @version 2023/11/23
 * <p>
 * 刷题 题目controller
 * </p>
 */

@Slf4j
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private ISubjectInfoDomainService subjectInfoDomainService;

    /**
     * 新增题目
     *
     * @param subjectInfoDTO dto
     */
    @PostMapping("/add")
    public Result<Object> addSubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto{}", JSONObject.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectInfoDTO.getSubjectName()), "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(), "题目分值不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");

            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConvert.INSTANCE.
                    convertDTOToSubjectInfoBO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOList = SubjectAnswerDTOConvert.INSTANCE
                    .convertDTOTOSubjectAnswerBO(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOList);
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(INSERT_SUCCESS.getNotify());
        } catch (Exception e) {
            log.error("SubjectController.add.error", e);
            return Result.fail(INSERT_FAIL.getNotify() + e.getMessage());
        }
    }


    /**
     * 查询题目列表
     *
     * @param subjectInfoDTO dto
     */
    @PostMapping("/getSubjectPage")
    public Result<Object> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPage.dto{}", JSONObject.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkNotNull(subjectInfoDTO.getCategoryId(), "题目分类id不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getLabelId(), "题目标签id不能为空");

            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConvert.INSTANCE
                    .convertDTOToSubjectInfoBO(subjectInfoDTO);

            PageResult<SubjectInfoBO> subjectInfoBOPageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);

            PageResult<SubjectInfoDTO> subjectInfoDTOPageResult = SubjectInfoDTOConvert.INSTANCE
                    .convertBOToSubjectInfoDTOButPage(subjectInfoBOPageResult);

            return Result.ok(subjectInfoDTOPageResult);
        } catch (Exception e) {
            log.error("SubjectController.getSubjectPage.error", e);
            return Result.fail(QUERY_FAIL.getNotify() + e.getMessage());
        }
    }

    /**
     * 查询题目详情信息
     */
    @PostMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo.dto:{}", JSONObject.toJSONString(subjectInfoDTO));
            }
            // 校验
            Preconditions.checkNotNull(subjectInfoDTO.getId(), "题目id不能为空");
            // DTO-->BO转换
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConvert.INSTANCE
                    .convertDTOToSubjectInfoBO(subjectInfoDTO);
            // 查询
            SubjectInfoBO boResult = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            // BO-->DTO转换
            SubjectInfoDTO dto = SubjectInfoDTOConvert.INSTANCE
                    .convertBOToSubjectInfoDTO(boResult);
            return Result.ok(dto);
        } catch (Exception e) {
            log.error("SubjectCategoryController.querySubjectInfo.error:{} : {}", e, e.getMessage());
            return Result.fail();
        }
    }


    /**
     * 全文检索
     */
    @PostMapping("/getSubjectPageBySearch")
    public Result<PageResult<SubjectInfoEs>> getSubjectPageBySearch(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPageBySearch.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectInfoDTO.getKeyWord()), "关键词不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConvert.INSTANCE.convertDTOToSubjectInfoBO(subjectInfoDTO);
            subjectInfoBO.setPageNo(subjectInfoDTO.getPageNo());
            subjectInfoBO.setPageSize(subjectInfoDTO.getPageSize());
            PageResult<SubjectInfoEs> boPageResult = subjectInfoDomainService.getSubjectPageBySearch(subjectInfoBO);
            return Result.ok(boPageResult);
        } catch (Exception e) {
            log.error("SubjectCategoryController.getSubjectPageBySearch.error:{}", e.getMessage(), e);
            return Result.fail();
        }
    }

    /**
     * 获取题目贡献榜
     */
    @PostMapping("/getContributeList")
    public Result<List<SubjectInfoDTO>> getContributeList() {
        try {
            List<SubjectInfoBO> boList = subjectInfoDomainService.getContributeList();
            List<SubjectInfoDTO> dtoList = SubjectInfoDTOConvert.INSTANCE.convertBOListToSubjectInfoDTOList(boList);
            return Result.ok(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.getContributeList.error:{}", e.getMessage(), e);
            return Result.fail();
        }
    }

    // TODO 题目纠错功能待开发


}
