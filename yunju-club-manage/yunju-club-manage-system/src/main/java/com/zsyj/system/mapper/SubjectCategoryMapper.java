package com.zsyj.system.mapper;

import com.zsyj.system.domain.SubjectCategory;
import com.zsyj.system.domain.SubjectLabel;

import java.util.List;

/**
 * 题目分类Mapper接口
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-12
 */
public interface SubjectCategoryMapper 
{
    /**
     * 查询题目分类
     * 
     * @param id 题目分类主键
     * @return 题目分类
     */
    public SubjectCategory selectSubjectCategoryById(Long id);

    /**
     * 查询题目分类列表
     * 
     * @param subjectCategory 题目分类
     * @return 题目分类集合
     */
    public List<SubjectCategory> selectSubjectCategoryList(SubjectCategory subjectCategory);

    /**
     * 新增题目分类
     * 
     * @param subjectCategory 题目分类
     * @return 结果
     */
    public int insertSubjectCategory(SubjectCategory subjectCategory);

    /**
     * 修改题目分类
     * 
     * @param subjectCategory 题目分类
     * @return 结果
     */
    public int updateSubjectCategory(SubjectCategory subjectCategory);

    /**
     * 删除题目分类
     * 
     * @param id 题目分类主键
     * @return 结果
     */
    public int deleteSubjectCategoryById(Long id);

    /**
     * 批量删除题目分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubjectCategoryByIds(Long[] ids);

    /**
     * 批量删除题目标签
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubjectLabelByCategoryIds(Long[] ids);
    
    /**
     * 批量新增题目标签
     * 
     * @param subjectLabelList 题目标签列表
     * @return 结果
     */
    public int batchSubjectLabel(List<SubjectLabel> subjectLabelList);
    

    /**
     * 通过题目分类主键删除题目标签信息
     * 
     * @param id 题目分类ID
     * @return 结果
     */
    public int deleteSubjectLabelByCategoryId(Long id);
}
