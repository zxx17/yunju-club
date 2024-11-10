package com.zsyj.web.controller.qsbank;

import com.zsyj.subject.api.SubjectFeignService;
import com.zsyj.subject.entity.Result;
import com.zsyj.subject.entity.SubjectCategoryDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题库管理.
 * TODO 目前将逻辑写在 controller层，后续再重构优化
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/10/11
 **/
@RestController
@RequestMapping("/resource-bank/question-manage")
public class QuestionResourceController {

    @Resource
    private SubjectFeignService subjectFeignService;

    /**
     * 分页显示题目列表
     */
    @PostMapping("/list")
    public void list() {

        // 获取所有大类
        SubjectCategoryDTO subjectCategoryDTO = new SubjectCategoryDTO();
        subjectCategoryDTO.setCategoryType(1);
        Result<List<SubjectCategoryDTO>> feignResult = subjectFeignService.queryPrimaryCategory(subjectCategoryDTO);
        // 根据大类查询所有题目列表

        // 返回map 其中key是大分类名称，value是题目列表
    }


    /**
     * 搜索题目
     */



    /**
     * 删除题目
     * TODO 连同C端 漏掉了删除ES里数据的操作！！！！！！！
     */




}
