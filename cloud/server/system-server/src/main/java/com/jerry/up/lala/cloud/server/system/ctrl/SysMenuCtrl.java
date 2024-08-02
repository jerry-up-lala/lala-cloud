package com.jerry.up.lala.cloud.server.system.ctrl;

import com.jerry.up.lala.cloud.api.system.constant.AccessConstant;
import com.jerry.up.lala.cloud.server.system.service.SysMenuService;
import com.jerry.up.lala.cloud.server.system.vo.SysMenuButtonVO;
import com.jerry.up.lala.cloud.server.system.vo.SysMenuRouteVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Description: 系统菜单
 *
 * @author FMJ
 * @date 2023/11/27 16:16
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuCtrl {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    @Api(value = "菜单管理-查询", accessCodes = {AccessConstant.MENU, AccessConstant.MENU_QUERY})
    public R<List<SysMenuButtonVO>> list(DataBody<String> dataBody) {
        List<SysMenuButtonVO> result = sysMenuService.list(dataBody);
        return R.succeed(result);
    }

    @GetMapping("/route")
    @Api(value = "公共接口-路由树", log = false)
    public R<List<SysMenuRouteVO>> routeList() {
        List<SysMenuRouteVO> result = sysMenuService.routeList();
        return R.succeed(result);
    }

}
