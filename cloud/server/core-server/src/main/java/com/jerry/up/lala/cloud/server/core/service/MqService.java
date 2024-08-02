package com.jerry.up.lala.cloud.server.core.service;

import com.jerry.up.lala.cloud.server.core.bo.MqBO;
import com.jerry.up.lala.cloud.server.core.vo.MqVO;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;

/**
 * <p>Description: 通知管理service
 *
 * @author FMJ
 * @date 2023/9/14 11:21
 */
public interface MqService {

    void producer(String id, MqVO mqVO);

    void consumer(MqBO mqBO);

    RedisLogInfoBO<MqBO> get(String id);

}
