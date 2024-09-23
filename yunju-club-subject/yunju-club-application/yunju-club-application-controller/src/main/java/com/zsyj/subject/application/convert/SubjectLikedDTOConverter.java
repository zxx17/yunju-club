package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectLikedDTO;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.domian.entity.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 题目点赞表 dto转换器
 *

 */
@Mapper
public interface SubjectLikedDTOConverter {

    SubjectLikedDTOConverter INSTANCE = Mappers.getMapper(SubjectLikedDTOConverter.class);

    SubjectLikedBO convertDTOToBO(SubjectLikedDTO subjectLikedDTO);

    PageResult<SubjectLikedDTO> convertBOToDTO(PageResult<SubjectLikedBO> subjectLikedBO);

}
