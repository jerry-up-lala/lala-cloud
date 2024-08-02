package com.jerry.up.lala.cloud.server.core.ctrl;

import com.jerry.up.lala.cloud.api.system.constant.AccessConstant;
import com.jerry.up.lala.cloud.server.core.bo.MqBO;
import com.jerry.up.lala.cloud.server.core.service.MqService;
import com.jerry.up.lala.cloud.server.core.vo.MqVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.boot.redis.RedisLogInfoBO;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 发送消息
 *
 * @author FMJ
 * @date 2023/9/14 11:17
 */
@RestController
@RequestMapping("/mq")
public class MqCtrl {

    @Autowired
    private MqService mqService;

    /**
     * 生产消息
     */
    @PostMapping("/{id}")
    @Api(value = "消息队列-生产消息", accessCodes = AccessConstant.MQ)
    public R producer(@PathVariable("id") String id, @RequestBody MqVO mqVO) {
        mqService.producer(id, mqVO);
        return R.empty();
    }

    /**
     * 获取消息
     */
    @GetMapping("/{id}")
    @Api(value = "消息队列-日志记录", accessCodes = AccessConstant.MQ)
    public R get(@PathVariable("id") String id) {
        RedisLogInfoBO<MqBO> result = mqService.get(id);
        return R.succeed(result);
    }

}
