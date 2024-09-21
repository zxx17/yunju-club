package com.zsyj.subject.infra.basic.service.impl;

import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.common.enums.SubjectInfoTypeEnum;
import com.zsyj.subject.infra.basic.entity.EsSubjectFields;
import com.zsyj.subject.infra.basic.entity.SubjectInfo;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;
import com.zsyj.subject.infra.basic.es.EsIndexInfo;
import com.zsyj.subject.infra.basic.es.EsRestClient;
import com.zsyj.subject.infra.basic.es.EsSearchRequest;
import com.zsyj.subject.infra.basic.es.EsSourceData;
import com.zsyj.subject.infra.basic.service.SubjectEsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/18
 */
@Service
@RequiredArgsConstructor
public class SubjectEsServiceImpl implements SubjectEsService {

    private final EsRestClient esRestClient;

    /**
     * 插入题目数据到es
     *
     * @param subjectInfoEs 题目信息
     * @return boolean
     */
    @Override
    public boolean insert(SubjectInfoEs subjectInfoEs) {
        Map<String, Object> data = convert2EsSourceData(subjectInfoEs);
        EsSourceData esSourceData = new EsSourceData();
        esSourceData.setDocId(subjectInfoEs.getDocId().toString());
        esSourceData.setData(data);
        return esRestClient.insertDoc(getEsIndexInfo(), esSourceData);
    }

    /**
     * 将题目信息转换为es格式 map
     *
     * @param subjectInfoEs es格式题目信息
     * @return map
     */
    private Map<String, Object> convert2EsSourceData(SubjectInfoEs subjectInfoEs) {
        Map<String, Object> data = new HashMap<>();
        data.put(EsSubjectFields.SUBJECT_ID, subjectInfoEs.getSubjectId());
        data.put(EsSubjectFields.DOC_ID, subjectInfoEs.getDocId());
        data.put(EsSubjectFields.SUBJECT_NAME, subjectInfoEs.getSubjectName());
        data.put(EsSubjectFields.SUBJECT_ANSWER, subjectInfoEs.getSubjectAnswer());
        data.put(EsSubjectFields.SUBJECT_TYPE, subjectInfoEs.getSubjectType());
        data.put(EsSubjectFields.CREATE_USER, subjectInfoEs.getCreateUser());
        data.put(EsSubjectFields.CREATE_TIME, subjectInfoEs.getCreateTime());
        return data;
    }

