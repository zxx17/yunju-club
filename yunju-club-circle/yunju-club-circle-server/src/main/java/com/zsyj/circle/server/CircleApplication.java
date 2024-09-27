package com.zsyj.circle.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 社区圈子启动类.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/27
 */
@SpringBootApplication
@ComponentScan("com.zsyj")
@MapperScan("com.zsyj.**.dao")
@EnableFeignClients(basePackages = "com.zsyj")
public class CircleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CircleApplication.class, args);
    }
}
