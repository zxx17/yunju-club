package com.zsyj.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;

/**
 * Es集群索引信息管理，便于用户集群的索引操作.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/16
 **/
@Data
public class EsIndexInfo implements Serializable {


    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 索引名称
     */
    private String indexName;
}
