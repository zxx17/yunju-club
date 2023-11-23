package com.zsyj.subject.infra.basic.entity;

import java.io.Serializable;

/**
 * 题目分类表(SubjectCategory)实体类
 *
 * @author makejava
 * @since 2023-11-23 17:12:51
 */
public class SubjectCategory implements Serializable {
    private static final long serialVersionUID = -63806072978952824L;
    
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

}

