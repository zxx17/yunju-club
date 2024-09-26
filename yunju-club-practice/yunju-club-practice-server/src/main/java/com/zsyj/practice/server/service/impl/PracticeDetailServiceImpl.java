package com.zsyj.practice.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.zsyj.practice.api.enums.AnswerStatusEnum;
import com.zsyj.practice.api.enums.CompleteStatusEnum;
import com.zsyj.practice.api.enums.IsDeletedFlagEnum;
import com.zsyj.practice.api.enums.SubjectInfoTypeEnum;
import com.zsyj.practice.api.req.*;
import com.zsyj.practice.api.vo.*;
import com.zsyj.practice.server.dao.*;
import com.zsyj.practice.server.entity.dto.SubjectDTO;
import com.zsyj.practice.server.entity.dto.SubjectDetailDTO;
import com.zsyj.practice.server.entity.dto.SubjectOptionDTO;
import com.zsyj.practice.server.entity.po.*;
import com.zsyj.practice.server.rpc.AuthUserServiceRpc;
import com.zsyj.practice.server.rpc.entity.UserInfo;
import com.zsyj.practice.server.service.PracticeDetailService;
import com.zsyj.practice.server.util.DateUtils;
import com.zsyj.practice.server.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/25
 */
@Slf4j
@Service
public class PracticeDetailServiceImpl implements PracticeDetailService {


    @Resource
    private PracticeDetailDao practiceDetailDao;

    @Resource
    private PracticeSetDao practiceSetDao;

    @Resource
    private PracticeSetDetailDao practiceSetDetailDao;

    @Resource
    private PracticeDao practiceDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private SubjectRadioDao subjectRadioDao;

    @Resource
    private SubjectMultipleDao subjectMultipleDao;

    @Resource
    private SubjectJudgeDao subjectJudgeDao;

    @Resource
    private SubjectMappingDao subjectMappingDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Resource
    private AuthUserServiceRpc authUserServiceRpc;

