package com.zsyj.subject.infra.basic.service.impl;

import com.zsyj.subject.common.entity.PageResult;
import com.zsyj.subject.infra.basic.entity.EsSubjectFields;
import com.zsyj.subject.infra.basic.entity.SubjectInfoEs;
import com.zsyj.subject.infra.basic.es.EsIndexInfo;
import com.zsyj.subject.infra.basic.es.EsRestClient;
import com.zsyj.subject.infra.basic.es.EsSourceData;
import com.zsyj.subject.infra.basic.service.SubjectEsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public boolean insert(SubjectInfoEs subjectInfoEs) {
        Map<String, Object> data = convert2EsSourceData(subjectInfoEs);
        EsSourceData esSourceData = new EsSourceData();
        esSourceData.setDocId(subjectInfoEs.getDocId().toString());
        esSourceData.setData(data);
        return esRestClient.insertDoc(getEsIndexInfo(), esSourceData);
    }

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

    @Override
    public PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs) {
        return null;
    }


    private EsIndexInfo getEsIndexInfo() {
        EsIndexInfo esIndexInfo = new EsIndexInfo();
        esIndexInfo.setClusterName("3ee83eb78b24");
        esIndexInfo.setIndexName("subject_index");
        return esIndexInfo;
    }
}
