package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.domian.convert.SubjectInfoBOConvert;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandler;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandlerFactory;
import com.zsyj.subject.domian.service.ISubjectInfoDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.service.SubjectInfoService;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xinxuan Zhuo
 * @version 2023/12/5
 * <p>
 *
 * </p>
 */

@Slf4j
@Service
public class SubjectInfoDomainServiceImpl implements ISubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    /**
     * 新增题目
     * @param subjectInfoBO bo
     */
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectInfoBO));
        }
        //插入题目基本信息
        SubjectInfo subjectInfo = SubjectInfoBOConvert.INSTANCE.convertBOToSubjectInfo(subjectInfoBO);
        subjectInfoService.insert(subjectInfo);
        //插入对应题目类型的信息
        SubjectTypeHandler instance = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        instance.add(subjectInfoBO);
        //插入题目mapping映射表 关联信息
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> subjectMappingList = new LinkedList<>();
        categoryIds.forEach(categoryId->{
            labelIds.forEach(labelId->{
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(categoryId);
                subjectMapping.setLabelId(labelId);
                subjectMapping.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
                subjectMappingList.add(subjectMapping);
            });
        });
        subjectMappingService.batchInsert(subjectMappingList);
    }


    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        return null;
    }
}
