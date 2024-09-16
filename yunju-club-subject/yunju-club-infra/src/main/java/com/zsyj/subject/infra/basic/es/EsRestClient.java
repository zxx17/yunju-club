package com.zsyj.subject.infra.basic.es;

import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义Es客户端.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/16
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class EsRestClient implements InitializingBean {


    private final EsConfigProperties esConfigProperties;

    private final static Map<String, RestHighLevelClient> clientMap = new HashMap<>();


    /**
     * 读取Es集训配置，初始化自定义es客户端
     *
     * @throws Exception Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<EsConfigProperties.EsClusterConfig> esConfigs = esConfigProperties.getEsConfigs();
        if (CollectionUtils.isEmpty(esConfigs)){
            log.warn(">>>>>es.config.is.empty!<<<<<");
            return;
        }
        for (EsConfigProperties.EsClusterConfig esConfig : esConfigs) {
            log.info(">>>>>initialize.es.config.name:{},node:{}", esConfig.getName(), esConfig.getNodes());
            RestHighLevelClient restHighLevelClient = initRestClient(esConfig);
            if (restHighLevelClient != null) {
                clientMap.put(esConfig.getName(), restHighLevelClient);
            } else {
                log.error("es.config.name:{},node:{}.initError<<<<<", esConfig.getName(), esConfig.getNodes());
            }
        }
        log.info("initialize.es.successful!<<<<<");
    }

    /**
     * 初始化RestClient
     *
     * @param esClusterConfig es集群配置
     * @return RestHighLevelClient
     */
    private RestHighLevelClient initRestClient(EsConfigProperties.EsClusterConfig esClusterConfig) {
        try {
            // 获取该es集群的所有节点
            String[] ipPortArr = esClusterConfig.getNodes().split(",");
            // 分割节点的ip和端口，放入httpHostList
            List<HttpHost> httpHostList = new ArrayList<>(ipPortArr.length);
            for (String ipPort : ipPortArr) {
                String[] ipPortInfo = ipPort.split(":");
                if (ipPortInfo.length == 2) {
                    HttpHost httpHost = new HttpHost(ipPortInfo[0], NumberUtils.toInt(ipPortInfo[1]));
                    httpHostList.add(httpHost);
                }
            }
            // 把httpHostList转成数组
            HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
            httpHostList.toArray(httpHosts);
            // 构建该集群的client
            RestClientBuilder builder = RestClient.builder(httpHosts);
            return new RestHighLevelClient(builder);
        } catch (Exception e) {
            return null;
        }
    }
}
