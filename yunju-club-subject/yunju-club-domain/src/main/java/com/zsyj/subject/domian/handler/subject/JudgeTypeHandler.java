package com.zsyj.subject.domian.handler.subject;

import com.google.common.base.Preconditions;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.convert.SubjectJudgeBOConvert;
import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;
import com.zsyj.subject.infra.basic.entity.SubjectJudge;
import com.zsyj.subject.infra.basic.service.SubjectJudgeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 判断题目的策略类
 */
@Component
public class JudgeTypeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectJudgeService subjectJudgeService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //判断题目的插入
        SubjectJudge subjectJudge = new SubjectJudge();
        //判断题的optionList仅能有一个
        List<SubjectAnswerBO> optionList = subjectInfoBO.getOptionList();
        SubjectAnswerBO subjectAnswerBO = checkOptionList(optionList);
        subjectJudge.setSubjectId(subjectInfoBO.getId());
        subjectJudge.setIsCorrect(subjectAnswerBO.getIsCorrect());
        subjectJudge.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        subjectJudgeService.insert(subjectJudge);
    }

    @Override
    public SubjectOptionBO query(Long subjectId) {
        SubjectJudge subjectJudge = new SubjectJudge();
        subjectJudge.setSubjectId(subjectId);
        List<SubjectJudge> subjectJudgeDOList = subjectJudgeService.queryByCondition(subjectJudge);
        List<SubjectAnswerBO> subjectAnswerBOS = SubjectJudgeBOConvert.INSTANCE.convertEntityToBoList(subjectJudgeDOList);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOS);
        return subjectOptionBO;
    }

    private SubjectAnswerBO checkOptionList(List<SubjectAnswerBO> optionList) {
        // checkArgument 第一个参数为true则返回errorMessage
        Preconditions.checkArgument(!(!optionList.isEmpty() && optionList.size() != 1), "判断题的答案只能有一个");
        return optionList.get(0);
    }

}
