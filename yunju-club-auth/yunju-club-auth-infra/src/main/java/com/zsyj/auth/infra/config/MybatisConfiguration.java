package com.zsyj.auth.infra.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.zsyj.auth.infra.interceptor.MybatisPlusAllSqlLogInterceptor;
import com.zsyj.auth.infra.interceptor.SqlStatementInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Mybatis-plus配置.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/2
 **/
@Configuration
public class MybatisConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new MybatisPlusAllSqlLogInterceptor());
        return mybatisPlusInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlStatementInterceptor dataScopeInterceptor() {
        return new SqlStatementInterceptor();
    }



}