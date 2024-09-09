package com.zsyj.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目分类表(SubjectCategory)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-24 11:21:40
 */

@Data
public class SubjectCategory implements Serializable {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类类型
     */
    private Integer categoryType;
    /**
     * 分类图片url
     */
    private String imageUrl;
    /**
     * 父级id
     */
    private Integer parentId;
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
     * 是否删除 0未删除 | 1已删除
     */
    private Integer isDeleted;
}

