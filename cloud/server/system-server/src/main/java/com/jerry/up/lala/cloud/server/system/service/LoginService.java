package com.jerry.up.lala.cloud.server.system.service;

import com.jerry.up.lala.cloud.server.system.vo.LoginUserVO;
import com.jerry.up.lala.cloud.server.system.vo.LoginVO;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:51
 */
public interface LoginService {

    String sysLogin(LoginVO loginVO);

    String login(LoginVO loginVO);

    LoginUserVO info();
}
