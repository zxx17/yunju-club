package com.zsyj.ai.service;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.zsyj.ai.entity.dto.QuestionDTO;
import io.reactivex.Flowable;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
public interface AiModelService {
    Flowable<GenerationResult> aiTalk(QuestionDTO questionDTO);
}
