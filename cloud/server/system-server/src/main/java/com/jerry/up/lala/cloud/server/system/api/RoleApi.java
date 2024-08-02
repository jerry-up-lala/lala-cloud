package com.jerry.up.lala.cloud.server.system.api;

import com.jerry.up.lala.cloud.server.system.service.RoleService;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: 角色服务
 *
 * @author FMJ
 * @date 2023/12/12 11:26
 */
@RestController
@RequestMapping(CommonConstant.FEIGN_URL_PREFIX + "/role")
public class RoleApi {

    @Autowired
    private RoleService roleService;

    @PostMapping("/access-codes")
    public List<String> getAccessCodes() {
        return roleService.getAccessCodes();
    }

}
