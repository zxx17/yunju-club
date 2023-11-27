package com.zsyj.subject.domian.service;

import com.zsyj.subject.domian.entity.SubjectLabelBO;

public interface ISubjectLabelDomainService {

    Boolean add(SubjectLabelBO subjectLabelBO);

    Boolean updateLabel(SubjectLabelBO subjectLabelBO);

    Boolean deleteLabel(SubjectLabelBO subjectLabelBO);
}
