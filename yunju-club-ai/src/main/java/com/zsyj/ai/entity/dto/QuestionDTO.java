package com.zsyj.ai.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 问题DTO.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Data
public class QuestionDTO implements Serializable {


    /**
     * 问题的主体内容
     */
    private String context;

    /**
     * 是否根据用户记录进行问答
     */
    private Boolean useHistoryData;

}
