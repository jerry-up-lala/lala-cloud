package com.jerry.up.lala.cloud.server.core.service.impl;

import com.jerry.up.lala.cloud.server.core.bo.HttpBO;
import com.jerry.up.lala.cloud.server.core.component.HttpComponent;
import com.jerry.up.lala.cloud.server.core.service.HttpService;
import com.jerry.up.lala.cloud.server.core.vo.HttpSendVO;
import com.jerry.up.lala.framework.boot.redis.RedisLogComponent;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: http 请求 service实现
 *
 * @author FMJ
 * @date 2018/12/21 11:23
 */
@Slf4j
@Service
public class HttpServiceImpl implements HttpService {

    @Autowired
    private RedisLogComponent<HttpBO> redisLogComponent;

    @Autowired
    private HttpComponent httpComponent;

    /**
     * <p>Title: send
     *
     * @param id         日志ID
     * @param httpSendVO 请求信息
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    @Override
    public void send(String id, HttpSendVO httpSendVO) {
        String httpUrl = httpSendVO.getHttpUrl();
        if (StringUtil.isNull(httpUrl)) {
            throw ServiceException.args();
        }
        try {
            Integer count = httpSendVO.getCount();
            for (int i = 0; i < count; i++) {
                httpComponent.get(id, httpUrl);
            }
        } catch (Exception e) {
            throw ServiceException.error(e);
        }
    }

    /**
     * <p>Title: query
     * <p>Description: 查询
     *
     * @return 查询结果（请求结果/是否完毕）
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    @Override
    public RedisLogInfoBO<HttpBO> query(String id) {
        return redisLogComponent.get(new DataBody<>(id));
    }

}