package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.domian.convert.SubjectCategoryBOConverter;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.service.ISubjectCategoryDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;
import com.zsyj.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/24
 * <p>
 *
 * </p>
 */

@Slf4j
@Service
public class SubjectCategoryDomainServiceImpl implements ISubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;


    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()){
            log.info("SubjectCategoryDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE
                .convertBOToSubjectCategory(subjectCategoryBO);
        subjectCategoryService.insert(subjectCategory);
    }


}
