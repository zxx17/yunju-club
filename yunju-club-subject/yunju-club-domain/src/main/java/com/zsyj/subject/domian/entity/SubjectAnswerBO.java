package com.zsyj.subject.domian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目答案DTO
 */
@Data
public class SubjectAnswerBO implements Serializable {

    /**
     * 题目答案标识
     */
    private Integer optionTYpe;
    /**
     * 题目答案
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;



}

