package com.zsyj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author Xinxuan Zhuo
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YunJuIoTClubManageApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YunJuIoTClubManageApplication.class, args);
    }
}
