package com.zsyj.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 圈子信息对象 share_circle
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-14
 */
public class ShareCircle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 圈子ID */
    private Long id;

    /** 父级ID,-1为大类 */
    @Excel(name = "父级ID,-1为大类")
    private Long parentId;

    /** 圈子名称 */
    @Excel(name = "圈子名称")
    private String circleName;

    /** 圈子图片 */
    @Excel(name = "圈子图片")
    private String icon;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 是否被删除 0为删除 1已删除 */
    @Excel(name = "是否被删除 0为删除 1已删除")
    private Long isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setCircleName(String circleName) 
    {
        this.circleName = circleName;
    }

    public String getCircleName() 
    {
        return circleName;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
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
            .append("parentId", getParentId())
            .append("circleName", getCircleName())
            .append("icon", getIcon())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