    /**
     * es全文检索题目信息
     *
     * @param subjectInfoEsReq 题目信息
     * @return 全文检索结果
     */
    @Override
    public PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEsReq) {
        PageResult<SubjectInfoEs> pageResult = new PageResult<>();
        // 创建搜索条件
        EsSearchRequest esSearchRequest = createSearchListQuery(subjectInfoEsReq);
        // 进行搜索
        SearchResponse searchResponse = esRestClient.searchWithTermQuery(getEsIndexInfo(), esSearchRequest);

        // 组装数据Record
        List<SubjectInfoEs> subjectInfoEsList = new LinkedList<>();
        SearchHits searchHits = searchResponse.getHits();
        // 未命中
        if (searchHits == null || searchHits.getHits() == null) {
            pageResult.setPageNo(subjectInfoEsReq.getPageNo());
            pageResult.setPageSize(subjectInfoEsReq.getPageSize());
            pageResult.setRecords(subjectInfoEsList);
            pageResult.setTotal(0);
            return pageResult;
        }
        // 命中
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            SubjectInfoEs subjectInfoEs = convertEs2PojoResult(hit);
            if (Objects.nonNull(subjectInfoEs)){
                subjectInfoEsList.add(subjectInfoEs);
            }
        }
        pageResult.setPageNo(subjectInfoEsReq.getPageNo());
        pageResult.setPageSize(subjectInfoEsReq.getPageSize());
        pageResult.setRecords(subjectInfoEsList);
        pageResult.setTotal(Long.valueOf(searchHits.getTotalHits().value).intValue());
        return pageResult;
    }

    /**
     * 将es命中的数据转成SubjectInfoEs
     * @param hit 命中数据
     * @return SubjectInfoEs
     */
    private SubjectInfoEs convertEs2PojoResult(SearchHit hit) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        if (CollectionUtils.isEmpty(sourceAsMap)) {
            return null;
        }
        SubjectInfoEs result = new SubjectInfoEs();
        result.setSubjectId(MapUtils.getLong(sourceAsMap, EsSubjectFields.SUBJECT_ID));
        result.setSubjectName(MapUtils.getString(sourceAsMap, EsSubjectFields.SUBJECT_NAME));

        result.setSubjectAnswer(MapUtils.getString(sourceAsMap, EsSubjectFields.SUBJECT_ANSWER));

        result.setDocId(MapUtils.getLong(sourceAsMap, EsSubjectFields.DOC_ID));
        result.setSubjectType(MapUtils.getInteger(sourceAsMap, EsSubjectFields.SUBJECT_TYPE));
        result.setScore(new BigDecimal(String.valueOf(hit.getScore())).multiply(new BigDecimal("100.00")
                .setScale(2, RoundingMode.HALF_UP)));

        //处理name的高亮
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        HighlightField subjectNameField = highlightFields.get(EsSubjectFields.SUBJECT_NAME);
        if(Objects.nonNull(subjectNameField)){
            Text[] fragments = subjectNameField.getFragments();
            StringBuilder subjectNameBuilder = new StringBuilder();
            for (Text fragment : fragments) {
                subjectNameBuilder.append(fragment);
            }
            result.setSubjectName(subjectNameBuilder.toString());
        }

        //处理答案高亮
        HighlightField subjectAnswerField = highlightFields.get(EsSubjectFields.SUBJECT_ANSWER);
        if(Objects.nonNull(subjectAnswerField)){
            Text[] fragments = subjectAnswerField.getFragments();
            StringBuilder subjectAnswerBuilder = new StringBuilder();
            for (Text fragment : fragments) {
                subjectAnswerBuilder.append(fragment);
            }
            result.setSubjectAnswer(subjectAnswerBuilder.toString());
        }

        return result;
    }


    /**
     * 构建es查询请求条件
     * @param req 请求参数
     * @return EsSearchRequest
     */
    private EsSearchRequest createSearchListQuery(SubjectInfoEs req) {
        // 创建es查询请求对象
        EsSearchRequest esSearchRequest = new EsSearchRequest();
        // 创建布尔查询构建器，用于组合多个查询条件
        BoolQueryBuilder bq = new BoolQueryBuilder();

        // 创建匹配查询条件，查询字段为subjectName，查询值为req.getKeyWord()
        MatchQueryBuilder subjectNameQueryBuilder =
                QueryBuilders.matchQuery(EsSubjectFields.SUBJECT_NAME, req.getKeyWord());

        // 将subjectNameQueryBuilder查询条件添加到布尔查询中，该条件为可选条件
        bq.should(subjectNameQueryBuilder);
        // 设置subjectNameQueryBuilder查询条件的重要性，提高其匹配分数
        subjectNameQueryBuilder.boost(2);

        // 创建匹配查询条件，查询字段为subjectAnswer，查询值为req.getKeyWord()
        MatchQueryBuilder subjectAnswerQueryBuilder =
                QueryBuilders.matchQuery(EsSubjectFields.SUBJECT_ANSWER, req.getKeyWord());

        // 将subjectAnswerQueryBuilder查询条件添加到布尔查询中
        bq.should(subjectAnswerQueryBuilder);

        // 创建匹配查询条件，查询字段为subjectType
        MatchQueryBuilder subjectTypeQueryBuilder =
                QueryBuilders.matchQuery(EsSubjectFields.SUBJECT_TYPE, SubjectInfoTypeEnum.BRIEF.getCode());

        // 将subjectTypeQueryBuilder查询条件添加到布尔查询中，并设置为“必须”关系，即该条件为必选条件
        bq.must(subjectTypeQueryBuilder);
        // 设置布尔查询中至少有一个“应该”关系条件必须匹配
        bq.minimumShouldMatch(1);

        // 创建高亮构建器，设置需要高亮的字段，并设置不需要字段匹配
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        // 设置高亮前缀标签，用于在查询结果中标识高亮部分
        highlightBuilder.preTags("<span style = \"color:red\">");
        // 设置高亮后缀标签，用于结束高亮标识
        highlightBuilder.postTags("</span>");

        // 设置查询请求的布尔查询条件
        esSearchRequest.setBq(bq);
        // 设置查询请求的高亮构建器
        esSearchRequest.setHighlightBuilder(highlightBuilder);
        // 设置查询请求的返回字段
        esSearchRequest.setFields(EsSubjectFields.FIELD_QUERY);
        // 计算并设置查询请求的起始位置，实现分页查询
        esSearchRequest.setFrom((req.getPageNo() - 1) * req.getPageSize());
        // 设置查询请求的页面大小
        esSearchRequest.setSize(req.getPageSize());
        // 设置查询请求不需要滚动上下文
        esSearchRequest.setNeedScroll(false);
        // 返回构建的查询请求对象
        return esSearchRequest;
    }


    /**
     * 获取es集群信息，目前是单集群，后续扩展多集群考虑从Nacos配置文件读取
     *
     * @return EsIndexInfo
     */
    private EsIndexInfo getEsIndexInfo() {
        EsIndexInfo esIndexInfo = new EsIndexInfo();
        esIndexInfo.setClusterName("3ee83eb78b24");
        esIndexInfo.setIndexName("subject_index");
        return esIndexInfo;
    }
}
