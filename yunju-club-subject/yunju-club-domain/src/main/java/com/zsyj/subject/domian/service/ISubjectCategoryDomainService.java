package com.zsyj.subject.domian.service;

import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;

import java.util.List;


/**
 * @author Xinxuan Zhuo
 * @version 2023/11/24
 * <p>
 *
 * </p>
 */

public interface ISubjectCategoryDomainService {

    void add(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO);
}
