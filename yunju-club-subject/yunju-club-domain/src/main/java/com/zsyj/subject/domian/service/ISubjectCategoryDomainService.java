package com.zsyj.subject.domian.service;

import com.zsyj.subject.domian.entity.SubjectCategoryBO;

import java.util.List;


/**
 * @author Xinxuan Zhuo
 * @version 2023/11/24
 * <p>
 *
 * </p>
 */

public interface ISubjectCategoryDomainService {

    /**
     * 新增分类
     * @param subjectCategoryBO bo
     */
    void add(SubjectCategoryBO subjectCategoryBO);

    /**
     * 查询分类
     * @param subjectCategoryBO bo
     * @return list
     */
    List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO);

    /**
     * 更新分类
     * @param subjectCategoryBO bo
     * @return boolean
     */
    Boolean update(SubjectCategoryBO subjectCategoryBO);

    /**
     * 删除分类
     * @param subjectCategoryBO bo
     * @return boolean
     */
    Boolean delete(SubjectCategoryBO subjectCategoryBO);
}
