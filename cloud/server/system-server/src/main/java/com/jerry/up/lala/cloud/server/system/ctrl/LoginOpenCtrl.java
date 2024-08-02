package com.jerry.up.lala.cloud.server.system.ctrl;

import com.jerry.up.lala.cloud.server.system.service.LoginService;
import com.jerry.up.lala.cloud.server.system.vo.LoginVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 登录
 *
 * @author FMJ
 * @date 2023/9/4 14:41
 */
@RestController
@RequestMapping("/open/login")
public class LoginOpenCtrl {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @Api(value = "公共接口-登录")
    public R<String> login(@RequestBody LoginVO loginVO) {
        String token = loginService.login(loginVO);
        return R.succeed(token);
    }

}
