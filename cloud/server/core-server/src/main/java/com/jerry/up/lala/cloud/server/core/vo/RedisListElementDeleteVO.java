package com.jerry.up.lala.cloud.server.core.vo;

import lombok.Data;

/**
 * <p>Description: redis list element 删除
 *
 * @author FMJ
 * @date 2023/11/10 09:55
 */
@Data
public class RedisListElementDeleteVO {

    /**
     * @see com.jerry.up.lala.cloud.server.core.enums.RedisListPushType;
     */
    private Integer pushType;

    private Long count;
}

