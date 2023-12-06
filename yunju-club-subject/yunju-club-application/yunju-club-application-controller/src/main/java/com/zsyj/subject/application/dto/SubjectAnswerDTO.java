package com.zsyj.subject.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目答案DTO
 */
@Data
public class SubjectAnswerDTO implements Serializable {
    /**
     * 题目答案标识
     */
    private Integer optionType;
    /**
     * 题目答案
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;



}

