package com.zsyj.ai.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Configuration
public class AiConfiguration {

    @Bean
    public Generation generation() {
        return new Generation();
    }
}

