package com.zsyj.subject.application.convert;

import com.zsyj.subject.application.dto.SubjectInfoDTO;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
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
public interface SubjectInfoDTOConvert {

    SubjectInfoDTOConvert INSTANCE = Mappers.getMapper(SubjectInfoDTOConvert.class);


    SubjectInfoBO convertDTOToSubjectInfoBO(SubjectInfoDTO subjectInfoDTO);

    PageResult<SubjectInfoDTO> convertBOToSubjectInfoDTOButPage(PageResult<SubjectInfoBO> subjectInfoBOPageResult);

    SubjectInfoDTO convertBOToSubjectInfoDTO(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoDTO> convertBOListToSubjectInfoDTOList(List<SubjectInfoBO> subjectInfoBOList);

}
