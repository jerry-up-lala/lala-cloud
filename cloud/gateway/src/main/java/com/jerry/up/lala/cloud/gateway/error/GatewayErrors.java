package com.jerry.up.lala.cloud.gateway.error;

import com.jerry.up.lala.framework.common.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 错误信息
 *
 * @author FMJ
 * @date 2023/9/5 14:26
 */
@Getter
@AllArgsConstructor
public enum GatewayErrors implements Error {

    ROUTE_ID_ERROR("110001", "路由ID已存在，请确认"),

    ROUTE_PATH_ERROR("110002", "路由路径已存在，请确认");

    private final String code;

    private final String msg;

}