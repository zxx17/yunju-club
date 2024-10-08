package com.zsyj.circle.server.config.sensitive;

import com.zsyj.circle.server.sensitive.WordContext;
import com.zsyj.circle.server.sensitive.WordFilter;
import com.zsyj.circle.server.service.SensitiveWordsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensitiveConfig {

    @Bean
    public WordContext wordContext(SensitiveWordsService service) {
        // 敏感词库 开启自动加载
        return new WordContext(true, service);
    }

    @Bean
    public WordFilter wordFilter(WordContext wordContext) {
        // 敏感词过滤器
        return new WordFilter(wordContext);
    }

}
