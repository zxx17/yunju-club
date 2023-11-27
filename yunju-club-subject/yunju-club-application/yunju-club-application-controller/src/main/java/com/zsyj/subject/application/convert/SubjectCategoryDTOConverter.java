package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectCategoryDTO;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
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
public interface SubjectCategoryDTOConverter {

    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO convertDTOToSubjectCategoryBO(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertBOToSubjectCategoryDTO(List<SubjectCategoryBO> subjectCategoryBOList);
}
