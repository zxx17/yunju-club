package com.zsyj.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 虚拟仿真实验用户记录对象 iot_sim_lab_user
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
public class IotSimLabUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 完成用户id */
    @Excel(name = "完成用户id")
    private Long userId;

    /** 完成的仿真实验id */
    @Excel(name = "完成的仿真实验id")
    private Long simLabId;

    /** 是否完成 0未完成 1已完成 */
    @Excel(name = "是否完成 0未完成 1已完成")
    private Long isFinished;

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
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSimLabId(Long simLabId) 
    {
        this.simLabId = simLabId;
    }

    public Long getSimLabId() 
    {
        return simLabId;
    }
    public void setIsFinished(Long isFinished) 
    {
        this.isFinished = isFinished;
    }

    public Long getIsFinished() 
    {
        return isFinished;
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
            .append("userId", getUserId())
            .append("simLabId", getSimLabId())
            .append("isFinished", getIsFinished())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
