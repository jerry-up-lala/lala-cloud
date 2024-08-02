package com.jerry.up.lala.cloud.api.system.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: user查询BO
 *
 * @author FMJ
 * @date 2024/7/29 22:51
 */
@Data
@Accessors(chain = true)
public class UserQueryBO {

    /**
     * 账号ID
     */
    private String id;

    /**
     * 账号ID列表
     */
    private List<String> ids;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;
}
