package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectCategoryDTO;
import com.zsyj.subject.application.dto.SubjectLabelDTO;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/24
 * <p>
 * 使用mapstruct将BO转成bean
 * </p>
 */

@Mapper
public interface SubjectLabelDTOConverter {

    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);


    SubjectLabelBO convertDTOToSubjectLabelBO(SubjectLabelDTO subjectLabelDTO);
}
