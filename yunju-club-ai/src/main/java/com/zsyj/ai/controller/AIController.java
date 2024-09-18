package com.zsyj.ai.controller;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.utils.JsonUtils;
import com.zsyj.ai.entity.Result;
import com.zsyj.ai.entity.ResultCodeEnum;
import com.zsyj.ai.entity.dto.QuestionDTO;
import com.zsyj.ai.service.AiModelService;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Slf4j
@RestController
@RequestMapping("/aiModel")
public class AIController {

    @Resource
    private AiModelService aiModelService;

    @PostMapping("/send")
    public Flux<ServerSentEvent<String>> aiTalk(@RequestBody QuestionDTO questionDTO, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("AIController.aiTalk.questionDTO:{}", JsonUtils.toJson(questionDTO));
        }
        Flowable<GenerationResult> result = aiModelService.aiTalk(questionDTO);
        return Flux.from(result)
                .map(m -> {
                    String content = m.getOutput().getChoices().get(0).getMessage().getContent();
                    return ServerSentEvent.<String>builder().data(content).build();
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnError(e -> {
                    // 错误处理
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", ResultCodeEnum.FAIL.code);
                    map.put("message", "has mistake " + e.getMessage());
                    try {
                        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
                        response.setCharacterEncoding("UTF-8");
                        response.getOutputStream().print(JsonUtils.toJson(map));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

}
