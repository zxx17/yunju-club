package com.zsyj.subject.domian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目点赞消息
 */
@Data
public class SubjectLikedMessage implements Serializable {


    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 点赞人id
     */
    private String likeUserId;

    /**
     * 点赞状态 1点赞 0不点赞
     */
    private Integer status;

}

