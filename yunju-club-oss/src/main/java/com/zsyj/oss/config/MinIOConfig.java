package com.zsyj.oss.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/3
 **/
@Slf4j
@Configuration
public class MinIOConfig {
    /**
     * minioUrl
     */
    @Value("${minio.url}")
    private String url;

    /**
     * minio账户
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * minio密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 构造minioClient
     */
    @Bean
    public MinioClient getMinioClient() {

        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

}
