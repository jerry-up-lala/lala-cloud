package com.jerry.up.lala.cloud.server.system.constant;

/**
 * <p>Description: 通知管理常量
 *
 * @author FMJ
 * @date 2023/9/14 16:52
 */
public class MqConstant {

    public static final String LALA_CLOUD_SYSTEM_CONSUMER = "lala_cloud_system_consumer";

    /**
     * 与 log.mq配置项对应
     */
    public static final String LOG_REQUEST_TOPIC = "lala_cloud_log_request_topic";

    /**
     * 请求日志消费组
     */
    public static final String LOG_REQUEST_TOPIC_CONSUMER_GROUP = LALA_CLOUD_SYSTEM_CONSUMER + "_"  + LOG_REQUEST_TOPIC;


}