package com.jerry.up.lala.cloud.gateway.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.cloud.gateway.vo.RouteVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;

import java.util.Date;

/**
 * 路由
 * @author jerry
 * @TableName route
 */
@TableName(value ="route")
@Data
@DataBean(targetTypes = RouteVO.class)
public class Route {
    /**
     * 路由ID
     */
    @TableId
    private String id;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;
}