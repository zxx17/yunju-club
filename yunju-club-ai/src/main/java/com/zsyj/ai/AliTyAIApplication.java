package com.zsyj.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ai助手仅使用阿里系的产品，通义支持多个大模型.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@MapperScan("com.zsyj.ai.mapper")
@SpringBootApplication
public class AliTyAIApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliTyAIApplication.class, args);
    }
}
