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
import java.util.List;

import static com.zsyj.subject.common.enums.DeletedFlagEnum.IS_DELETED;
import static com.zsyj.subject.common.enums.DeletedFlagEnum.UN_DELETE;

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

    /**
     * 新增分类
     * @param subjectCategoryBO bo
     */
    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE
                .convertBOToSubjectCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(UN_DELETE.getFlag());
        subjectCategoryService.insert(subjectCategory);
    }

    /**
     * 查询分类
     * @param subjectCategoryBO bo
     * @return list
     */
    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.convertBOToSubjectCategory(subjectCategoryBO);
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryBOConverter.INSTANCE.convertToSubjectCategoryBO(subjectCategoryList);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.queryCategory.subjectCategoryBOList:{}", subjectCategoryBOList);
        }
        return subjectCategoryBOList;
    }

    /**
     * 更新分类
     * @param subjectCategoryBO bo
     * @return boolean
     */
    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.update.bo{}", JSONObject.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.convertBOToSubjectCategory(subjectCategoryBO);
        int isUpdate = subjectCategoryService.update(subjectCategory);
        return isUpdate > 0;
    }


    /**
     * 删除分类
     * @param subjectCategoryBO bo
     * @return boolean
     */
    @Override
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.delete.bo{}", JSONObject.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.convertBOToSubjectCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IS_DELETED.getFlag());
        int isDelete = subjectCategoryService.update(subjectCategory);
        return isDelete > 0;
    }
}



