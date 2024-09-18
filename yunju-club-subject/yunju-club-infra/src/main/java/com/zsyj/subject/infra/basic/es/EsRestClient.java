package com.zsyj.subject.infra.basic.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

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

    // es配置
    private final EsConfigProperties esConfigProperties;

    // es集群客户端集合 K 集群名称 V client
    private final static Map<String, RestHighLevelClient> clientMap = new HashMap<>();

    // 请求参数
    private static final RequestOptions COMMON_OPTIONS;

    // 初始化请求参数
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }


    /**
     * 读取Es集训配置，初始化自定义es客户端
     *
     * @throws Exception Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 读取配置
        List<EsConfigProperties.EsClusterConfig> esConfigs = esConfigProperties.getEsConfigs();
        // 判断是否为空
        if (CollectionUtils.isEmpty(esConfigs)) {
            log.warn(">>>>>es.config.is.empty!<<<<<");
            return;
        }
        // 遍历配置
        for (EsConfigProperties.EsClusterConfig esConfig : esConfigs) {
            log.info(">>>>>initialize.es.config.name:{},node:{}", esConfig.getName(), esConfig.getNodes());
            // 初始化client
            RestHighLevelClient restHighLevelClient = initRestClient(esConfig);
            if (restHighLevelClient != null) {
                clientMap.put(esConfig.getName(), restHighLevelClient);
            } else {
                // 构建失败
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

    /**
     * 获取client
     *
     * @param clusterName es集群名称
     * @return RestHighLevelClient
     */
    private RestHighLevelClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }


    /**
     * 插入文档
     *
     * @param esIndexInfo  集群和索引信息
     * @param esSourceData 数据
     * @return boolean
     */
    public boolean insertDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
            indexRequest.source(esSourceData.getData());
            indexRequest.id(esSourceData.getDocId());
            getClient(esIndexInfo.getClusterName()).index(indexRequest, COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            log.error("insertDoc.error.esSourceData:{},error:{}", JSON.toJSONString(esSourceData), e);
        }
        return false;
    }


    /**
     * 更新文档
     *
     * @param esIndexInfo  集群和索引信息
     * @param esSourceData 数据
     * @return boolean
     */
    public boolean updateDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index(esIndexInfo.getIndexName());
            updateRequest.id(esSourceData.getDocId());
            updateRequest.doc(esSourceData.getData());
            getClient(esIndexInfo.getClusterName()).update(updateRequest, COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            log.error("updateDoc.error.esSourceData:{},error:{}", JSON.toJSONString(esSourceData), e);
        }
        return false;
    }

    /**
     * 批量更新
     *
     * @param esIndexInfo      集群和索引信息
     * @param esSourceDataList 数据
     * @return boolean
     */
    public boolean batchUpdateDoc(EsIndexInfo esIndexInfo,
                                  List<EsSourceData> esSourceDataList) {
        try {
            boolean flag = false;
            BulkRequest bulkRequest = new BulkRequest();
            for (EsSourceData esSourceData : esSourceDataList) {
                String docId = esSourceData.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    UpdateRequest updateRequest = new UpdateRequest();
                    updateRequest.index(esIndexInfo.getIndexName());
                    updateRequest.id(esSourceData.getDocId());
                    updateRequest.doc(esSourceData.getData());
                    bulkRequest.add(updateRequest);
                    flag = true;
                }
            }
            if (flag) {
                BulkResponse bulk = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                if (bulk.hasFailures()) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("batchUpdateDoc.exception:{}", e.getMessage(), e);
        }
        return false;
    }


    /**
     * 指定索引名称删除所有文档。
     * @param esIndexInfo es集群和索引信息
     * @return boolean
     */
    public boolean delete(EsIndexInfo esIndexInfo) {
        try {
            DeleteByQueryRequest deleteByQueryRequest =
                    new DeleteByQueryRequest(esIndexInfo.getIndexName());
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).deleteByQuery(
                    deleteByQueryRequest, COMMON_OPTIONS
            );
            long deleted = response.getDeleted();
            log.info("deleted.size:{}", deleted);
            return true;
        } catch (Exception e) {
            log.error("delete.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除文档
     * @param esIndexInfo es集群和索引信息
     * @param docId 文档id
     * @return boolean
     */
    public boolean deleteDoc(EsIndexInfo esIndexInfo, String docId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(esIndexInfo.getIndexName());
            deleteRequest.id(docId);
            DeleteResponse response = getClient(esIndexInfo.getClusterName()).delete(deleteRequest, COMMON_OPTIONS);
            log.info("deleteDoc.response:{}", com.alibaba.fastjson.JSON.toJSONString(response));
            return true;
        } catch (Exception e) {
            log.error("deleteDoc.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 判断文档是否存在
     * @param esIndexInfo es集群和索引信息
     * @param docId 文档id
     * @return boolean
     */
    public  boolean isExistDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            return getClient(esIndexInfo.getClusterName()).exists(getRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据文档id获取文档
     * @param esIndexInfo es集群和索引信息
     * @param docId 文档id
     * @return Map
     */
    public  Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            return response.getSource();
        } catch (Exception e) {
            log.error("getDocById.exception:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据文档id和字段获取文档
     * @param esIndexInfo 集群和索引信息
     * @param docId 文档信息
     * @param fields 字段信息
     * @return map
     */
    public  Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId,
                                                 String[] fields) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, fields, null);
            getRequest.fetchSourceContext(fetchSourceContext);
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            Map<String, Object> source = response.getSource();
            return source;
        } catch (Exception e) {
            log.error("getDocById.exception:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * es搜索
     * @param esIndexInfo es集群和索引信息
     * @param esSearchRequest 搜索条件
     * @return SearchResponse
     */
    public SearchResponse searchWithTermQuery(EsIndexInfo esIndexInfo,
                                                     EsSearchRequest esSearchRequest) {
        try {
            BoolQueryBuilder bq = esSearchRequest.getBq();
            String[] fields = esSearchRequest.getFields();
            int from = esSearchRequest.getFrom();
            int size = esSearchRequest.getSize();
            Long minutes = esSearchRequest.getMinutes();
            Boolean needScroll = esSearchRequest.getNeedScroll();
            String sortName = esSearchRequest.getSortName();
            SortOrder sortOrder = esSearchRequest.getSortOrder();


            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(bq);

            searchSourceBuilder.fetchSource(fields, null).from(from).size(size);

            if (Objects.nonNull(esSearchRequest.getHighlightBuilder())) {
                searchSourceBuilder.highlighter(esSearchRequest.getHighlightBuilder());
            }

            if (StringUtils.isNotBlank(sortName)) {
                searchSourceBuilder.sort(sortName);
            }

            searchSourceBuilder.sort(new ScoreSortBuilder().order(sortOrder == null? SortOrder.DESC:sortOrder));

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.searchType(SearchType.DEFAULT);
            searchRequest.indices(esIndexInfo.getIndexName());
            searchRequest.source(searchSourceBuilder);
            if (needScroll) {
                Scroll scroll = new Scroll(TimeValue.timeValueMinutes(minutes));
                searchRequest.scroll(scroll);
            }
            SearchResponse search = getClient(esIndexInfo.getClusterName()).search(searchRequest, COMMON_OPTIONS);
            return search;
        } catch (Exception e) {
            log.error("searchWithTermQuery.exception:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量插入文档
     * @param esIndexInfo es集群和索引信息
     * @param esSourceDataList 数据列表
     * @return boolean
     */
    public boolean batchInsertDoc(EsIndexInfo esIndexInfo, List<EsSourceData> esSourceDataList) {
        if (log.isInfoEnabled()) {
            log.info("批量新增ES:" + esSourceDataList.size());
            log.info("indexName:" + esIndexInfo.getIndexName());
        }
        try {
            boolean flag = false;
            BulkRequest bulkRequest = new BulkRequest();

            for (EsSourceData source : esSourceDataList) {
                String docId = source.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
                    indexRequest.id(docId);
                    indexRequest.source(source.getData());
                    bulkRequest.add(indexRequest);
                    flag = true;
                }
            }


            if (flag) {
                BulkResponse response = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                if (response.hasFailures()) {
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("batchInsertDoc.error", e);
        }

        return true;
    }

    /**
     * 更新文档
     * @param esIndexInfo es集群和索引信息
     * @param queryBuilder 查询构建器，用于定义要更新文档的筛选条件。
     * @param script  脚本对象，定义了更新操作的具体逻辑。
     * @param batchSize 批处理大小，控制每次处理的文档数量，以优化性能。
     * @return boolean
     */
    public boolean updateByQuery(EsIndexInfo esIndexInfo, QueryBuilder queryBuilder, Script script, int batchSize) {
        if (log.isInfoEnabled()) {
            log.info("updateByQuery.indexName:" + esIndexInfo.getIndexName());
        }
        try {
            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(esIndexInfo.getIndexName());
            updateByQueryRequest.setQuery(queryBuilder);
            updateByQueryRequest.setScript(script);
            updateByQueryRequest.setBatchSize(batchSize);
            updateByQueryRequest.setAbortOnVersionConflict(false);
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName()).updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            List<BulkItemResponse.Failure> failures = response.getBulkFailures();
        } catch (Exception e) {
            log.error("updateByQuery.error", e);
        }
        return true;
    }

    /**
     * ik中文分词方法
     */
    public  List<String> getAnalyze(EsIndexInfo esIndexInfo, String text) throws Exception {
        List<String> list = new ArrayList<String>();
        Request request = new Request("GET", "_analyze");
        JSONObject entity = new JSONObject();
        entity.put("analyzer", "ik_smart");
        entity.put("text", text);
        request.setJsonEntity(entity.toJSONString());
        Response response = getClient(esIndexInfo.getClusterName()).getLowLevelClient().performRequest(request);
        JSONObject tokens = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        JSONArray arrays = tokens.getJSONArray("tokens");
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = com.alibaba.fastjson.JSON.parseObject(arrays.getString(i));
            list.add(obj.getString("token"));
        }
        return list;
    }

}