    /**
     * 提交题目
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitSubject(SubmitSubjectDetailReq req) {
        String timeUse = req.getTimeUse();
        // 容错处理，不可控因素前端控件瞬时传递的值为0
        if (timeUse.equals("0")) {
            timeUse = "000000";
        }
        // 答题用时由于前端框架问题，只能传hhmmss格式，后端做以下转换
        String hour = timeUse.substring(0, 2);
        String minute = timeUse.substring(2, 4);
        String second = timeUse.substring(4, 6);
        PracticePO practicePO = new PracticePO();
        // 设置练习id
        practicePO.setId(req.getPracticeId());
        // 设置用时
        practicePO.setTimeUse(hour + ":" + minute + ":" + second);
        // 设置提交时间
        practicePO.setSubmitTime(new Date());
        // 更新练习记录数据库
        practiceDao.update(practicePO);

        // 练习记录详情
        PracticeDetailPO practiceDetailPO = new PracticeDetailPO();
        practiceDetailPO.setPracticeId(req.getPracticeId());
        practiceDetailPO.setSubjectId(req.getSubjectId());
        practiceDetailPO.setSubjectType(req.getSubjectType());
        String answerContent = "";
        //排序答案（要和正确答案做校验，主要考虑多选的题目）
        if (CollectionUtils.isNotEmpty(req.getAnswerContents())) {
            List<Integer> answerContents = req.getAnswerContents();
            Collections.sort(answerContents);
            answerContent = StringUtils.join(answerContents, ",");
        }
        // 设置答案
        practiceDetailPO.setAnswerContent(answerContent);
        // 根据题目id和类型查询题目正确答案
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectId(req.getSubjectId());
        subjectDTO.setSubjectType(req.getSubjectType());
        //获取正确答案，并判断答案是否正确
        SubjectDetailDTO subjectDetail = getSubjectDetail(subjectDTO);
        StringBuffer correctAnswer = new StringBuffer();
        // 如果是判断
        if (req.getSubjectType().equals(SubjectInfoTypeEnum.JUDGE.getCode())) {
            Integer isCorrect = subjectDetail.getIsCorrect();
            correctAnswer.append(isCorrect);
        }
        // 单选和多选（单选列表长度就是为1，考虑多选的情况）
        else {
            subjectDetail.getOptionList().forEach(e -> {
                if (Objects.equals(e.getIsCorrect(), 1)) {
                    correctAnswer.append(e.getOptionType()).append(",");
                }
            });
            if (correctAnswer.length() > 0) {
                correctAnswer.deleteCharAt(correctAnswer.length() - 1);
            }
        }
        // 判断答案是否正确
        if (Objects.equals(correctAnswer.toString(), answerContent)) {
            practiceDetailPO.setAnswerStatus(1);
        } else {
            practiceDetailPO.setAnswerStatus(0);
        }
        // 填充练习详情字段
        practiceDetailPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practiceDetailPO.setCreateBy(LoginUtil.getLoginId());
        practiceDetailPO.setCreatedTime(new Date());
        // 查询本次练习详情信息
        PracticeDetailPO existDetail = practiceDetailDao.selectDetail(req.getPracticeId(), req.getSubjectId(), LoginUtil.getLoginId());
        // 不存在则新增
        if (Objects.isNull(existDetail)) {
            practiceDetailDao.insertSingle(practiceDetailPO);
        }
        // 存在则更新
        else {
            practiceDetailPO.setId(existDetail.getId());
            practiceDetailDao.update(practiceDetailPO);
        }
        return true;
    }


    /**
     * 提交练题情况（交卷）
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean submit(SubmitPracticeDetailReq req) {
        PracticePO practicePO = new PracticePO();
        Long practiceId = req.getPracticeId();
        Long setId = req.getSetId();
        practicePO.setSetId(setId);
        String timeUse = req.getTimeUse();
        String hour = timeUse.substring(0, 2);
        String minute = timeUse.substring(2, 4);
        String second = timeUse.substring(4, 6);
        practicePO.setTimeUse(hour + ":" + minute + ":" + second);
        practicePO.setSubmitTime(DateUtils.parseStrToDate(req.getSubmitTime()));
        practicePO.setCompleteStatus(CompleteStatusEnum.COMPLETE.getCode());
        practicePO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        practicePO.setCreateBy(LoginUtil.getLoginId());
        practicePO.setCreatedTime(new Date());
        // 查询正确题数
        int correctCount = practiceDetailDao.selectCorrectCount(practiceId);
        // 查询总题数
        List<PracticeSetDetailPO> practiceSetDetailPOS = practiceSetDetailDao.selectBySetId(setId);
        int totalCount = practiceSetDetailPOS.size();
        //计算正确率
        BigDecimal correctRate = new BigDecimal(correctCount).divide(new BigDecimal(totalCount), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100.00"));
        practicePO.setCorrectRate(correctRate);
        PracticePO po = practiceDao.selectById(practiceId);
        if (Objects.isNull(po)) {
            practiceDao.insert(practicePO);
        } else {
            practicePO.setId(practiceId);
            practiceDao.update(practicePO);
        }
        practiceSetDao.updateHeat(setId);
        //补充剩余题目的记录（总的减去已经做的）
        List<PracticeDetailPO> practiceDetailPOList = practiceDetailDao.selectByPracticeId(practiceId);
        List<PracticeSetDetailPO> minusList = practiceSetDetailPOS.stream()
                .filter(item -> !practiceDetailPOList.stream()
                        .map(PracticeDetailPO::getSubjectId)
                        .collect(Collectors.toList())
                        .contains(item.getSubjectId()))
                .collect(Collectors.toList());
        if (log.isInfoEnabled()) {
            log.info("题目差集{}", JSON.toJSONString(minusList));
        }
        if (CollectionUtils.isNotEmpty(minusList)) {
            minusList.forEach(e -> {
                PracticeDetailPO practiceDetailPO = new PracticeDetailPO();
                practiceDetailPO.setPracticeId(practiceId);
                practiceDetailPO.setSubjectType(e.getSubjectType());
                practiceDetailPO.setSubjectId(e.getSubjectId());
                practiceDetailPO.setAnswerStatus(0);
                practiceDetailPO.setAnswerContent("");
                practiceDetailPO.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                practiceDetailPO.setCreatedTime(new Date());
                practiceDetailPO.setCreateBy(LoginUtil.getLoginId());
                practiceDetailDao.insertSingle(practiceDetailPO);
            });
        }
        return true;
    }

    /**
     * 答案解析-每题得分
     */
    @Override
    public List<ScoreDetailVO> getScoreDetail(GetScoreDetailReq req) {
        Long practiceId = req.getPracticeId();
        List<ScoreDetailVO> list = new LinkedList<>();
        // 根据练习ID查answerStatus得分状态
        List<PracticeDetailPO> practiceDetailPOList = practiceDetailDao.selectByPracticeId(practiceId);
        if (CollectionUtils.isEmpty(practiceDetailPOList)) {
            return Collections.emptyList();
        }
        practiceDetailPOList.forEach(po -> {
            ScoreDetailVO scoreDetailVO = new ScoreDetailVO();
            scoreDetailVO.setSubjectId(po.getSubjectId());
            scoreDetailVO.setSubjectType(po.getSubjectType());
            scoreDetailVO.setIsCorrect(po.getAnswerStatus());
            list.add(scoreDetailVO);
        });
        return list;
    }

