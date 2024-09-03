package com.zsyj.subject.domian.handler.subject;

import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/5
 * <p>
 *
 * </p>
 */

public interface SubjectTypeHandler {


    /**
     * 枚举身份的识别
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 题目的插入
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 题目详情的查询
     * @param subjectId 题目ID
     */
    SubjectOptionBO query(Long subjectId);


}
