package com.zsyj.subject.application.controller;

import com.zsyj.subject.infra.rpc.AuthUserServiceRpc;
import com.zsyj.subject.infra.rpc.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试feign远程调用  subject---auth
 *
 */
@RestController
@RequestMapping("/subject/")
@Slf4j
public class TestFeignController {

    @Resource
    private AuthUserServiceRpc authUserServiceRpc;

    @GetMapping("testFeign")
    public String testFeign() {
        UserInfo userInfo = authUserServiceRpc.getUserInfo("testFeign");
        log.info("testFeign.userInfo:{}", userInfo);
        return "success";
    }

}
