package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectAnswerDTOConvert;
import com.zsyj.subject.application.convert.SubjectInfoDTOConvert;
import com.zsyj.subject.application.dto.SubjectInfoDTO;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.service.ISubjectInfoDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.zsyj.subject.common.enums.NotifyEnum.INSERT_FAIL;
import static com.zsyj.subject.common.enums.NotifyEnum.INSERT_SUCCESS;


/**
 * @author Xinxuan Zhuo
 * @version 2023/11/23
 * <p>
 *  刷题 题目controller
 * </p>
 */

@Slf4j
@RestController
@RequestMapping("/subject/info")
public class SubjectController {

    @Resource
    private ISubjectInfoDomainService subjectInfoDomainService;

    /**
     * 新增题目
     * @param subjectInfoDTO dto
     */
    @PostMapping("/add")
    public Result<Object> addSubjectInfo(SubjectInfoDTO subjectInfoDTO){
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto{}", JSONObject.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectInfoDTO.getSubjectName()), "题目名称不能为空");
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectInfoDTO.getSubjectAnswer()), "题目分数不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectAnswer(), "题目答案不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");

            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConvert.INSTANCE.convertDTOToSubjectInfoBO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOList = SubjectAnswerDTOConvert.INSTANCE.convertDTOTOSubjectAnswerBO(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOList);
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(INSERT_SUCCESS);
        }catch (Exception e){
            log.error("SubjectController.add.error{}", e.getMessage());
            return Result.fail(INSERT_FAIL + e.getMessage());
        }
    }


}
