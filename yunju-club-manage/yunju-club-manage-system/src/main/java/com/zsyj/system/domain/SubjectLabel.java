package com.zsyj.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 题目标签对象 subject_label
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-12
 */
public class SubjectLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String labelName;

    /** 分类Id */
    @Excel(name = "分类Id")
    private Long categoryId;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortNum;

    /** 是否删除 0未删除|1已删除 */
    @Excel(name = "是否删除 0未删除|1已删除")
    private Long isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLabelName(String labelName) 
    {
        this.labelName = labelName;
    }

    public String getLabelName() 
    {
        return labelName;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setSortNum(Long sortNum) 
    {
        this.sortNum = sortNum;
    }

    public Long getSortNum() 
    {
        return sortNum;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("labelName", getLabelName())
            .append("categoryId", getCategoryId())
            .append("sortNum", getSortNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
