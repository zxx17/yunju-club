package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.infra.basic.entity.SubjectBrief;


/**
 * 简答题表(SubjectBrief)表服务接口
 *
 * @author makejava
 * @since 2023-11-30 11:37:26
 */
public interface SubjectBriefService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectBrief queryById(Long id);


    /**
     * 新增数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    SubjectBrief insert(SubjectBrief subjectBrief);

    /**
     * 修改数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    SubjectBrief update(SubjectBrief subjectBrief);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 条件查询
     * @param subjectBrief subjectBrief
     * @return SubjectBriefDO
     */
    SubjectBrief queryByCondition(SubjectBrief subjectBrief);
}
