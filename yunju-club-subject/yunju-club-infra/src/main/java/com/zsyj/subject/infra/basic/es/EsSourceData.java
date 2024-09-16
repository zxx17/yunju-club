package com.zsyj.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Es查询出来的文档（数据）封装
 */
@Data
public class EsSourceData implements Serializable {

    private String docId;

    private Map<String, Object> data;

}
