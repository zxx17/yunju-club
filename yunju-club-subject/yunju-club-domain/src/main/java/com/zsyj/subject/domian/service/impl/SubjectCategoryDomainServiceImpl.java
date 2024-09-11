package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsyj.subject.domian.convert.SubjectCategoryBOConverter;
import com.zsyj.subject.domian.entity.SubjectCategoryBO;
import com.zsyj.subject.domian.entity.SubjectLabelBO;
import com.zsyj.subject.domian.service.ISubjectCategoryDomainService;
import com.zsyj.subject.domian.util.CacheUtil;
import com.zsyj.subject.infra.basic.entity.SubjectCategory;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.service.SubjectCategoryService;
import com.zsyj.subject.infra.basic.service.SubjectLabelService;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private ThreadPoolExecutor labelThreadPool;

    @Resource
    private CacheUtil cacheUtil;

    /**
     * 新增分类
     *
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
        subjectCategory.setCreateTime(new Date());
        subjectCategory.setUpdateTime(new Date());
        subjectCategoryService.insert(subjectCategory);
    }

    /**
     * 查询分类
     *
     * @param subjectCategoryBO bo
     * @return list
     */
    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.
                convertBOToSubjectCategory(subjectCategoryBO);
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryBOConverter.INSTANCE.
                convertToSubjectCategoryBO(subjectCategoryList);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.queryCategory.subjectCategoryBOList:{}", subjectCategoryBOList);
        }
        // 查询该分类大类下有几道题目
        subjectCategoryBOList.forEach(bo -> {
            Long subjectCount = subjectCategoryService.querySubjectCount(bo.getId());
            bo.setCount(subjectCount);
        });
        return subjectCategoryBOList;
    }

    /**
     * 更新分类
     *
     * @param subjectCategoryBO bo
     * @return boolean
     */
    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryDomainServiceImpl.update.bo{}", JSONObject.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.INSTANCE.convertBOToSubjectCategory(subjectCategoryBO);
        subjectCategory.setUpdateTime(new Date());
        int isUpdate = subjectCategoryService.update(subjectCategory);
        return isUpdate > 0;
    }


    /**
     * 删除分类
     *
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

    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        Integer categoryId = subjectCategoryBO.getId();
        String cacheKey = "categoryAndLabel." + categoryId;
        List<SubjectCategoryBO> subjectCategoryBOS = cacheUtil.getListResult(cacheKey,
                SubjectCategoryBO.class, (key) -> getSubjectCategoryBOS(categoryId));
        return subjectCategoryBOS;
    }

    /**
     * 根据categoryId 获取对应的List<SubjectCategoryBO>
     *
     * @param categoryId 分类ID
     * @return 分类标签BO信息
     */
    private List<SubjectCategoryBO> getSubjectCategoryBOS(Integer categoryId) {
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setParentId(categoryId);
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        if (log.isInfoEnabled()) {
            // 所有的子分类列表
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }
        // 转成BO，利用CompletableFuture查询label的全部信息
        List<SubjectCategoryBO> categoryBOList = SubjectCategoryBOConverter.INSTANCE.convertToSubjectCategoryBO(subjectCategoryList);
        List<CompletableFuture<Map<Integer, List<SubjectLabelBO>>>> completableFutureList = categoryBOList.stream().map(category ->
                CompletableFuture.supplyAsync(() -> getLabelBOList(category), labelThreadPool)
        ).collect(Collectors.toList());

        // CompletableFuture查询label的全部信息获取到之后 将其放到map中，K是分类ID，V是标签数据
        Map<Integer, List<SubjectLabelBO>> map = new HashMap<>();
        completableFutureList.forEach(future -> {
            try {
                Map<Integer, List<SubjectLabelBO>> resultMap = future.get();
                if (!CollectionUtils.isEmpty(resultMap)) {
                    map.putAll(resultMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 组装BO
        categoryBOList.forEach(categoryBO -> {
            if (!CollectionUtils.isEmpty(map.get(categoryBO.getId()))) {
                categoryBO.setLabelBOList(map.get(categoryBO.getId()));
            }
        });
        return categoryBOList;
    }


    /**
     * 根据category信息 到mapping和label中查询label的全部信息
     *
     * @param category 分类
     * @return Map<分类ID ， 标签信息列表>
     */
    private Map<Integer, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBO category) {
        if (log.isInfoEnabled()) {
            log.info("getLabelBOList by:{}", JSON.toJSONString(category));
        }
        // 将categoryId放到mapping实体中进行查询
        Map<Integer, List<SubjectLabelBO>> labelMap = new HashMap<>();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(Long.valueOf(category.getId()));
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        if (CollectionUtils.isEmpty(mappingList)) {
            return null;
        }
        // 获取所有LabelId，进行批量查询
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<SubjectLabelBO> labelBOList = new LinkedList<>();
        labelList.forEach(label -> {
            SubjectLabelBO subjectLabelBO = new SubjectLabelBO();
            subjectLabelBO.setId(label.getId());
            subjectLabelBO.setLabelName(label.getLabelName());
            subjectLabelBO.setCategoryId(label.getCategoryId());
            subjectLabelBO.setSortNum(label.getSortNum());
            labelBOList.add(subjectLabelBO);
        });
        labelMap.put(category.getId(), labelBOList);
        return labelMap;
    }

}



