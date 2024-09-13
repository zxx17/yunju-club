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
@RequestMapping("/subject/category")
@Slf4j
public class TestFeignController {

    @Resource
    private AuthUserServiceRpc authUserServiceRpc;

    @GetMapping("testFeign")
    public void testFeign() {
        UserInfo userInfo = authUserServiceRpc.getUserInfo("oejvp6DGK3-G8-IFEJO4POcZudME");
        log.info("testFeign.userInfo:{}", userInfo);
    }

}
