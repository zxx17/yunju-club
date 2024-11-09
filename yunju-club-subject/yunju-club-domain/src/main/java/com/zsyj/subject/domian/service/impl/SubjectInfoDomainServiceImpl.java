package com.zsyj.subject.domian.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsyj.data.context.LoginUtil;
import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.common.util.IdWorkerUtil;
import com.zsyj.subject.domian.convert.SubjectInfoBOConvert;
import com.zsyj.subject.domian.entity.SubjectInfoBO;
import com.zsyj.subject.domian.entity.SubjectOptionBO;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandler;
import com.zsyj.subject.domian.handler.subject.SubjectTypeHandlerFactory;
import com.zsyj.subject.domian.redis.RedisUtil;
import com.zsyj.subject.domian.service.ISubjectInfoDomainService;
import com.zsyj.subject.domian.service.SubjectLikedDomainService;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;
import com.zsyj.subject.infra.basic.entity.SubjectLabel;
import com.zsyj.subject.infra.basic.entity.SubjectMapping;
import com.zsyj.subject.infra.basic.service.*;
import com.zsyj.subject.infra.rpc.AuthUserServiceRpc;
import com.zsyj.subject.infra.rpc.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private SubjectEsService subjectEsService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthUserServiceRpc authUserServiceRpc;

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    private static final String RANK_KEY = "subject_rank";

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
        // 同步到es
        // TODO 优化： 异步提交到ES和Redis
        // TODO 补充createUser逻辑 是否这边放昵称、头像等信息，扩展ES文档映射字段
        String loginId = LoginUtil.getLoginId();
        SubjectInfoEs subjectInfoEs = SubjectInfoEs.builder()
                .subjectId(subjectInfo.getId())
                .docId(new IdWorkerUtil(1, 1, 1).nextId())
                .subjectAnswer(subjectInfoBO.getSubjectAnswer())
                .createTime(new Date().getTime())
                .createUser(loginId)
                .subjectName(subjectInfo.getSubjectName())
                .subjectType(subjectInfo.getSubjectType())
                .build();
        subjectEsService.insert(subjectInfoEs);
        // 同步到redis出题贡献榜，贡献值+1
        redisUtil.addScore(RANK_KEY, loginId, 1);
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
        // 组装标签信息
        assembleLabelName(bo, subjectMapping);
        // 题目点赞数量和当前用户点赞状态
        Boolean liked = subjectLikedDomainService.isLiked(bo.getId().toString(), LoginUtil.getLoginId());
        Integer likedCount = subjectLikedDomainService.getLikedCount(bo.getId().toString());
        bo.setLiked(liked);
        bo.setLikedCount(likedCount);
        // 组装快速刷题 上一题 下一题
        assembleSubjectCursor(subjectInfoBO, bo);
        return bo;
    }


    @Override
    public PageResult<SubjectInfoEs> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO) {
        SubjectInfoEs subjectInfoEs = new SubjectInfoEs();
        subjectInfoEs.setPageNo(subjectInfoBO.getPageNo());
        subjectInfoEs.setPageSize(subjectInfoBO.getPageSize());
        subjectInfoEs.setKeyWord(subjectInfoBO.getKeyWord());
        return subjectEsService.querySubjectList(subjectInfoEs);
    }

    @Override
    public List<SubjectInfoBO> getContributeList() {
        // 取贡献榜前5
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil.rankWithScore(RANK_KEY, 0, 5);
        if (log.isInfoEnabled()) {
            // value是用户ID
            log.info("getContributeList.typedTuples:{}", JSON.toJSONString(typedTuples));
        }
        if (CollectionUtils.isEmpty(typedTuples)) {
            return Collections.emptyList();
        }
        List<SubjectInfoBO> boList = new LinkedList<>();
        typedTuples.forEach(rank -> {
            SubjectInfoBO subjectInfoBO = new SubjectInfoBO();
            subjectInfoBO.setSubjectCount(Objects.requireNonNull(rank.getScore()).intValue());
            // 远程调用用户微服务获取用户信息 TODO 这里用先提前批量获取，然后放入缓存中 减少循环中的远程RPC调用
            UserInfo userInfo = authUserServiceRpc.getUserInfo(rank.getValue());
            subjectInfoBO.setCreateUser(userInfo.getNickName());
            subjectInfoBO.setCreateUserAvatar(userInfo.getAvatar());
            boList.add(subjectInfoBO);
        });
        return boList;
    }

    /**
     * 组装标签名称
     *
     * @param info           题目信息
     * @param subjectMapping mapping信息
     */
    private void assembleLabelName(SubjectInfoBO info, SubjectMapping subjectMapping) {
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIds);
        List<String> labelNames = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        info.setLabelName(labelNames);
    }

    /**
     * 组装快速刷题 上一题 下一题
     * @param subjectInfoBO 原始bo 就是dto前端传来的，要拿到原始的分类和标签id 才能够查询
     * @param bo 题目信息 题目答案、选项、解析等信息，但是分类和标签信息
     */
    private void assembleSubjectCursor(SubjectInfoBO subjectInfoBO, SubjectInfoBO bo) {
        Long categoryId = subjectInfoBO.getCategoryId();
        Long labelId = subjectInfoBO.getLabelId();
        Long subjectId = subjectInfoBO.getId();
        if (Objects.isNull(categoryId) || Objects.isNull(labelId)) {
            return;
        }
        Long nextSubjectId = subjectInfoService.querySubjectIdCursor(subjectId, categoryId, labelId, 1);
        bo.setNextSubjectId(nextSubjectId);
        Long lastSubjectId = subjectInfoService.querySubjectIdCursor(subjectId, categoryId, labelId, 0);
        bo.setLastSubjectId(lastSubjectId);
    }


}
