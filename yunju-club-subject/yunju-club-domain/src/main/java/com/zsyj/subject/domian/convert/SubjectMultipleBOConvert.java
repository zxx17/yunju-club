package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.infra.basic.entity.SubjectMultiple;
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
public interface SubjectMultipleBOConvert {

    SubjectMultipleBOConvert INSTANCE = Mappers.getMapper(SubjectMultipleBOConvert.class);


    SubjectMultiple convertInfoBOToSubjectMultiple(SubjectAnswerBO subjectAnswerBO);


    List<SubjectAnswerBO> convertEntityToBoList(List<SubjectMultiple> subjectMultipleList);

}
