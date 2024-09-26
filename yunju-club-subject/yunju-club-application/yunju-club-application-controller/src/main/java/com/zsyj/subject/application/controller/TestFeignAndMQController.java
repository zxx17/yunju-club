package com.zsyj.subject.application.controller;

import com.zsyj.subject.infra.rpc.AuthUserServiceRpc;
import com.zsyj.subject.infra.rpc.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试feign远程调用  subject---auth
 * 测试RocketMQ调用
 */
@RestController
@RequestMapping("/subject/")
@Slf4j
public class TestFeignAndMQController {

    @Resource
    private AuthUserServiceRpc authUserServiceRpc;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("testFeign")
    public String testFeign() {
        UserInfo userInfo = authUserServiceRpc.getUserInfo("testFeign");
        log.info("testFeign.userInfo:{}", userInfo);
        return "success";
    }

    @GetMapping("testMq")
    public String testMq() {
        rocketMQTemplate.convertAndSend("test-topic", "testMq");
        return "success";
    }

}
