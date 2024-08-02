package com.jerry.up.lala.cloud.server.core.vo;

import lombok.Data;

/**
 * <p>Description: redis list 添加
 *
 * @author FMJ
 * @date 2023/11/10 09:55
 */
@Data
public class RedisListElementAddVO {

    private Object value;

    /**
     * @see com.jerry.up.lala.cloud.server.core.enums.RedisListPushType;
     */
    private Integer pushType;
}
