package com.jerry.up.lala.cloud.server.system.vo;

import lombok.Data;

/**
 * <p>Description: 登录表单
 *
 * @author FMJ
 * @date 2023/9/4 14:55
 */
@Data
public class LoginVO {

    private String loginName;

    private String passWord;

    private String tenantName;

}
