package com.zsyj.oss.config;

import com.zsyj.oss.adapter.MinioStorageAdapter;
import com.zsyj.oss.adapter.StorageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/3
 **/
@RefreshScope
@Configuration
public class StorageConfig {

    @Value("${storage.service.type}")
    private String storageType;

    @Bean
    @RefreshScope
    public StorageAdapter storageService() {
        if ("minio".equals(storageType)) {
            return new MinioStorageAdapter();
        } else if ("aliyun".equals(storageType)) {
            // TODO 扩展阿里云 腾讯云存储等
            return null;
        } else {
            throw new IllegalArgumentException("未找到对应的文件存储处理器");
        }
    }

}
