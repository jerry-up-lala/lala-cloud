package com.jerry.up.lala.cloud.server.core.listener;

import com.jerry.up.lala.cloud.server.core.bo.MqBO;
import com.jerry.up.lala.cloud.server.core.constant.MqConstant;
import com.jerry.up.lala.cloud.server.core.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 消费消息
 *
 * @author FMJ
 * @date 2023/9/14 14:51
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = MqConstant.DEMO_TOPIC_CONSUMER_GROUP, topic = MqConstant.DEMO_TOPIC)
public class MqDemoTopicListener implements RocketMQListener<MqBO> {

    @Autowired
    private MqService mqService;

    @Override
    public void onMessage(MqBO mqBO) {
        mqService.consumer(mqBO);
    }
}
