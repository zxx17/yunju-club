package com.zsyj.subject.domian.handler.subject;


import com.google.common.base.Preconditions;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.convert.SubjectBriefBOConvert;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectBrief;
import com.zsyj.subject.infra.basic.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 简答题目的策略类
 */
@Component
public class BriefTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectBriefService subjectBriefService;
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectBrief subjectBrief = SubjectBriefBOConvert.INSTANCE
                .convertInfoBOToSubjectBrief(subjectInfoBO);
        subjectBrief.setSubjectId(subjectInfoBO.getId());
        subjectBrief.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        subjectBriefService.insert(subjectBrief);
    }

}
