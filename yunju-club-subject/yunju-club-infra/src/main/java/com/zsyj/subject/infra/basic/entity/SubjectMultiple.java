package com.zsyj.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 多选题表(SubjectMultiple)实体类
 *
 * @author Xinxuan Zhuo
 * @since 2023-11-30 11:38:36
 */
@Data
public class SubjectMultiple implements Serializable {

    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 选项类型（A B C D E）
     */
    private Integer optionType;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;
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

