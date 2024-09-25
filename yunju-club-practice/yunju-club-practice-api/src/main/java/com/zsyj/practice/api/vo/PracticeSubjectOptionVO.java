package com.zsyj.practice.api.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeSubjectOptionVO implements Serializable {

    /**
     * 选项类型
     */
    private Integer optionType;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 是否正確
     */
    private Integer isCorrect;

}