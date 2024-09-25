package com.zsyj.practice.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.zsyj.practice.api.common.PageInfo;
import com.zsyj.practice.api.common.PageResult;
import com.zsyj.practice.api.enums.CompleteStatusEnum;
import com.zsyj.practice.api.enums.IsDeletedFlagEnum;
import com.zsyj.practice.api.enums.SubjectInfoTypeEnum;
import com.zsyj.practice.api.req.GetPracticeSubjectsReq;
import com.zsyj.practice.api.req.GetUnCompletePracticeReq;
import com.zsyj.practice.api.vo.*;
import com.zsyj.practice.server.dao.*;
import com.zsyj.practice.server.entity.dto.CategoryDTO;
import com.zsyj.practice.server.entity.dto.PracticeSetDTO;
import com.zsyj.practice.server.entity.dto.PracticeSubjectDTO;
import com.zsyj.practice.server.entity.po.*;
import com.zsyj.practice.server.service.PracticeSetService;
import com.zsyj.practice.server.util.DateUtils;
import com.zsyj.practice.server.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/23
 */
@Slf4j
@Service
public class PracticeSetServiceImpl implements PracticeSetService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Resource
    private SubjectMappingDao subjectMappingDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Resource
    private PracticeSetDetailDao practiceSetDetailDao;

    @Resource
    private PracticeSetDao practiceSetDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private PracticeDetailDao practiceDetailDao;

    @Resource
    private PracticeDao practiceDao;

    @Resource
    private SubjectRadioDao subjectRadioDao;

    @Resource
    private SubjectMultipleDao subjectMultipleDao;

    /**
     * 获取专项练习内容
     * 每个一级大类是一个专项练习
     */
    @Override
    public List<SpecialPracticeVO> getSpecialPracticeContent() {
        List<SpecialPracticeVO> specialPracticeVOList = new LinkedList<>();
        // 组装题目类型
        List<Integer> subjectTypeList = new LinkedList<>();
        subjectTypeList.add(SubjectInfoTypeEnum.RADIO.getCode());
        subjectTypeList.add(SubjectInfoTypeEnum.MULTIPLE.getCode());
        subjectTypeList.add(SubjectInfoTypeEnum.JUDGE.getCode());
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setSubjectTypeList(subjectTypeList);
        // 查询所有岗位（大类）
        List<PrimaryCategoryPO> poList = subjectCategoryDao.getPrimaryCategory(categoryDTO);
        if (CollectionUtils.isEmpty(poList)) {
            return specialPracticeVOList;
        }
        // 遍历岗位
        poList.forEach(primaryCategoryPO -> {
            SpecialPracticeVO specialPracticeVO = new SpecialPracticeVO();
            specialPracticeVO.setPrimaryCategoryId(primaryCategoryPO.getParentId());
            // 获取岗位名
            CategoryPO categoryPO = subjectCategoryDao.selectById(primaryCategoryPO.getParentId());
            specialPracticeVO.setPrimaryCategoryName(categoryPO.getCategoryName());
            // 获取岗位下的分类
            CategoryDTO categoryDTOTemp = new CategoryDTO();
            categoryDTOTemp.setCategoryType(2);
            categoryDTOTemp.setParentId(primaryCategoryPO.getParentId());
            List<CategoryPO> smallPoList = subjectCategoryDao.selectList(categoryDTOTemp);
            if (CollectionUtils.isEmpty(smallPoList)) {
                return;
            }
            // 遍历岗位下的分类获取标签 并组装
            List<SpecialPracticeCategoryVO> categoryList = new LinkedList();
            smallPoList.forEach(smallPo -> {
                // 根据小类以及题目类型取查询标签
                List<SpecialPracticeLabelVO> labelVOList = getLabelVOList(smallPo.getId(), subjectTypeList);
                if (CollectionUtils.isEmpty(labelVOList)) {
                    return;
                }
                SpecialPracticeCategoryVO specialPracticeCategoryVO = new SpecialPracticeCategoryVO();
                // 小类id
                specialPracticeCategoryVO.setCategoryId(smallPo.getId());
                // 小类名称
                specialPracticeCategoryVO.setCategoryName(smallPo.getCategoryName());
                List<SpecialPracticeLabelVO> labelList = new LinkedList<>();
                // 组装标签信息
                labelVOList.forEach(labelVo -> {
                    SpecialPracticeLabelVO specialPracticeLabelVO = new SpecialPracticeLabelVO();
                    specialPracticeLabelVO.setId(labelVo.getId());
                    specialPracticeLabelVO.setAssembleId(labelVo.getAssembleId());
                    specialPracticeLabelVO.setLabelName(labelVo.getLabelName());
                    labelList.add(specialPracticeLabelVO);
                });
                specialPracticeCategoryVO.setLabelList(labelList);
                categoryList.add(specialPracticeCategoryVO);
            });
            specialPracticeVO.setCategoryList(categoryList);
            specialPracticeVOList.add(specialPracticeVO);
        });
        return specialPracticeVOList;
    }


    /**
     * 开始练习
     * TODO CR：使用手动事务，避免事务过长
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PracticeSetVO addPractice(PracticeSubjectDTO dto) {
        PracticeSetVO setVO = new PracticeSetVO();
        // 获取套卷题目列表信息
        List<PracticeSubjectDetailVO> practiceList = getPracticeList(dto);
        // 没有题目则直接返回
        if (CollectionUtils.isEmpty(practiceList)) {
            return setVO;
        }
        // 组装参数新增实时生成套题
        PracticeSetPO practiceSetPO = new PracticeSetPO();
        // 实时生成标识
        practiceSetPO.setSetType(1);
        // 获取分类和标签组合信息，然后据此获取题目以及小类和标签信息
        List<String> assembleIds = dto.getAssembleIds();
        Set<Long> categoryIdSet = new HashSet<>();
        assembleIds.forEach(assembleId -> {
            Long categoryId = Long.valueOf(assembleId.split("-")[0]);
            categoryIdSet.add(categoryId);
        });
        // 设置套卷名称 规则为 【xx、xx的专项练习或者xx、xx等专项练习】
        StringBuilder setName = new StringBuilder();
        int i = 1;
        for (Long categoryId : categoryIdSet) {
            if (i > 2) {
                break;
            }
            CategoryPO categoryPO = subjectCategoryDao.selectById(categoryId);
            setName.append(categoryPO.getCategoryName());
            setName.append("、");
            i = i + 1;
        }
        setName.deleteCharAt(setName.length() - 1);
        if (i == 2) {
            setName.append("专项练习");
        } else {
            setName.append("等专项练习");
        }
        // 组装套卷名称
        practiceSetPO.setSetName(setName.toString());
        String labelId = assembleIds.get(0).split("-")[1];
        // 通过标签信息查询大类ID
        SubjectLabelPO labelPO = subjectLabelDao.queryById(Long.valueOf(labelId));
        practiceSetPO.setPrimaryCategoryId(labelPO.getCategoryId());
        practiceSetPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practiceSetPO.setCreateBy(LoginUtil.getLoginId());
        practiceSetPO.setCreatedTime(new Date());
        // 将实时生成的套卷入库
        practiceSetDao.add(practiceSetPO);

        // 获取入库的套卷ID
        Long practiceSetId = practiceSetPO.getId();
        // 对上面获取的套卷题目列表进行信息补充
        List<PracticeSetDetailPO> detailPOList = new ArrayList<>();
        practiceList.forEach(e -> {
            PracticeSetDetailPO detailPO = new PracticeSetDetailPO();
            detailPO.setSetId(practiceSetId);
            detailPO.setSubjectId(e.getSubjectId());
            detailPO.setSubjectType(e.getSubjectType());
            detailPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            detailPO.setCreateBy(LoginUtil.getLoginId());
            detailPO.setCreatedTime(new Date());
            // practiceSetDetailDao.add(detailPO); 使用批量插入
            detailPOList.add(detailPO);
        });
        // 批量插入套卷里的题目信息
        practiceSetDetailDao.insertBatch(detailPOList);
        setVO.setSetId(practiceSetId);
        return setVO;
    }

    /**
     * 获取练习题
     */
    @Override
    public PracticeSubjectListVO getSubjects(GetPracticeSubjectsReq req) {
        PracticeSubjectListVO vo = new PracticeSubjectListVO();
        // 根据套题ID查询套卷信息
        Long setId = req.getSetId();
        List<PracticeSetDetailPO> practiceSetDetailPOS = practiceSetDetailDao.selectBySetId(setId);
        if (CollectionUtils.isEmpty(practiceSetDetailPOS)) {
            return vo;
        }
        // 组装题目信息列表
        List<PracticeSubjectDetailVO> practiceSubjectListVOS = new LinkedList<>();
        // 条件1 当前用户
        String loginId = LoginUtil.getLoginId();
        // 条件2 练习ID 如无->则是新开始的练习
        Long practiceId = req.getPracticeId();
        // 遍历 获取题目ID、类型、是否已作答
        practiceSetDetailPOS.forEach(e -> {
            PracticeSubjectDetailVO practiceSubjectListVO = new PracticeSubjectDetailVO();
            practiceSubjectListVO.setSubjectId(e.getSubjectId());
            practiceSubjectListVO.setSubjectType(e.getSubjectType());
            // 如果practiceId有值，则要判断当前题目用户是否已作答
            if (Objects.nonNull(practiceId)) {
                PracticeDetailPO practiceDetailPO = practiceDetailDao.selectDetail(practiceId, e.getSubjectId(), loginId);
                if (Objects.nonNull(practiceDetailPO) && StringUtils.isNotBlank(practiceDetailPO.getAnswerContent())) {
                    practiceSubjectListVO.setIsAnswer(1);
                } else {
                    practiceSubjectListVO.setIsAnswer(0);
                }
            }
            practiceSubjectListVOS.add(practiceSubjectListVO);
        });
        // 设置套卷题目信息
        vo.setSubjectList(practiceSubjectListVOS);
        // 查询套卷标题
        PracticeSetPO practiceSetPO = practiceSetDao.selectById(setId);
        vo.setTitle(practiceSetPO.getSetName());
        // 如果是新练习，新增练习记录
        if (Objects.isNull(practiceId)) {
            Long newPracticeId = insertUnCompletePractice(setId);
            vo.setPracticeId(newPracticeId);
        }
        // 如果是继续答题，则更新练习记录
        else {
            updateUnCompletePractice(practiceId);
            PracticePO practicePO = practiceDao.selectById(practiceId);
            vo.setTimeUse(practicePO.getTimeUse());
            vo.setPracticeId(practiceId);
        }
        return vo;
    }

    /**
     * 获取题目详情
     * TODO CR：工厂策略模式优化，后面如果分库则不需要，而是使用Feign
     */
    @Override
    public PracticeSubjectVO getPracticeSubject(PracticeSubjectDTO dto) {
        PracticeSubjectVO practiceSubjectVO = new PracticeSubjectVO();
        // 根据题目ID获取题目信息
        SubjectPO subjectPO = subjectDao.selectById(dto.getSubjectId());
        practiceSubjectVO.setSubjectName(subjectPO.getSubjectName());
        practiceSubjectVO.setSubjectType(subjectPO.getSubjectType());
        // 如果是单选，获取每个选项
        if (dto.getSubjectType() == SubjectInfoTypeEnum.RADIO.getCode()) {
            List<PracticeSubjectOptionVO> optionList = new LinkedList<>();
            List<SubjectRadioPO> radioSubjectPOS = subjectRadioDao.selectBySubjectId(subjectPO.getId());
            radioSubjectPOS.forEach(e -> {
                PracticeSubjectOptionVO practiceSubjectOptionVO = new PracticeSubjectOptionVO();
                practiceSubjectOptionVO.setOptionContent(e.getOptionContent());
                practiceSubjectOptionVO.setOptionType(e.getOptionType());
                optionList.add(practiceSubjectOptionVO);
            });
            practiceSubjectVO.setOptionList(optionList);
        }
        // 如果是多选，获取每个选项
        if (dto.getSubjectType() == SubjectInfoTypeEnum.MULTIPLE.getCode()) {
            List<PracticeSubjectOptionVO> optionList = new LinkedList<>();
            List<SubjectMultiplePO> multipleSubjectPOS = subjectMultipleDao.selectBySubjectId(subjectPO.getId());
            multipleSubjectPOS.forEach(e -> {
                PracticeSubjectOptionVO practiceSubjectOptionVO = new PracticeSubjectOptionVO();
                practiceSubjectOptionVO.setOptionContent(e.getOptionContent());
                practiceSubjectOptionVO.setOptionType(e.getOptionType());
                optionList.add(practiceSubjectOptionVO);
            });
            practiceSubjectVO.setOptionList(optionList);
        }
        // 判断题则只有SubjectName（简答题不纳入组卷）
        return practiceSubjectVO;
    }

    /**
     * 获取模拟套题内容
     */
    @Override
    public PageResult<PracticeSetVO> getPreSetContent(PracticeSetDTO dto) {
        PageResult<PracticeSetVO> pageResult = new PageResult<>();
        PageInfo pageInfo = dto.getPageInfo();
        pageResult.setPageNo(pageInfo.getPageNo());
        pageResult.setPageSize(pageInfo.getPageSize());
        int start = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
        // 模拟套题数量
        Integer count = practiceSetDao.getListCount(dto);
        if (count == 0) {
            return pageResult;
        }
        // 获取模拟套题列表
        List<PracticeSetPO> setPOList = practiceSetDao.getSetList(dto,
                start, dto.getPageInfo().getPageSize());
        if (log.isInfoEnabled()) {
            log.info("获取的模拟考卷列表{}", JSON.toJSONString(setPOList));
        }
        // 组装数据
        List<PracticeSetVO> list = new LinkedList<>();
        setPOList.forEach(e -> {
            PracticeSetVO vo = new PracticeSetVO();
            vo.setSetId(e.getId());
            vo.setSetName(e.getSetName());
            vo.setSetHeat(e.getSetHeat());
            vo.setSetDesc(e.getSetDesc());
            list.add(vo);
        });
        pageResult.setRecords(list);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 获取未完成的练题内容
     */
    @Override
    public PageResult<UnCompletePracticeSetVO> getUnCompletePractice(GetUnCompletePracticeReq req) {
        PageResult<UnCompletePracticeSetVO> pageResult = new PageResult<>();
        PageInfo pageInfo = req.getPageInfo();
        pageResult.setPageNo(pageInfo.getPageNo());
        pageResult.setPageSize(pageInfo.getPageSize());
        int start = (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
        String loginId = LoginUtil.getLoginId();
        // 查询当前用户未完成的记录总数
        Integer count = practiceDao.getUnCompleteCount(loginId);
        if (count == 0) {
            return pageResult;
        }
        // 查询当前用户未完成的记录
        List<PracticePO> poList = practiceDao.getUnCompleteList(loginId, start, req.getPageInfo().getPageSize());
        if (log.isInfoEnabled()) {
            log.info("获取未完成的考卷列表{}", JSON.toJSONString(poList));
        }
        // 组装数据
        List<UnCompletePracticeSetVO> list = new LinkedList<>();
        poList.forEach(e -> {
            UnCompletePracticeSetVO vo = new UnCompletePracticeSetVO();
            vo.setSetId(e.getSetId());
            vo.setPracticeId(e.getId());
            vo.setPracticeTime(DateUtils.format(e.getSubmitTime(), "yyyy-MM-dd"));
            PracticeSetPO practiceSetPO = practiceSetDao.selectById(e.getSetId());
            vo.setTitle(practiceSetPO.getSetName());
            list.add(vo);
        });
        pageResult.setRecords(list);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 新增练习记录
     */
    private Long insertUnCompletePractice(Long practiceSetId) {
        PracticePO practicePO = new PracticePO();
        // 设置套卷ID
        practicePO.setSetId(practiceSetId);
        // 设置未完成状态
        practicePO.setCompleteStatus(CompleteStatusEnum.NO_COMPLETE.getCode());
        // 设置时间
        practicePO.setTimeUse("00:00:00");
        // 设置提交时间
        practicePO.setSubmitTime(new Date());
        // 设置正确率
        practicePO.setCorrectRate(new BigDecimal("0.00"));
        // 设置未删除
        practicePO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practicePO.setCreateBy(LoginUtil.getLoginId());
        practicePO.setCreatedTime(new Date());
        // 新增
        practiceDao.insert(practicePO);
        return practicePO.getId();
    }

    /**
     * 更新练习记录
     */
    private void updateUnCompletePractice(Long practiceId) {
        PracticePO practicePO = new PracticePO();
        practicePO.setId(practiceId);
        // 设置提交时间
        practicePO.setSubmitTime(new Date());
        // 根据练习ID更新
        practiceDao.update(practicePO);
    }


    /**
     * 获取套卷题目信息
     * TODO CR：工厂策略模式优化，后面如果分库则不需要，而是使用Feign
     */
    private List<PracticeSubjectDetailVO> getPracticeList(PracticeSubjectDTO dto) {
        List<PracticeSubjectDetailVO> practiceSubjectListVOS = new LinkedList<>();
        // 排除的题目ID列表，避免重复
        List<Long> excludeSubjectIds = new LinkedList<>();
        // 设置题目数量
        // TODO 之后优化到nacos动态配置，目前默认一次练习最多出20题（单选10 多选6 判断4）
        int radioSubjectCount = 10;
        int multipleSubjectCount = 6;
        int judgeSubjectCount = 4;
        int totalSubjectCount = 20;
        //查询单选
        dto.setSubjectCount(radioSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.RADIO.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //查询多选
        dto.setSubjectCount(multipleSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.MULTIPLE.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //查询判断
        dto.setSubjectCount(judgeSubjectCount);
        dto.setSubjectType(SubjectInfoTypeEnum.JUDGE.getCode());
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        //补充题目，刚好到20题则直接返回
        if (practiceSubjectListVOS.size() == totalSubjectCount) {
            return practiceSubjectListVOS;
        }
        Integer remainCount = totalSubjectCount - practiceSubjectListVOS.size();
        // 缺口数量
        dto.setSubjectCount(remainCount);
        // 题目不够就再补充一次单选
        dto.setSubjectType(1);
        assembleList(dto, practiceSubjectListVOS, excludeSubjectIds);
        return practiceSubjectListVOS;
    }

    /**
     * 根据dto（数量、类型） 查询题目信息
     */
    private void assembleList(PracticeSubjectDTO dto,
                              List<PracticeSubjectDetailVO> list,
                              List<Long> excludeSubjectIds) {
        dto.setExcludeSubjectIds(excludeSubjectIds);
        List<SubjectPO> subjectPOList = subjectDao.getPracticeSubject(dto);
        if (CollectionUtils.isEmpty(subjectPOList)) {
            return;
        }
        subjectPOList.forEach(e -> {
            PracticeSubjectDetailVO vo = new PracticeSubjectDetailVO();
            vo.setSubjectId(e.getId());
            vo.setSubjectType(e.getSubjectType());
            excludeSubjectIds.add(e.getId());
            list.add(vo);
        });
    }

    /**
     * 获取标签信息列表
     *
     * @param categoryId      小类id
     * @param subjectTypeList 题目类型
     * @return SpecialPracticeLabelVO List
     */
    private List<SpecialPracticeLabelVO> getLabelVOList(Long categoryId, List<Integer> subjectTypeList) {
        List<LabelCountPO> countPOList = subjectMappingDao.getLabelSubjectCount(categoryId, subjectTypeList);
        if (CollectionUtils.isEmpty(countPOList)) {
            return Collections.emptyList();
        }
        List<SpecialPracticeLabelVO> voList = new LinkedList<>();
        countPOList.forEach(countPo -> {
            SpecialPracticeLabelVO vo = new SpecialPracticeLabelVO();
            vo.setId(countPo.getLabelId());
            vo.setAssembleId(categoryId + "-" + countPo.getLabelId());
            SubjectLabelPO subjectLabelPO = subjectLabelDao.queryById(countPo.getLabelId());
            vo.setLabelName(subjectLabelPO.getLabelName());
            voList.add(vo);
        });
        return voList;
    }
}
