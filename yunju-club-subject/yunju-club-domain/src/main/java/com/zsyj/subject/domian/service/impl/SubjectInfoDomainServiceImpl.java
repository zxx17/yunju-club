package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.domian.convert.SubjectInfoBOConvert;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandler;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandlerFactory;
import com.zsyj.subject.domian.service.ISubjectInfoDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.service.SubjectInfoService;
import com.zsyj.subject.infra.basic.service.SubjectLabelService;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Resource
    private SubjectLabelService subjectLabelService;


    /**
     * 新增题目
     *
     * @param subjectInfoBO bo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectInfoBO));
        }
        //插入题目基本信息
        SubjectInfo subjectInfo = SubjectInfoBOConvert.INSTANCE
                .convertBOToSubjectInfo(subjectInfoBO);
        subjectInfo.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        // 插入subject_info题目基本信息
        subjectInfoService.insert(subjectInfo);
        // 插入对应题目类型的信息（工厂加策略模式）
        SubjectTypeHandler instance = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        subjectInfoBO.setId(subjectInfo.getId());
        instance.add(subjectInfoBO);
        //插入题目mapping映射表 关联信息
        List<Long> categoryIds = subjectInfoBO.getCategoryIds();
        List<Long> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> subjectMappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(categoryId);
                subjectMapping.setLabelId(labelId);
                subjectMapping.setCreateTime(new Date());
                subjectMapping.setUpdateTime(new Date());
                subjectMapping.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
                subjectMappingList.add(subjectMapping);
            });
        });
        subjectMappingService.batchInsert(subjectMappingList);
    }


    /**
     * 查询题目列表
     *
     * @param subjectInfoBO dto
     */
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.getSubjectPage.bo{}", JSONObject.toJSONString(subjectInfoBO));
        }
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectInfoBO.getPageNo());
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        int start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();
        SubjectInfo subjectInfo = SubjectInfoBOConvert.INSTANCE
                .convertBOToSubjectInfo(subjectInfoBO);
        int count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId());
        if (count == 0) {
            return pageResult;
        }

        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
        List<SubjectInfoBO> subjectInfoBOS = SubjectInfoBOConvert.INSTANCE
                .convertToSubjectInfoBOLIst(subjectInfoList);
        subjectInfoBOS.forEach(info -> {
            SubjectMapping subjectMapping = new SubjectMapping();
            subjectMapping.setSubjectId(info.getId());
            assembleLabelName(info, subjectMapping);
        });

        pageResult.setRecords(subjectInfoBOS);
        pageResult.setTotal(count);
        return pageResult;
    }


    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.querySubjectInfo.bo{}", JSONObject.toJSONString(subjectInfoBO));
        }
        SubjectInfo subjectInfo = subjectInfoService.queryById(subjectInfoBO.getId());
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        SubjectOptionBO subjectOptionBO = handler.query(subjectInfo.getId());
        SubjectInfoBO bo = SubjectInfoBOConvert.INSTANCE.convertOptionAndInfoToBo(subjectOptionBO, subjectInfo);

        // 查询题目标签Ids
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        assembleLabelName(bo, subjectMapping);
        return bo;
    }


    private void assembleLabelName(SubjectInfoBO info, SubjectMapping subjectMapping) {
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIds);
        List<String> labelNames = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        info.setLabelName(labelNames);
    }



}
