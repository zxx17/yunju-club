package com.zsyj.subject.domian.service;

import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;

import java.util.List;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/5
 * <p>
 *
 * </p>
 */

public interface ISubjectInfoDomainService {

    /**
     * 新增题目
     * @param subjectInfoBO bo
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 查询题目列表
     * @param subjectInfoBO dto
     */
    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);

    /**
     * 查询题目详情
     * @param subjectInfoBO subjectInfoBO
     * @return subjectInfoBO
     */
    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);

    /**
     * 全文检索
     */
    PageResult<SubjectInfoEs> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO);

    /**
     * 获取题目贡献榜
     * @return SubjectInfoBOLIst
     */
    List<SubjectInfoBO> getContributeList();
}
