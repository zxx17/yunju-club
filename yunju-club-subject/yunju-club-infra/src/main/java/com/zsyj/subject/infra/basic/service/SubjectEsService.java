package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
public interface SubjectEsService {

    boolean insert(SubjectInfoEs subjectInfoEs);

    PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs);
}
