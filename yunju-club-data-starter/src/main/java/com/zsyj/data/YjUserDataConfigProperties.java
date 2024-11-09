package com.zsyj.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/11/9
 **/
@Data
@ConfigurationProperties(prefix = "yj-data.users-operations")
@EnableConfigurationProperties(YjUserDataConfigProperties.class)
public class YjUserDataConfigProperties {

    /**
     * 收集等级 0-1-2-3 默认0
     */
    private int level;

}
