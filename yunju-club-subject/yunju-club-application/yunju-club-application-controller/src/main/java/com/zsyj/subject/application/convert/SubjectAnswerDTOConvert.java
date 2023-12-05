package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectAnswerDTO;
import com.zsyj.subject.domian.entity.SubjectAnswerBO;
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
public interface SubjectAnswerDTOConvert {

    SubjectAnswerDTOConvert INSTANCE = Mappers.getMapper(SubjectAnswerDTOConvert.class);

    List<SubjectAnswerBO> convertDTOTOSubjectAnswerBO(List<SubjectAnswerDTO> subjectAnswerDTOList);


}
