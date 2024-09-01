package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.infra.basic.entity.SubjectMapping;

import java.util.List;

/**
 * 题目分类关联表(SubjectMapping)表服务接口
 *
 * @author makejava
 * @since 2023-11-29 19:34:34
 */
public interface SubjectMappingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectMapping queryById(Long id);


    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    SubjectMapping insert(SubjectMapping subjectMapping);

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    SubjectMapping update(SubjectMapping subjectMapping);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过分类id查询标签
     * @param subjectMapping categoryId is_deleted
     * @return list list<SubjectMapping>
     */
    List<SubjectMapping> queryLabelByCategoryId(SubjectMapping subjectMapping);

    void batchInsert(List<SubjectMapping> subjectMappingList);

    List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping);
}
