package com.zsyj.subject.domian.handler.subject;


import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.convert.SubjectBriefBOConvert;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;
import com.zsyj.subject.infra.basic.entity.SubjectBrief;
import com.zsyj.subject.infra.basic.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Override
    public SubjectOptionBO query(Long subjectId) {
        // 构造查询实体
        SubjectBrief subjectBrief = new SubjectBrief();
        subjectBrief.setSubjectId(subjectId);
        // 调用基础设施层进行查询
        SubjectBrief subjectBriefDO = subjectBriefService.queryByCondition(subjectBrief);
        // 转换为业务层对象
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setSubjectAnswer(subjectBriefDO.getSubjectAnswer());
        return subjectOptionBO;
    }

}
