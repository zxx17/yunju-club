package com.zsyj.subject.domian.handler.subject;


import com.google.common.base.Preconditions;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.domian.convert.SubjectRadioBOConvert;
import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;
import com.zsyj.subject.infra.basic.entity.SubjectRadio;
import com.zsyj.subject.infra.basic.service.SubjectRadioService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 单选题目的策略类
 */
@Component
public class RadioTypeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        List<SubjectRadio> subjectRadioList = new LinkedList<>();
        Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoBO.getOptionList()),"单选题答案选项不能为空");
        subjectInfoBO.getOptionList().forEach(option ->{
            SubjectRadio subjectRadio = SubjectRadioBOConvert.INSTANCE.convertInfoBOToSubjectRadio(option);
            subjectRadio.setSubjectId(subjectInfoBO.getId());
            subjectRadio.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
            subjectRadioList.add(subjectRadio);
        });
        subjectRadioService.batchInsert(subjectRadioList);
    }

    @Override
    public SubjectOptionBO query(Long subjectId) {
        SubjectRadio subjectRadio = new SubjectRadio();
        subjectRadio.setSubjectId(subjectId);
        List<SubjectRadio> subjectRadioList = subjectRadioService.queryByCondition(subjectRadio);
        List<SubjectAnswerBO> subjectAnswerBOList = SubjectRadioBOConvert.INSTANCE.convertEntityToBoList(subjectRadioList);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }


}
