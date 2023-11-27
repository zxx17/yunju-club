package com.zsyj.subject.application.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 * @author makejava
 * @since 2023-11-27 19:22:45
 */
@Data
public class SubjectLabelDTO implements Serializable {

    private Integer id;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 排序
     */
    private Integer sortNum;


}

