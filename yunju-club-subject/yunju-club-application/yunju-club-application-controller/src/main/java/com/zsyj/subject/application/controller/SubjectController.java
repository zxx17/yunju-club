package com.zsyj.subject.application.controller;

import com.zsyj.subject.infra.basic.entity.SubjectCategory;
import com.zsyj.subject.infra.basic.service.SubjectCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author Xinxuan Zhuo
 * @version 2023/11/23
 * <p>
 *
 * </p>
 */

@RestController
public class SubjectController {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @GetMapping("/test")
    public String test() {

        SubjectCategory subjectCategory = subjectCategoryService.queryById(1);
        if (subjectCategory == null) {
            return "hello world";
        } else {
            System.out.println(subjectCategory);
            return subjectCategory.getCategoryName();
        }

    }
}
