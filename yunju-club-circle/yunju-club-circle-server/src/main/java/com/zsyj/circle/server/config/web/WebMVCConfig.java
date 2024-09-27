package com.zsyj.circle.server.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zsyj.practice.server.config.interceptor.LoginInterceptor;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 对mvc返回的json进行处理的相关配置.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/2
 **/
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {

    /**
     * 添加MappingJackson2HttpMessageConverter转换器
     */
    @Override
    protected void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(mappingJackson2HttpMessageConverter());
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/doLogin");
    }


    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
    }

    /**
     * 自定义mappingJackson2HttpMessageConverter
     * 目前实现：空值忽略，空字段可返回
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性（将NULL值隐藏）
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


}
