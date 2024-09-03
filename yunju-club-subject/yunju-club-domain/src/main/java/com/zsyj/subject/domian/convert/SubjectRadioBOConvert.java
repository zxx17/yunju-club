package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
import com.zsyj.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/5
 * <p>
 *
 * </p>
 */

@Mapper
public interface SubjectRadioBOConvert {

    SubjectRadioBOConvert INSTANCE = Mappers.getMapper(SubjectRadioBOConvert.class);


    SubjectRadio convertInfoBOToSubjectRadio(SubjectAnswerBO subjectAnswerBO);


    List<SubjectAnswerBO> convertEntityToBoList(List<SubjectRadio> subjectRadioList);
}
