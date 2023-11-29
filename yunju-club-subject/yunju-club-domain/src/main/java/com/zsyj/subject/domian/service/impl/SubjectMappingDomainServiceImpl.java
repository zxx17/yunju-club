package com.zsyj.subject.domian.service.impl;

import com.zsyj.subject.domian.service.ISubjectMappingDomainService;
import com.zsyj.subject.infra.basic.service.SubjectMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubjectMappingDomainServiceImpl implements ISubjectMappingDomainService {

    @Resource
    private SubjectMappingService subjectMappingService;

}