    /**
     * 答案解析-答题详情
     */
    @Override
    public SubjectDetailVO getSubjectParseDetail(GetSubjectDetailReq req) {
        SubjectDetailVO subjectDetailVO = new SubjectDetailVO();
        // 获取题目id
        Long subjectId = req.getSubjectId();
        // 获取题目类型
        Integer subjectType = req.getSubjectType();
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectId(subjectId);
        subjectDTO.setSubjectType(subjectType);
        // 根据题目ID和类型获取题目详情
        SubjectDetailDTO subjectDetail = getSubjectDetail(subjectDTO);
        // 获取选项列表
        List<SubjectOptionDTO> optionList = subjectDetail.getOptionList();
        // 封装选项
        List<PracticeSubjectOptionVO> optionVOList = new LinkedList<>();
        // 获取正确答案
        List<Integer> correctAnswer = new LinkedList<>();
        // 遍历选项
        if (CollectionUtils.isNotEmpty(optionList)) {
            optionList.forEach(option -> {
                PracticeSubjectOptionVO optionVO = new PracticeSubjectOptionVO();
                optionVO.setOptionType(option.getOptionType());
                optionVO.setOptionContent(option.getOptionContent());
                optionVO.setIsCorrect(option.getIsCorrect());
                optionVOList.add(optionVO);
                // 判断是否正确
                if (option.getIsCorrect() == 1) {
                    correctAnswer.add(option.getOptionType());
                }
            });
        }
        // 判断题逻辑
        if (subjectType.equals(SubjectInfoTypeEnum.JUDGE.getCode())) {
            Integer isCorrect = subjectDetail.getIsCorrect();
            PracticeSubjectOptionVO correctOption = new PracticeSubjectOptionVO();
            correctOption.setOptionType(1);
            correctOption.setOptionContent("正确");
            correctOption.setIsCorrect(isCorrect == 1 ? 1 : 0);
            PracticeSubjectOptionVO errorOptionVO = new PracticeSubjectOptionVO();
            errorOptionVO.setOptionType(2);
            errorOptionVO.setOptionContent("错误");
            errorOptionVO.setIsCorrect(isCorrect == 0 ? 1 : 0);
            optionVOList.add(correctOption);
            optionVOList.add(errorOptionVO);
            correctAnswer.add(subjectDetail.getIsCorrect());
        }
        subjectDetailVO.setOptionList(optionVOList);
        subjectDetailVO.setSubjectParse(subjectDetail.getSubjectParse());
        subjectDetailVO.setSubjectName(subjectDetail.getSubjectName());
        subjectDetailVO.setCorrectAnswer(correctAnswer);
        //自己的答题答案
        List<Integer> respondAnswer = new LinkedList<>();
        PracticeDetailPO practiceDetailPO = practiceDetailDao.selectAnswer(req.getPracticeId(), subjectId);
        String answerContent = practiceDetailPO.getAnswerContent();
        if (StringUtils.isNotBlank(answerContent)) {
            String[] split = answerContent.split(",");
            for (String s : split) {
                respondAnswer.add(Integer.valueOf(s));
            }
        }
        subjectDetailVO.setRespondAnswer(respondAnswer);
        List<SubjectMappingPO> subjectMappingPOList = subjectMappingDao.getLabelIdsBySubjectId(subjectId);
        List<Long> labelIdList = new LinkedList<>();
        subjectMappingPOList.forEach(subjectMappingPO -> {
            labelIdList.add(subjectMappingPO.getLabelId());
        });
        List<String> labelNameList = subjectLabelDao.getLabelNameByIds(labelIdList);
        subjectDetailVO.setLabelNames(labelNameList);
        return subjectDetailVO;
    }

