package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;
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
public interface SubjectCategoryBOConverter {

    SubjectCategoryBOConverter INSTANCE = Mappers.getMapper(SubjectCategoryBOConverter.class);

    SubjectCategory convertBOToSubjectCategory(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> convertToSubjectCategoryBO(List<SubjectCategory> subjectCategoryList);
}
