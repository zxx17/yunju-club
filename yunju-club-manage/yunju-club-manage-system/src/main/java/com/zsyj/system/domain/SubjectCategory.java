package com.zsyj.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 题目分类对象 subject_category
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-12
 */
public class SubjectCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类类型 */
    @Excel(name = "分类类型")
    private Long categoryType;

    /** 分类图片url */
    @Excel(name = "分类图片url")
    private String imageUrl;

    /** 父级id */
    @Excel(name = "父级id")
    private Long parentId;

    /** 删除判断 */
    @Excel(name = "删除判断")
    private Long isDeleted;

    /** 题目标签信息 */
    private List<SubjectLabel> subjectLabelList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setCategoryType(Long categoryType) 
    {
        this.categoryType = categoryType;
    }

    public Long getCategoryType() 
    {
        return categoryType;
    }
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    public List<SubjectLabel> getSubjectLabelList()
    {
        return subjectLabelList;
    }

    public void setSubjectLabelList(List<SubjectLabel> subjectLabelList)
    {
        this.subjectLabelList = subjectLabelList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryName", getCategoryName())
            .append("categoryType", getCategoryType())
            .append("imageUrl", getImageUrl())
            .append("parentId", getParentId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("subjectLabelList", getSubjectLabelList())
            .toString();
    }
}
