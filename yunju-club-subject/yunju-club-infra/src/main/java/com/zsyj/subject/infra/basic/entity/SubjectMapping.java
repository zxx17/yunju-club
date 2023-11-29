package com.zsyj.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目分类关联表(SubjectMapping)实体类
 *
 * @author makejava
 * @since 2023-11-29 19:34:33
 */
@Data
public class SubjectMapping implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 题目id
     */
    private Integer subjectId;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 标签id
     */
    private Integer labelId;
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
     * 是否删除 0未删除 1已删除
     */
    private Integer isDeleted;


}

