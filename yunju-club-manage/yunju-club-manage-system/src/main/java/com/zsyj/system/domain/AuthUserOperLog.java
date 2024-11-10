package com.zsyj.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zsyj.common.annotation.Excel;
import com.zsyj.common.core.domain.BaseEntity;

/**
 * 社区C端用户操作日志对象 auth_user_oper_log
 * 
 * @author Xinxuan Zhuo
 * @date 2024-11-10
 */
public class AuthUserOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户账号（唯一标识） */
    @Excel(name = "用户账号", readConverterExp = "唯=一标识")
    private String userName;

    /** 答题1|仿真实验2|场景实验3|发布4 */
    @Excel(name = "答题1|仿真实验2|场景实验3|发布4")
    private Long operationType;

    /** 操作结果 */
    @Excel(name = "操作结果")
    private String operationResult;

    /** 操作详情 */
    @Excel(name = "操作详情")
    private String details;

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
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setOperationType(Long operationType) 
    {
        this.operationType = operationType;
    }

    public Long getOperationType() 
    {
        return operationType;
    }
    public void setOperationResult(String operationResult) 
    {
        this.operationResult = operationResult;
    }

    public String getOperationResult() 
    {
        return operationResult;
    }
    public void setDetails(String details) 
    {
        this.details = details;
    }

    public String getDetails() 
    {
        return details;
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
            .append("userName", getUserName())
            .append("operationType", getOperationType())
            .append("operationResult", getOperationResult())
            .append("details", getDetails())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
