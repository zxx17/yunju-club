package com.zsyj.data;

import com.zsyj.data.aspect.YjUserDataAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/11/9
 **/
@EnableConfigurationProperties(YjUserDataConfigProperties.class)
@Configuration
public class YjUserDataAutoConfiguration{

    @Resource
    private YjUserDataConfigProperties yjUserDataConfigProperties;

    @Bean
    public YjUserDataAspect yjUserDataAspect() {
        return new YjUserDataAspect(yjUserDataConfigProperties.getLevel());
    }


}
