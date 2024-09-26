package com.zsyj.practice.server.service;

import com.zsyj.practice.api.req.*;
import com.zsyj.practice.api.vo.RankVO;
import com.zsyj.practice.api.vo.ReportVO;
import com.zsyj.practice.api.vo.ScoreDetailVO;
import com.zsyj.practice.api.vo.SubjectDetailVO;

import java.util.List;

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

    /**
     * 提交练题情况（交卷）
     */
    Boolean submit(SubmitPracticeDetailReq req);

    /**
     * 答案解析-每题得分
     */
    List<ScoreDetailVO> getScoreDetail(GetScoreDetailReq req);

    /**
     * 答案解析-答题详情
     */
    SubjectDetailVO getSubjectParseDetail(GetSubjectDetailReq req);


    /**
     * 答案解析-评估报告
     */
    ReportVO getReport(GetReportReq req);

    /**
     * 获取练习榜
     */
    List<RankVO> getPracticeRankList();

    /**
     * 放弃练习
     */
    Boolean giveUp(Long practiceId);
}
