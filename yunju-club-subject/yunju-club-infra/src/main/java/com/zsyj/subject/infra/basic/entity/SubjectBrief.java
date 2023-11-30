package com.zsyj.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 简答题表(SubjectBrief)实体类
 *
 * @author makejava
 * @since 2023-11-30 11:37:26
 */
@Data
public class SubjectBrief implements Serializable {

    private Integer id;
    /**
     * 题目id
     */
    private String subjectId;
    /**
     * 题目答案
     */
    private Integer subjectAnswer;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除 0未删除|1已删除
     */
    private Integer isDeleted;



}

