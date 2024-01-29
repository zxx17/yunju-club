package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectInfoDTO;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
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
public interface SubjectInfoDTOConvert {

    SubjectInfoDTOConvert INSTANCE = Mappers.getMapper(SubjectInfoDTOConvert.class);


    SubjectInfoBO convertDTOToSubjectInfoBO(SubjectInfoDTO subjectInfoDTO);

    PageResult<SubjectInfoDTO> convertBOToSubjectInfoDTOButPage(PageResult<SubjectInfoBO> subjectInfoBOPageResult);

    SubjectInfoDTO convertBOToSubjectInfoDTO(SubjectInfoBO subjectInfoBO);


}