    /**
     * 答案解析-评估报告
     */
    @Override
    public ReportVO getReport(GetReportReq req) {
        ReportVO reportVO = new ReportVO();
        Long practiceId = req.getPracticeId();
        // 根据练习id拿到练习记录
        PracticePO practicePO = practiceDao.selectById(practiceId);
        // 从练习记录获取套卷ID
        Long setId = practicePO.getSetId();
        // 根据套卷ID拿到套卷信息
        PracticeSetPO practiceSetPO = practiceSetDao.selectById(setId);
        // 设置VO的Title
        reportVO.setTitle(practiceSetPO.getSetName());
        // 根据练习ID 拿到答题记录 练习详情
        List<PracticeDetailPO> practiceDetailPOList = practiceDetailDao.selectByPracticeId(practiceId);
        // 为空直接返回
        if (CollectionUtils.isEmpty(practiceDetailPOList)) {
            return null;
        }
        // 获取总题目数
        int totalCount = practiceDetailPOList.size();
        // 获取正确的题目列表
        List<PracticeDetailPO> correctPoList = practiceDetailPOList.stream().filter(e ->
                Objects.equals(e.getAnswerStatus(), AnswerStatusEnum.CORRECT.getCode())).collect(Collectors.toList());
        // 答对的题和总题目数   比如5/8
        reportVO.setCorrectSubject(correctPoList.size() + "/" + totalCount);
        // 构造技能图谱列表
        List<ReportSkillVO> reportSkillVOS = new LinkedList<>();
        // 获取所有题目的所有标签
        Map<Long, Integer> totalMap = getSubjectLabelMap(practiceDetailPOList);
        // 获取答对题目的所有标签
        Map<Long, Integer> correctMap = getSubjectLabelMap(correctPoList);
        // 遍历所有题目的标签，作为技能图谱，然后用根据答对的题目的标签的比例来填充图谱
        totalMap.forEach((key, val) -> {
            ReportSkillVO skillVO = new ReportSkillVO();
            // 根据标签id查询标签名称
            SubjectLabelPO labelPO = subjectLabelDao.queryById(key);
            String labelName = labelPO.getLabelName();
            // 获取答对的题目数量
            Integer correctCount = correctMap.get(key);
            if (Objects.isNull(correctCount)) {
                correctCount = 0;
            }
            skillVO.setName(labelName);
            BigDecimal rate = BigDecimal.ZERO;
            if (!Objects.equals(val, 0)) {
                // 获取答对的题目比例 = 正确题目数量/总题目数量
                rate = new BigDecimal(correctCount.toString()).divide(new BigDecimal(val.toString()), 4,
                        RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            }
            skillVO.setStar(rate);
            reportSkillVOS.add(skillVO);
        });
        if (log.isInfoEnabled()) {
            log.info("获取到的正确率{}", JSON.toJSONString(reportSkillVOS));
        }
        reportVO.setSkill(reportSkillVOS);
        return reportVO;
    }

    /**
     * 获取练习榜
     * TODO 优化循环调用RPC
     *  List<String> userNameList = poList.stream().map(PracticeRankPO::getCreateBy).collect(Collectors.toList());
     *         Map<String, UserInfo> authUserMap = authUserServiceRpc.getUserInfoByList(userNameList);
     */
    @Override
    public List<RankVO> getPracticeRankList() {
        List<RankVO> list = new LinkedList<>();
        // 获取练习总数前5的创建人和练习数量
        List<PracticeRankPO> poList = practiceDetailDao.getPracticeCount();
        if (CollectionUtils.isEmpty(poList)) {
            return list;
        }
        poList.forEach(e -> {
            RankVO rankVO = new RankVO();
            rankVO.setCount(e.getCount());
            UserInfo userInfo = authUserServiceRpc.getUserInfo(e.getCreateBy());
            rankVO.setName(userInfo.getNickName());
            rankVO.setAvatar(userInfo.getAvatar());
            list.add(rankVO);
        });
        return list;
    }

    /**
     * 放弃练习
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean giveUp(Long practiceId) {
        practiceDetailDao.deleteByPracticeId(practiceId);
        practiceDao.deleteById(practiceId);
        return true;
    }

    /**
     * 根据练习详情里的题目ID获取题目对应的标签供给组成技能图谱等报告信息
     * key是labelId value是题目数量
     */
    private Map<Long, Integer> getSubjectLabelMap(List<PracticeDetailPO> practiceDetailPOList) {
        if (CollectionUtils.isEmpty(practiceDetailPOList)) {
            return Collections.emptyMap();
        }
        Map<Long, Integer> map = new HashMap<>();
        practiceDetailPOList.forEach(detail -> {
            Long subjectId = detail.getSubjectId();
            List<SubjectMappingPO> labelIdPO = subjectMappingDao.getLabelIdsBySubjectId(subjectId);
            labelIdPO.forEach(po -> {
                Long labelId = po.getLabelId();
                if (Objects.isNull(map.get(labelId))) {
                    map.put(labelId, 1);
                    return;
                }
                map.put(labelId, map.get(labelId) + 1);
            });
        });
        if (log.isInfoEnabled()) {
            log.info("获取到的题目对应的标签map{}", JSON.toJSONString(map));
        }
        return map;
    }

    /**
     * 根据题目ID和类型查询题目信息（答案）
     * TODO 工厂策略模式优化
     */
    private SubjectDetailDTO getSubjectDetail(SubjectDTO dto) {
        SubjectDetailDTO subjectDetailDTO = new SubjectDetailDTO();
        // 根据id查出subjectPO
        SubjectPO subjectPO = subjectDao.selectById(dto.getSubjectId());
        // 单选
        if (dto.getSubjectType() == SubjectInfoTypeEnum.RADIO.getCode()) {
            List<SubjectOptionDTO> optionList = new LinkedList<>();
            List<SubjectRadioPO> radioSubjectPOS = subjectRadioDao.selectBySubjectId(subjectPO.getId());
            radioSubjectPOS.forEach(e -> {
                SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
                subjectOptionDTO.setOptionContent(e.getOptionContent());
                subjectOptionDTO.setOptionType(e.getOptionType());
                subjectOptionDTO.setIsCorrect(e.getIsCorrect());
                optionList.add(subjectOptionDTO);
            });
            subjectDetailDTO.setOptionList(optionList);
        }
        // 多选
        if (dto.getSubjectType() == SubjectInfoTypeEnum.MULTIPLE.getCode()) {
            List<SubjectOptionDTO> optionList = new LinkedList<>();
            List<SubjectMultiplePO> multipleSubjectPOS = subjectMultipleDao.selectBySubjectId(subjectPO.getId());
            multipleSubjectPOS.forEach(e -> {
                SubjectOptionDTO subjectOptionDTO = new SubjectOptionDTO();
                subjectOptionDTO.setOptionContent(e.getOptionContent());
                subjectOptionDTO.setOptionType(e.getOptionType());
                subjectOptionDTO.setIsCorrect(e.getIsCorrect());
                optionList.add(subjectOptionDTO);
            });
            subjectDetailDTO.setOptionList(optionList);
        }
        // 判断
        if (dto.getSubjectType() == SubjectInfoTypeEnum.JUDGE.getCode()) {
            SubjectJudgePO judgeSubjectPO = subjectJudgeDao.selectBySubjectId(subjectPO.getId());
            subjectDetailDTO.setIsCorrect(judgeSubjectPO.getIsCorrect());
        }
        subjectDetailDTO.setSubjectParse(subjectPO.getSubjectParse());
        subjectDetailDTO.setSubjectName(subjectPO.getSubjectName());
        return subjectDetailDTO;
    }
}
