package com.zsyj.subject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Xinxuan Zhuo
 * @version 2023/11/23
 * <p>
 *  刷题模块微服务启动类
 * </p>
 */

@SpringBootApplication
@ComponentScan("com.zsyj")
@MapperScan("com.zsyj.**.mapper")
public class SubjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectApplication.class, args);
    }

}
