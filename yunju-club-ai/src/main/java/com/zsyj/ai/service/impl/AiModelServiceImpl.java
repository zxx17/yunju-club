package com.zsyj.ai.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.zsyj.ai.entity.AuthUserOperLog;
import com.zsyj.ai.entity.dto.QuestionDTO;
import com.zsyj.ai.entity.enums.DeletedFlagEnum;
import com.zsyj.ai.entity.enums.OperationTypeEnum;
import com.zsyj.ai.mapper.AuthUserOperLogDao;
import com.zsyj.ai.service.AiModelService;
import com.zsyj.ai.util.LoginUtil;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Service
@RequiredArgsConstructor
public class AiModelServiceImpl implements AiModelService {

    @Value("${ali-ai.key}")
    private String appKey;

    private final Generation generation;

    private final AuthUserOperLogDao authUserOperLogDao;

    @Override
    @SneakyThrows
    public Flowable<GenerationResult> aiTalk(QuestionDTO questionDTO) {
        Boolean useHistoryData = questionDTO.getUseHistoryData();
        GenerationParam generationParam;
        if (useHistoryData) {
            // 使用历史数据
            generationParam = getGenerationParam(buildHistoryMessage(questionDTO.getContext()));
        } else {
            // 不使用历史数据
            String attention = "  你只要给出结果就行！注意不要md格式！";
            generationParam = getGenerationParam(questionDTO.getContext() + attention);
        }
        return generation.streamCall(generationParam);
    }

    /**
     * 构建通义千问参数对象
     *
     * @param question 问题内容
     * @return GenerationParam
     */
    private GenerationParam getGenerationParam(String question) {
        // 构建消息对象
        Message message = Message.builder().role(Role.USER.getValue()).content(question).build();

        // 构建通义千问参数对象
        return GenerationParam.builder()
                .model(Generation.Models.QWEN_PLUS)
                .messages(Collections.singletonList(message))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .apiKey(appKey)
                .build();
    }

    /**
     * 结合用户的操作日志，与问题，构建合理的请求参数
     *
     * @param question 问题内容
     * @return String
     */
    private String buildHistoryMessage(String question) {
        String loginId = LoginUtil.getLoginId();
        AuthUserOperLog authUserOperLog = new AuthUserOperLog();
        authUserOperLog.setUserName(loginId);
        authUserOperLog.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        List<AuthUserOperLog> authUserOperLogDOList = authUserOperLogDao.queryByCondition(authUserOperLog);
        if (CollectionUtils.isEmpty(authUserOperLogDOList)) {
            return question;
        }
        // 只取最近10条
        authUserOperLogDOList = authUserOperLogDOList.subList(0, Math.min(10, authUserOperLogDOList.size()));
        StringBuilder sb = new StringBuilder();
        sb.append("以下是").append(loginId).append("在物联网技术社区的全部操作记录，请你阅读完之后，回答他的问题，你只要给出结果就行，不管提供的是什么，注意不要md格式。他的问题是：").append(question).append("\n");
        authUserOperLogDOList.forEach(authUserOperLogDO -> {
            sb.append("操作编号：");
            sb.append(authUserOperLogDO.getId());
            sb.append("\n操作类型：");
            sb.append(OperationTypeEnum.getDescByType(authUserOperLogDO.getOperationType()));
            sb.append("\n操作内容：");
            sb.append(authUserOperLogDO.getDetails());
            sb.append("\n操作结果：");
            sb.append(authUserOperLogDO.getOperationResult());
            sb.append("\n\n");
        });
        return sb.toString();
    }


}
