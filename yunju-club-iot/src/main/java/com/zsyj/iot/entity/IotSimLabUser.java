package com.zsyj.iot.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 虚拟仿真实验用户记录表(IotSimLabUser)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2024-09-09 08:57:04
 */
public class IotSimLabUser implements Serializable {
    private static final long serialVersionUID = 246395924621101268L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 完成用户id
     */
    private String userId;
    /**
     * 完成的仿真实验id
     */
    private Long simLabId;
    /**
     * 是否完成 0未完成 1已完成
     */
    private Integer isFinished;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSimLabId() {
        return simLabId;
    }

    public void setSimLabId(Long simLabId) {
        this.simLabId = simLabId;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
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

