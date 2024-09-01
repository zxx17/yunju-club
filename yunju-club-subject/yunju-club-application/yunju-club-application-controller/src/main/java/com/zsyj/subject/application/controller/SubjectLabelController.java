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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.zsyj.subject.common.enums.NotifyEnum.INSERT_FAIL;
import static com.zsyj.subject.common.enums.NotifyEnum.QUERY_FAIL;

/**
 * 刷题模块 标签 控制器
 */
@Slf4j
@RestController
@RequestMapping("/subject/label")
public class SubjectLabelController {

    @Resource
    private ISubjectLabelDomainService subjectLabelDomainService;


    @PostMapping("/add")
    public Result<Object> addLabel(@RequestBody SubjectLabelDTO subjectLabelDTO) {
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
            return Result.fail(INSERT_FAIL.getNotify() + e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result<Object> updateLabel(@RequestBody SubjectLabelDTO subjectLabelDTO) {
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
            return Result.fail(INSERT_FAIL.getNotify() + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<Object> deleteLabel(@RequestBody SubjectLabelDTO subjectLabelDTO) {
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
            return Result.fail(INSERT_FAIL.getNotify() + e.getMessage());
        }
    }


    /**
     * 根据分类id查询标签
     *
     * @param subjectLabelDTO dto 分类id
     * @return json result List<SubjectLabelDTO>
     */
    @PostMapping("/queryLabelByCategoryId")
    public Result<Object> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryLabelByCategoryId.dto{}", JSONObject.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkNotNull(subjectLabelDTO.getCategoryId(), "分类id不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE
                    .convertDTOToSubjectLabelBO(subjectLabelDTO);
            List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> subjectLabelDTOList = SubjectLabelDTOConverter.INSTANCE
                    .convertBOToSubjectLabelDTOList(subjectLabelBOList);
            return Result.ok(subjectLabelDTOList);
        } catch (Exception e) {
            log.error("SubjectLabelController.queryLabelByCategoryId.error{}", e.getMessage());
            return Result.fail(QUERY_FAIL.getNotify() + e.getMessage());
        }
    }


}
