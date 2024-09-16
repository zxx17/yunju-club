package com.zsyj.subject.infra.basic.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Es集群配置.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/16
 */
@Data
@Component
@ConfigurationProperties(prefix = "es.cluster")
public class EsConfigProperties {

    private List<EsClusterConfig> esConfigs = new ArrayList<>();

    @Data
    public static class EsClusterConfig implements Serializable {
        /**
         * 集群名称
         */
        private String name;

        /**
         * 集群节点
         */
        private String nodes;
    }
}
