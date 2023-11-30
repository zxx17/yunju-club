package com.zsyj.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;


@Data
public class SubjectInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 题目名称
     */
    private String subjectName;
    /**
     * 题目难度
     */
    private Integer subjectDifficult;
    /**
     * 出题人姓名
     */
    private String settleName;
    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;
    /**
     * 题目分值
     */
    private Integer subjectSource;
    /**
     * 题目解析
     */
    private String subjectParse;
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
     * 是否删除 0未删除 1yi's
     */
    private Integer isDeleted;



}

