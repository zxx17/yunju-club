package com.zsyj.subject.infra.basic.service;

import com.zsyj.subject.infra.basic.entity.SubjectInfo;

import java.util.List;


/**
 * 题目信息表(SubjectInfo)表服务接口
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-30 11:37:57
 */
public interface SubjectInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectInfo queryById(Long id);



    /**
     * 新增数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    SubjectInfo insert(SubjectInfo subjectInfo);

    /**
     * 修改数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    SubjectInfo update(SubjectInfo subjectInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    int countByCondition(SubjectInfo subjectInfo, Long categoryId, Long labelId);

    List<SubjectInfo> queryPage(SubjectInfo subjectInfo, Long categoryId, Long labelId, Integer start, Integer pageSize);

    /**
     * 根据条件查询题目id游标 上一题下一题
     * @param subjectId 题目id
     * @param categoryId 分类id
     * @param labelId 标签id
     * @param cursor 游标
     * @return 题目id
     */
    Long querySubjectIdCursor(Long subjectId, Long categoryId, Long labelId, int cursor);
}
