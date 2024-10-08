package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.infra.basic.entity.SubjectLabel;

import java.util.List;

/**
 * 题目标签表(SubjectLabel)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-27 19:22:45
 */
public interface SubjectLabelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectLabel queryById(Integer id);

    /**
     * 新增数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    int insert(SubjectLabel subjectLabel);

    /**
     * 修改数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    int update(SubjectLabel subjectLabel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据标签id批量查询标签
     * @param labelIds labelId
     * @return list List<SubjectLabel>
     */
    List<SubjectLabel> querySubjectLabelById(List<Long> labelIds);

    List<SubjectLabel> batchQueryById(List<Long> labelIds);

    List<SubjectLabel> queryByCondition(SubjectLabel subjectLabel);
}
