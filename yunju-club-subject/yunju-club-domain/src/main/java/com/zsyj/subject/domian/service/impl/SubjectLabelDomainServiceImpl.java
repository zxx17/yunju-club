package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.domian.convert.SubjectLabelBOConverter;
import com.zsyj.subject.domian.entity.SubjectLabelBO;
import com.zsyj.subject.domian.service.ISubjectLabelDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.service.SubjectLabelService;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zsyj.subject.common.enums.DeletedFlagEnum.IS_DELETED;
import static com.zsyj.subject.common.enums.DeletedFlagEnum.UN_DELETE;

@Slf4j
@Service
public class SubjectLabelDomainServiceImpl implements ISubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectMappingService subjectMappingService;


    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.add.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(UN_DELETE.getFlag());
        subjectLabel.setCreateTime(new Date());
        subjectLabel.setUpdateTime(new Date());
        int isInsert = subjectLabelService.insert(subjectLabel);
        return isInsert > 0;
    }

    @Override
    public Boolean updateLabel(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.updateLabel.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        subjectLabel.setUpdateTime(new Date());
        int isUpdate = subjectLabelService.update(subjectLabel);
        return isUpdate > 0;
    }

    @Override
    public Boolean deleteLabel(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.deleteLabel.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelBOConverter.INSTANCE
                .convertBOToSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IS_DELETED.getFlag());
        int isUpdate = subjectLabelService.update(subjectLabel);
        return isUpdate > 0;
    }


    /**
     * 根据分类id查询标签
     *
     * @param subjectLabelBO bo 分类id
     * @return json result List<SubjectLabelDTO>
     */
    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.queryLabelByCategoryId.bo{}", JSONObject.toJSONString(subjectLabelBO));
        }
        // 根据分类ID 查询Mapping表对应的LabelIds
        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(UN_DELETE.getFlag());
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryLabelByCategoryId(subjectMapping);
        if (CollectionUtils.isEmpty(subjectMappingList)) {
            return Collections.emptyList();
        }
        // 过去所有LabelIds查询标签信息
        List<Long> labelIds = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabelList = subjectLabelService.querySubjectLabelById(labelIds);
        List<SubjectLabelBO> subjectLabelBOList = new LinkedList<>();
        subjectLabelList.forEach(label -> {
            SubjectLabelBO bo = new SubjectLabelBO();
            bo.setCategoryId(categoryId);
            bo.setLabelName(label.getLabelName());
            bo.setSortNum(label.getSortNum());
            bo.setId(label.getId());
            subjectLabelBOList.add(bo);
        });
        return subjectLabelBOList;
    }
}
