package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectLabelBO;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
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
public interface SubjectLabelBOConverter {

    SubjectLabelBOConverter INSTANCE = Mappers.getMapper(SubjectLabelBOConverter.class);

    SubjectLabel convertBOToSubjectLabel(SubjectLabelBO subjectLabelBO);

    List<SubjectLabelBO> convertLabelToBoList(List<SubjectLabel> subjectLabelList);

}
