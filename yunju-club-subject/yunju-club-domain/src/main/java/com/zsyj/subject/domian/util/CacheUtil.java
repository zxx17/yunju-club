package com.zsyj.subject.domian.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存工具类.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/11
 */
@Component
public class CacheUtil<K, V> {

    private final Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();

    public List<V> getListResult(String cacheKey, Class<V> clazz,
                             Function<String, List<V>> function) {
        List<V> resultList = new ArrayList<>();
        String content = localCache.getIfPresent(cacheKey);
        if (StringUtils.isNotBlank(content)) {
            resultList = JSON.parseArray(content, clazz);
        } else {
            resultList = function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(resultList)) {
                localCache.put(cacheKey, JSON.toJSONString(resultList));
            }
        }
        return resultList;
    }


    public Map<K, V> getMapResult(String cacheKey,
                                  Function<String, Map<K, V>> function) {
        Map<K, V> resultMap = new HashMap<>();
        String content = localCache.getIfPresent(cacheKey);
        if (content != null && !content.trim().isEmpty()) {
            TypeReference<Map<K, V>> typeRef = new TypeReference<Map<K, V>>() {};
            resultMap = JSON.parseObject(content, typeRef);
        } else {
            resultMap = function.apply(cacheKey);
            if (resultMap != null && !resultMap.isEmpty()) {
                localCache.put(cacheKey, JSON.toJSONString(resultMap));
            }
        }
        return resultMap;
    }



}
