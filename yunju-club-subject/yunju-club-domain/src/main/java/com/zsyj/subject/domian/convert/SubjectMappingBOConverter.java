package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectMappingBO;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMappingBOConverter {

    SubjectMappingBOConverter INSTANCE = Mappers.getMapper(SubjectMappingBOConverter.class);

    SubjectMapping converterBOToSubjectMapping(SubjectMappingBO subjectMappingBO);

}
