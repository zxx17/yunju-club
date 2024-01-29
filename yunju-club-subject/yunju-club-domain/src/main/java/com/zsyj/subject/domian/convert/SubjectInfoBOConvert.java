package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
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
public interface SubjectInfoBOConvert {

    SubjectInfoBOConvert INSTANCE = Mappers.getMapper(SubjectInfoBOConvert.class);


    SubjectInfo convertBOToSubjectInfo(SubjectInfoBO subjectInfoDTO);


    List<SubjectInfoBO> convertToSubjectInfoBOLIst(List<SubjectInfo> subjectInfoList);

}
