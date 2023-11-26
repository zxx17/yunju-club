package com.zsyj.subject.domian.convert;

import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
}
