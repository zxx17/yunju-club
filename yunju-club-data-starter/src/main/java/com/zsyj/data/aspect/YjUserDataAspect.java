package com.zsyj.data.aspect;

import com.zsyj.data.context.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/11/9
 **/
@Slf4j
@Aspect
public class YjUserDataAspect {

    private final int level;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    public YjUserDataAspect(int level) {
        this.level = level;
    }

    @Pointcut("execution(* com.zsyj..controller..*(..))")
    public void controllerMethods() {
    }

    @Around("controllerMethods()")
    public Object logMethodDetails(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

        // 执行目标方法
        Object result = joinPoint.proceed();

        Map<String, String > payloadMap= new HashMap<>(8);
        payloadMap.put("username", LoginUtil.getLoginId());
        payloadMap.put("method-name", methodName);
        payloadMap.put("args", Arrays.toString(args));
        payloadMap.put("result", result.toString());
        payloadMap.put("level", String.valueOf(level));
        payloadMap.put("create-time", String.valueOf(System.currentTimeMillis()));
        rocketMQTemplate.convertAndSend("user-operation-details", payloadMap);
        return result;
    }

}
