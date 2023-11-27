package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.domian.convert.SubjectLabelBOConverter;
import com.zsyj.subject.domian.entity.SubjectLabelBO;
import com.zsyj.subject.domian.service.ISubjectLabelDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.zsyj.subject.common.enums.DeletedFlagEnum.IS_DELETED;
import static com.zsyj.subject.common.enums.DeletedFlagEnum.UN_DELETE;

@Slf4j
@Service
public class SubjectLabelDomainServiceImpl implements ISubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;


    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(UN_DELETE.getFlag());
        int isInsert = subjectLabelService.insert(subjectLabel);
        return isInsert > 0;
    }

    @Override
    public Boolean updateLabel(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.updateLabel.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        int isUpdate = subjectLabelService.update(subjectLabel);
        return isUpdate > 0;
    }

    @Override
    public Boolean deleteLabel(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.deleteLabel.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IS_DELETED.getFlag());
        int isUpdate = subjectLabelService.update(subjectLabel);
        return isUpdate > 0;
    }

}
