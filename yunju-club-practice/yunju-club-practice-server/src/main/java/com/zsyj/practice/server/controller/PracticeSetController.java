package com.zsyj.practice.server.controller;

import com.alibaba.fastjson.JSON;
import com.zsyj.practice.api.common.Result;
import com.zsyj.practice.api.vo.SpecialPracticeVO;
import com.zsyj.practice.server.service.PracticeSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 练习套卷控制器.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/23
 */
@RestController
@RequestMapping("/practice/set")
@Slf4j
public class PracticeSetController {

    @Resource
    private PracticeSetService practiceSetService;

    /**
     * 获取专项练习内容
     * 每个一级大类是一个专项练习
     */
    @RequestMapping("getSpecialPracticeContent")
    public Result<List<SpecialPracticeVO>> getSpecialPracticeContent() {
        try {
            List<SpecialPracticeVO> result = practiceSetService.getSpecialPracticeContent();
            if (log.isInfoEnabled()) {
                log.info("getSpecialPracticeContent.result:{}", JSON.toJSONString(result));
            }
            return Result.ok(result);
        } catch (Exception e) {
            log.error("getSpecialPracticeContent.error:{}", e.getMessage(), e);
            return Result.fail("获取专项练习内容失败");
        }
    }



    /**
     * 开始练习
     */



    /**
     * 获取练习题
     */


    /**
     * 获取题目详情
     */


    /**
     * 获取模拟套题内容
     */


    /**
     * 获取未完成的练题内容
     */

}
