package com.zsyj.web.core.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/13
 */
@Configuration
public class FeignConfig {

    /**
     * fegin拦截器,调用方传递token
     *
     * @return RequestInterceptor
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("loginId", "superAdmin");
        };
    }

}
