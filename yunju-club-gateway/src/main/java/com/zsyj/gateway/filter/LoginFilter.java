package com.zsyj.gateway.filter;

import com.zsyj.gateway.redis.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 登录拦截器
 */
@Slf4j
@Component
public class LoginFilter implements GlobalFilter{

    @Resource
    private RedisUtil redisUtil;

    private static final String LOGIN_PATH = "/user/doLogin";
    private static final String SATOKEN_HEADER = "satoken";
    private static final String TOKEN_PREFIX = "satoken:login:token:";
    private static final String HEADER_TOKEN_PREFIX = "jichi ";

    @Override
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        log.info("LoginFilter.filter - URL: {}, Method: {}", url, request.getMethod());

        if (LOGIN_PATH.equals(url)) {
            return chain.filter(exchange);
        }

        String tokenHeader = request.getHeaders().getFirst(SATOKEN_HEADER);
        if (tokenHeader == null) {
            log.warn("Token header not found");
            return chain.filter(exchange);
        }

        String cleanToken = tokenHeader.replace(HEADER_TOKEN_PREFIX, "");
        String key = TOKEN_PREFIX + cleanToken;
        String loginId = redisUtil.get(key);

        if (loginId == null) {
            log.warn("Login ID not found for token: {}", cleanToken);
            return chain.filter(exchange);
        }

        mutate.header("loginId", loginId);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }
}