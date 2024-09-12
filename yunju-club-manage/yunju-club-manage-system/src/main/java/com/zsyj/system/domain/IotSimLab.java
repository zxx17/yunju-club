package com.zsyj.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 虚拟仿真实验对象 iot_sim_lab
 * 
 * @author Xinxuan Zhuo
 * @date 2024-09-11
 */
public class IotSimLab extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 0是默认，1是推荐 */
    @Excel(name = "0是默认，1是推荐")
    private Long projectType;

    /** 项目支持地址 */
    @Excel(name = "项目支持地址")
    private String projectUrl;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String projectDesc;

    /** 难度（1，2，3，4） */
    @Excel(name = "难度", readConverterExp = "1=，2，3，4")
    private Long diffcult;

    /** 排序 */
    @Excel(name = "排序")
    private Long sorted;

    /** 完成数量(排行榜使用) */
    @Excel(name = "完成数量(排行榜使用)")
    private Long finishedCount;

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
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setProjectType(Long projectType) 
    {
        this.projectType = projectType;
    }

    public Long getProjectType() 
    {
        return projectType;
    }
    public void setProjectUrl(String projectUrl) 
    {
        this.projectUrl = projectUrl;
    }

    public String getProjectUrl() 
    {
        return projectUrl;
    }
    public void setProjectDesc(String projectDesc) 
    {
        this.projectDesc = projectDesc;
    }

    public String getProjectDesc() 
    {
        return projectDesc;
    }
    public void setDiffcult(Long diffcult) 
    {
        this.diffcult = diffcult;
    }

    public Long getDiffcult() 
    {
        return diffcult;
    }
    public void setSorted(Long sorted) 
    {
        this.sorted = sorted;
    }

    public Long getSorted() 
    {
        return sorted;
    }
    public void setFinishedCount(Long finishedCount) 
    {
        this.finishedCount = finishedCount;
    }

    public Long getFinishedCount() 
    {
        return finishedCount;
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
            .append("deviceName", getDeviceName())
            .append("projectName", getProjectName())
            .append("projectType", getProjectType())
            .append("projectUrl", getProjectUrl())
            .append("projectDesc", getProjectDesc())
            .append("diffcult", getDiffcult())
            .append("sorted", getSorted())
            .append("finishedCount", getFinishedCount())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
