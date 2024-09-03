package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectBrief;
import com.zsyj.subject.infra.basic.entity.SubjectJudge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/6
 * <p>
 *
 * </p>
 */

@Mapper
public interface SubjectJudgeBOConvert {

    SubjectJudgeBOConvert INSTANCE = Mappers.getMapper(SubjectJudgeBOConvert.class);

    List<SubjectAnswerBO> convertEntityToBoList(List<SubjectJudge> subjectJudgeList);


}
