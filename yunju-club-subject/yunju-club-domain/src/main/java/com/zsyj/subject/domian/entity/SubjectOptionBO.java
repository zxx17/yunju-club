package com.zsyj.subject.domian.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题目详情查询信息BO.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/2
 **/
@Data
public class SubjectOptionBO implements Serializable {

    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 答案选项
     */
    private List<SubjectAnswerBO> optionList;

}
