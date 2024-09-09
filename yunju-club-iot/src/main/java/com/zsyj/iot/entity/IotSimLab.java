package com.zsyj.iot.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 虚拟仿真实验表(IotSimLab)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:02
 */
public class IotSimLab implements Serializable {
    private static final long serialVersionUID = -44579006328583711L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 0是默认，1是推荐
     */
    private Integer projectType;
    /**
     * 项目支持地址
     */
    private String projectUrl;
    /**
     * 项目描述
     */
    private String projectDesc;
    /**
     * 难度（1，2，3，4）
     */
    private Integer diffcult;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 完成数量
     */
    private Long finishedCount;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public Integer getDiffcult() {
        return diffcult;
    }

    public void setDiffcult(Integer diffcult) {
        this.diffcult = diffcult;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public Long getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(Long finishedCount) {
        this.finishedCount = finishedCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

}

