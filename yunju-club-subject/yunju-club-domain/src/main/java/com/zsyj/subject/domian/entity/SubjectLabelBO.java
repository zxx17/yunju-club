package com.zsyj.subject.domian.entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-27 19:22:45
 */
@Data
public class SubjectLabelBO implements Serializable {

    private Integer id;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 排序
     */
    private Integer sortNum;


}

