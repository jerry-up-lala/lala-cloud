package com.jerry.up.lala.cloud.server.core.ctrl;

import com.jerry.up.lala.cloud.api.system.constant.AccessConstant;
import com.jerry.up.lala.cloud.server.core.bo.HttpBO;
import com.jerry.up.lala.cloud.server.core.service.HttpService;
import com.jerry.up.lala.cloud.server.core.vo.HttpSendVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;
import com.jerry.up.lala.framework.common.r.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: http请求
 *
 * @author FMJ
 * @date 2018/12/21 11:13
 */
@Slf4j
@RestController
@RequestMapping("/http")
public class HttpCtrl {

    private final HttpService httpService;

    @Autowired
    public HttpCtrl(HttpService httpService) {
        this.httpService = httpService;
    }

    /**
     * <p>Description: 发送请求
     *
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    @PostMapping("/{id}")
    @Api(value = "http样例-发送", accessCodes = AccessConstant.HTTP)
    public R send(@PathVariable("id") String id, @RequestBody HttpSendVO httpSendVO) {
        httpService.send(id, httpSendVO);
        return R.empty();
    }

    /**
     * <p>Description: 查询
     *
     * @return 查询结果（请求结果/是否完毕）
     * @author FMJ
     * @date 2018/12/21 11:21
     */
    @GetMapping("/{id}")
    @Api(value = "http样例-日志记录", accessCodes = AccessConstant.HTTP)
    public R query(@PathVariable("id") String id) {
        RedisLogInfoBO<HttpBO> redisLogInfoBO = httpService.query(id);
        return R.succeed(redisLogInfoBO);
    }
}