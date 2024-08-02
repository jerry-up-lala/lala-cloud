package com.jerry.up.lala.cloud.server.system.api;

import com.jerry.up.lala.cloud.server.system.service.SysMenuService;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 菜单服务
 *
 * @author FMJ
 * @date 2023/12/12 11:26
 */
@RestController
@RequestMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/menu")
public class SysMenuApi {

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/count")
    public Long count() {
        return sysMenuService.count();
    }

}
