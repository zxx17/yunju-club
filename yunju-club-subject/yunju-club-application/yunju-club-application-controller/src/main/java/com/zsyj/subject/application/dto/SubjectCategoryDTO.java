package com.zsyj.subject.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目分类表(SubjectCategory)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-24 11:21:40
 */

@Data
public class SubjectCategoryDTO implements Serializable {

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
     * 数量
     */
    private Long count;

}

