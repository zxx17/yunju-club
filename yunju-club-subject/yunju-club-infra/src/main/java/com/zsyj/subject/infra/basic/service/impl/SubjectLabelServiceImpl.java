package com.zsyj.subject.infra.basic.service.impl;

import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.mapper.SubjectLabelDao;
import com.zsyj.subject.infra.basic.service.SubjectLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目标签表(SubjectLabel)表服务实现类
 *
 * @author makejava
 * @since 2023-11-27 19:22:45
 */
@Service("subjectLabelService")
public class SubjectLabelServiceImpl implements SubjectLabelService {
    @Resource
    private SubjectLabelDao subjectLabelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectLabel queryById(Integer id) {
        return this.subjectLabelDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SubjectLabel subjectLabel) {
        return  this.subjectLabelDao.insert(subjectLabel);
    }

    /**
     * 修改数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SubjectLabel subjectLabel) {
        return  this.subjectLabelDao.update(subjectLabel);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.subjectLabelDao.deleteById(id) > 0;
    }

    /**
     * 根据标签id批量查询标签
     * @param labelIds labelId
     * @return list List<SubjectLabel>
     */
    @Override
    public List<SubjectLabel> querySubjectLabelById(List<Long> labelIds) {
        return  this.subjectLabelDao.querySubjectLabelById(labelIds);
    }

    @Override
    public List<SubjectLabel> batchQueryById(List<Long> labelIdList) {
        return this.subjectLabelDao.batchQueryById(labelIdList);
    }
}
