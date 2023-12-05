package com.zsyj.subject.domian.handler.subject;

import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.entity.SubjectInfoBO;

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
     * 实际的题目的插入
     */
    void add(SubjectInfoBO subjectInfoBO);


}
