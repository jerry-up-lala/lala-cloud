package com.jerry.up.lala.cloud.server.system.api;

import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.api.system.bo.UserQueryBO;
import com.jerry.up.lala.cloud.server.system.service.UserService;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import com.jerry.up.lala.framework.common.model.DataBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 用户服务
 *
 * @author FMJ
 * @date 2023/12/12 11:26
 */
@RestController
@RequestMapping(CommonConstant.FEIGN_URL_PREFIX + "/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public List<UserBO> list(@RequestBody UserQueryBO userQueryBO) {
        return userService.listBO(userQueryBO);
    }

    @GetMapping("/info")
    public UserBO info(@RequestBody DataBody<String> dataBody) {
        return userService.infoBO(dataBody);
    }

}
