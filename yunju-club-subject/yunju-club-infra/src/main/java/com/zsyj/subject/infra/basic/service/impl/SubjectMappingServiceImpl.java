package com.zsyj.subject.infra.basic.service.impl;

import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.mapper.SubjectMappingDao;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类关联表(SubjectMapping)表服务实现类
 *
 * @author makejava
 * @since 2023-11-29 19:34:34
 */
@Slf4j
@Service("subjectMappingService")
public class SubjectMappingServiceImpl implements SubjectMappingService {
    @Resource
    private SubjectMappingDao subjectMappingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectMapping queryById(Long id) {
        return this.subjectMappingDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectMapping insert(SubjectMapping subjectMapping) {
        this.subjectMappingDao.insert(subjectMapping);
        return subjectMapping;
    }

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectMapping update(SubjectMapping subjectMapping) {
        this.subjectMappingDao.update(subjectMapping);
        return this.queryById(subjectMapping.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.subjectMappingDao.deleteById(id) > 0;
    }

    /**
     * 通过分类id查询标签
     * @param subjectMapping categoryId is_deleted
     * @return list list<SubjectMapping>
     */
    @Override
    public List<SubjectMapping> queryLabelByCategoryId(SubjectMapping subjectMapping) {
        List<SubjectMapping> subjectMappingList = this.subjectMappingDao.queryLabelByCategoryId(subjectMapping);
        log.info("SubjectMappingServiceImpl.queryLabelByCategoryId.subjectMappingList{}", subjectMappingList);
        return subjectMappingList;
    }

    @Override
    public void batchInsert(List<SubjectMapping> subjectMappingList) {
        this.subjectMappingDao.insertBatch(subjectMappingList);
    }

    @Override
    public List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.queryDistinctLabelId(subjectMapping);
    }
}
