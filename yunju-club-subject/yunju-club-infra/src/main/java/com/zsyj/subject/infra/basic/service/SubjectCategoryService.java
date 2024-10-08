package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.infra.basic.entity.SubjectCategory;

import java.util.List;


/**
 * 题目分类表(SubjectCategory)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-24 11:21:42
 */
public interface SubjectCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectCategory queryById(Long id);


    /**
     * 新增数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    SubjectCategory insert(SubjectCategory subjectCategory);

    /**
     * 修改数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    int update(SubjectCategory subjectCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<SubjectCategory> queryCategory(SubjectCategory subjectCategory);

    Long querySubjectCount(Integer id);
}
