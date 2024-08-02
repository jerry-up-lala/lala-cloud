package com.jerry.up.lala.cloud.server.core.service.impl;

import com.jerry.up.lala.cloud.server.core.bo.MqBO;
import com.jerry.up.lala.cloud.server.core.constant.MqConstant;
import com.jerry.up.lala.cloud.server.core.service.MqService;
import com.jerry.up.lala.cloud.server.core.vo.MqVO;
import com.jerry.up.lala.framework.boot.redis.RedisLogAddBO;
import com.jerry.up.lala.framework.boot.redis.RedisLogComponent;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>Description: 通知管理service
 *
 * @author FMJ
 * @date 2023/9/14 11:21
 */
@Slf4j
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    @Autowired
    private RedisLogComponent<MqBO> redisLogComponent;

    @Override
    public void producer(String id, MqVO mqVO) {
        if (id == null) {
            throw ServiceException.args();
        }
        rocketMqTemplate.convertAndSend(MqConstant.DEMO_TOPIC, new MqBO().setId(id).setInfo(mqVO.getInfo()).setProducerDateTime(new Date()));
    }

    @Override
    public void consumer(MqBO mqBO) {
        if (mqBO == null || StringUtil.isNull(mqBO.getId())) {
            return;
        }
        mqBO.setConsumerThreadName(Thread.currentThread().getName());
        mqBO.setConsumerDateTime(new Date());
        RedisLogAddBO<MqBO> redisLogAddBO = new RedisLogAddBO<MqBO>().setInfo(mqBO);
        redisLogAddBO.setValue(mqBO.getId());
        redisLogComponent.add(redisLogAddBO);
    }

    @Override
    public RedisLogInfoBO<MqBO> get(String id) {
        return redisLogComponent.get(new DataBody<>(id));
    }
}
