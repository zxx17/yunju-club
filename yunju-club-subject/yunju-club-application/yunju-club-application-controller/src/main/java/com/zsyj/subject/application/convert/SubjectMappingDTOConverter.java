package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectMappingDTO;
import com.zsyj.subject.domian.entity.SubjectMappingBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMappingDTOConverter {

    SubjectMappingDTOConverter INSTANCE = Mappers.getMapper(SubjectMappingDTOConverter.class);


    SubjectMappingBO converterDTOToSubjectMappingBO(SubjectMappingDTO subjectMappingDTO);
}
