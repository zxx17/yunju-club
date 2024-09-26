package com.zsyj.subject.application.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * .
 *
 * @author Xinxuan Zhuo
 * @version 1.0.0
 * @since 2024/9/26
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-consumer")
public class TestConsumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String s) {
        log.info("接收到mq消息：{} " + s);
    }


}
