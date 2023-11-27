package com.zsyj.subject.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.zsyj.subject.application.convert.SubjectLabelDTOConverter;
import com.zsyj.subject.application.dto.SubjectLabelDTO;
import com.zsyj.subject.common.entity.Result;
import com.zsyj.subject.domian.entity.SubjectLabelBO;
import com.zsyj.subject.domian.service.ISubjectLabelDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.zsyj.subject.common.enums.NotifyEnum.INSERT_FAIL;

@Slf4j
@RestController
@RequestMapping("/subject/label")
public class SubjectLabelController {

    @Resource
    private ISubjectLabelDomainService subjectLabelDomainService;


    @PostMapping("/add")
    public Result<Object> addLabel(SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.addLabel.dto{}", JSONObject.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(subjectLabelDTO.getLabelName()), "标签名不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDTOToSubjectLabelBO(subjectLabelDTO);
            Boolean isInsert = subjectLabelDomainService.add(subjectLabelBO);
            return Result.ok(isInsert);
        } catch (Exception e) {
            log.error("SubjectLabelController.addLabel.error{}", e.getMessage());
            return Result.fail(INSERT_FAIL + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result<Object> updateLabel(SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.updateLabel.dto{}", JSONObject.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签id不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDTOToSubjectLabelBO(subjectLabelDTO);
            Boolean isInsert = subjectLabelDomainService.updateLabel(subjectLabelBO);
            return Result.ok(isInsert);
        } catch (Exception e) {
            log.error("SubjectLabelController.updateLabel.error{}", e.getMessage());
            return Result.fail(INSERT_FAIL + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<Object> deleteLabel(SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.deleteLabel.dto{}", JSONObject.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkNotNull(subjectLabelDTO.getId(), "标签id不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDTOToSubjectLabelBO(subjectLabelDTO);
            Boolean isInsert = subjectLabelDomainService.deleteLabel(subjectLabelBO);
            return Result.ok(isInsert);
        } catch (Exception e) {
            log.error("SubjectLabelController.deleteLabel.error{}", e.getMessage());
            return Result.fail(INSERT_FAIL + e.getMessage());
        }
    }


}
