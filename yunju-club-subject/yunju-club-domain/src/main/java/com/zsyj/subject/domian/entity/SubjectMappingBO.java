package com.zsyj.subject.domian.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectMappingBO implements Serializable {

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
    private Long categoryId;
    /**
     * 标签id
     */
    private Long labelId;


}
