package com.zsyj.subject.infra.basic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;
import com.zsyj.subject.infra.basic.mapper.SubjectCategoryDao;
import com.zsyj.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类表(SubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-11-24 11:21:42
 */
@Slf4j
@Service("subjectCategoryService")
public class SubjectCategoryServiceImpl implements SubjectCategoryService {
    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectCategory queryById(Integer id) {
        return this.subjectCategoryDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectCategory insert(SubjectCategory subjectCategory) {
        if (log.isInfoEnabled()){
            log.info("SubjectCategoryServiceImpl.insert.subjectCategory{}", JSONObject.toJSONString(subjectCategory));
        }
        this.subjectCategoryDao.insert(subjectCategory);
        return subjectCategory;
    }

    /**
     * 修改数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectCategory update(SubjectCategory subjectCategory) {
        this.subjectCategoryDao.update(subjectCategory);
        return this.queryById(subjectCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.subjectCategoryDao.deleteById(id) > 0;
    }

    @Override
    public List<SubjectCategory> queryPrimaryCategory() {
        return this.subjectCategoryDao.queryPrimaryCategory();
    }
}
