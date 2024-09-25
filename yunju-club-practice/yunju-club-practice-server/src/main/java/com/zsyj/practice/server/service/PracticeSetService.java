package com.zsyj.practice.server.service;

import com.zsyj.practice.api.common.PageResult;
import com.zsyj.practice.api.req.GetPracticeSubjectsReq;
import com.zsyj.practice.api.req.GetUnCompletePracticeReq;
import com.zsyj.practice.api.vo.*;
import com.zsyj.practice.server.entity.dto.PracticeSetDTO;
import com.zsyj.practice.server.entity.dto.PracticeSubjectDTO;

import java.util.List;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/23
 */
public interface PracticeSetService {

    /**
     * 获取专项练习内容
     * 每个一级大类是一个专项练习
     */
    List<SpecialPracticeVO> getSpecialPracticeContent();

    /**
     * 开始练习
     */

    PracticeSetVO addPractice(PracticeSubjectDTO dto);

    /**
     * 获取练习题
     */
    PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req);

    /**
     * 获取题目详情
     */
    PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto);

    /**
     * 获取模拟套题内容
     */
    PageResult<PracticeSetVO> getPreSetContent(PracticeSetDTO dto);

    /**
     * 获取未完成的练题内容
     */
    PageResult<UnCompletePracticeSetVO> getUnCompletePractice(GetUnCompletePracticeReq req);
}
