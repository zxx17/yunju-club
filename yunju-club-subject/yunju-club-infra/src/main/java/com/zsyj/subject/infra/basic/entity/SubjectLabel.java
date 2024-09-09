package com.zsyj.subject.infra.basic.entity;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-27 19:22:45
 */
@Data
public class SubjectLabel implements Serializable {

    private Integer id;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 排序
     */
    private Integer sortNum;
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

