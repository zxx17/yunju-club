package com.zsyj.practice.server.service;

import com.zsyj.practice.api.req.SubmitSubjectDetailReq;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/25
 */
public interface PracticeDetailService {

    /**
     * 提交题目
     */
    Boolean submitSubject(SubmitSubjectDetailReq req);
}
