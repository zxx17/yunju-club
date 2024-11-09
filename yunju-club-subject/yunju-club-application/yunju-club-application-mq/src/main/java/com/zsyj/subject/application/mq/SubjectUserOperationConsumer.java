package com.zsyj.subject.application.mq;

import com.zsyj.subject.common.enums.DeletedFlagEnum;
import com.zsyj.subject.infra.basic.entity.AuthUserOperLog;
import com.zsyj.subject.infra.basic.mapper.AuthUserOperLogDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * subject服务用户操作日志收集消费者.
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/11/9
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "user-operation-details", consumerGroup = "subject-user-operation-details-consumer")
public class SubjectUserOperationConsumer implements RocketMQListener<Map<String, String>> {

    @Resource
    private AuthUserOperLogDao authUserOperLogDao;


    @Override
    public void onMessage(Map<String, String> stringStringMap) {
        if (log.isInfoEnabled()){
            log.info("subject服务用户操作日志收集消费者接收到消息：{}", stringStringMap.toString());
        }
        if (stringStringMap.isEmpty()){
            return;
        }
        stringStringMap.get("level"); // 扩展
        stringStringMap.get("create-time"); // 扩展
        stringStringMap.putIfAbsent("username", "sys");

        AuthUserOperLog authUserOperLog = new AuthUserOperLog();
        authUserOperLog.setUserName(stringStringMap.get("username"));
        authUserOperLog.setDetails(stringStringMap.get("method-name") + " " + stringStringMap.get("args"));
        authUserOperLog.setOperationType(1);
        authUserOperLog.setOperationResult(stringStringMap.get("result"));
        authUserOperLog.setIsDeleted(DeletedFlagEnum.UN_DELETE.getFlag());
        authUserOperLog.setCreatedBy("sys");
        authUserOperLog.setCreatedTime(new Date());
        authUserOperLogDao.insert(authUserOperLog);
    }
}
