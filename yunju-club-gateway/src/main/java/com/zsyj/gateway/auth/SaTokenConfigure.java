package com.zsyj.gateway.auth;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限认证的配置器.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/5
 */
@Slf4j
@Configuration
public class SaTokenConfigure {

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    log.debug("-------- 前端访问path：" + SaHolder.getRequest().getRequestPath());
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/oss/**", r -> StpUtil.checkLogin());
                    SaRouter.match("/subject/**", r -> StpUtil.checkLogin());
                    SaRouter.match("/subject/subject/add", r -> StpUtil.checkPermission("subject:add"));
                })
                ;
    }

}
