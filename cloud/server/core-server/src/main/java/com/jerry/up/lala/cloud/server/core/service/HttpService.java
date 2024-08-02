package com.jerry.up.lala.cloud.server.core.service;


import com.jerry.up.lala.cloud.server.core.bo.HttpBO;
import com.jerry.up.lala.cloud.server.core.vo.HttpSendVO;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;

/**
 * <p>Description: http请求service
 *
 * @author FMJ
 * @date 2018/12/21 11:18
 */
public interface HttpService {

    /**
     * <p>Description: 发送请求
     *
     * @param id         日志id
     * @param httpSendVO 请求信息
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    void send(String id, HttpSendVO httpSendVO);

    /**
     * <p>Description: 查询
     *
     * @param id 日志id
     * @return 查询结果
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    RedisLogInfoBO<HttpBO> query(String id);

}