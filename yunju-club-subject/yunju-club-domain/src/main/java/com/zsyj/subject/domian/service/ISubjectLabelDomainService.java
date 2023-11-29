package com.zsyj.subject.domian.service;

import com.zsyj.subject.domian.entity.SubjectLabelBO;

import java.util.List;

public interface ISubjectLabelDomainService {

    Boolean add(SubjectLabelBO subjectLabelBO);

    Boolean updateLabel(SubjectLabelBO subjectLabelBO);

    Boolean deleteLabel(SubjectLabelBO subjectLabelBO);

    /**
     * 根据分类id查询标签
     *
     * @param subjectLabelBO bo 分类id
     * @return json result List<SubjectLabelDTO>
     */
    List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO);
}
