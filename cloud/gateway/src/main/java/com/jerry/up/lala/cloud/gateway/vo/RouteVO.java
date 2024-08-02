package com.jerry.up.lala.cloud.gateway.vo;

import lombok.Data;

/**
 * 路由
 * @author jerry
 * @TableName route
 */
@Data
public class RouteVO {
    /**
     * 路由ID
     */
    private String id;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 服务名称
     */
    private String serverName;
}