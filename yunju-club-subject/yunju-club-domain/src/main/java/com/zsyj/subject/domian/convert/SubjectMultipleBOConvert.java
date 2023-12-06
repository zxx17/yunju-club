package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectAnswerBO;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectMultiple;
import com.zsyj.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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


}
