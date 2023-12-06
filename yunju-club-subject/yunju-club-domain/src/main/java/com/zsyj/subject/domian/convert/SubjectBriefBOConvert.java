package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.infra.basic.entity.SubjectBrief;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/6
 * <p>
 *
 * </p>
 */

@Mapper
public interface SubjectBriefBOConvert {

    SubjectBriefBOConvert INSTANCE = Mappers.getMapper(SubjectBriefBOConvert.class);

    SubjectBrief convertInfoBOToSubjectBrief(SubjectInfoBO subjectInfoBO);


}
