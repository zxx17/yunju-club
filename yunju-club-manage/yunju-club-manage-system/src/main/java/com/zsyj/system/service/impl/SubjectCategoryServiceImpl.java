package com.zsyj.system.service.impl;

import java.util.List;
import com.zsyj.common.utils.DateUtils;
import com.zsyj.system.domain.SubjectCategory;
import com.zsyj.system.domain.SubjectLabel;
import com.zsyj.system.mapper.SubjectCategoryMapper;
import com.zsyj.system.service.ISubjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.zsyj.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * 题目分类Service业务层处理
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-12
 */
@Service
public class SubjectCategoryServiceImpl implements ISubjectCategoryService
{
    @Autowired
    private SubjectCategoryMapper subjectCategoryMapper;

    /**
     * 查询题目分类
     * 
     * @param id 题目分类主键
     * @return 题目分类
     */
    @Override
    public SubjectCategory selectSubjectCategoryById(Long id)
    {
        return subjectCategoryMapper.selectSubjectCategoryById(id);
    }

    /**
     * 查询题目分类列表
     * 
     * @param subjectCategory 题目分类
     * @return 题目分类
     */
    @Override
    public List<SubjectCategory> selectSubjectCategoryList(SubjectCategory subjectCategory)
    {
        return subjectCategoryMapper.selectSubjectCategoryList(subjectCategory);
    }

    /**
     * 新增题目分类
     * 
     * @param subjectCategory 题目分类
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSubjectCategory(SubjectCategory subjectCategory)
    {
        subjectCategory.setCreateTime(DateUtils.getNowDate());
        int rows = subjectCategoryMapper.insertSubjectCategory(subjectCategory);
        insertSubjectLabel(subjectCategory);
        return rows;
    }

    /**
     * 修改题目分类
     * 
     * @param subjectCategory 题目分类
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSubjectCategory(SubjectCategory subjectCategory)
    {
        subjectCategory.setUpdateTime(DateUtils.getNowDate());
        subjectCategoryMapper.deleteSubjectLabelByCategoryId(subjectCategory.getId());
        insertSubjectLabel(subjectCategory);
        return subjectCategoryMapper.updateSubjectCategory(subjectCategory);
    }

    /**
     * 批量删除题目分类
     * 
     * @param ids 需要删除的题目分类主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSubjectCategoryByIds(Long[] ids)
    {
        subjectCategoryMapper.deleteSubjectLabelByCategoryIds(ids);
        return subjectCategoryMapper.deleteSubjectCategoryByIds(ids);
    }

    /**
     * 删除题目分类信息
     * 
     * @param id 题目分类主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSubjectCategoryById(Long id)
    {
        subjectCategoryMapper.deleteSubjectLabelByCategoryId(id);
        return subjectCategoryMapper.deleteSubjectCategoryById(id);
    }

    /**
     * 新增题目标签信息
     * 
     * @param subjectCategory 题目分类对象
     */
    public void insertSubjectLabel(SubjectCategory subjectCategory)
    {
        List<SubjectLabel> subjectLabelList = subjectCategory.getSubjectLabelList();
        Long id = subjectCategory.getId();
        if (StringUtils.isNotNull(subjectLabelList))
        {
            List<SubjectLabel> list = new ArrayList<SubjectLabel>();
            for (SubjectLabel subjectLabel : subjectLabelList)
            {
                subjectLabel.setCategoryId(id);
                list.add(subjectLabel);
            }
            if (list.size() > 0)
            {
                subjectCategoryMapper.batchSubjectLabel(list);
            }
        }
    }
}
