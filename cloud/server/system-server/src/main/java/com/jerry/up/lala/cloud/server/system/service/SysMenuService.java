package com.jerry.up.lala.cloud.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.cloud.server.system.entity.SysMenu;
import com.jerry.up.lala.cloud.server.system.vo.SysMenuButtonVO;
import com.jerry.up.lala.cloud.server.system.vo.SysMenuRouteVO;
import com.jerry.up.lala.framework.common.model.DataBody;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service
 * @createDate 2023-11-27 16:15:31
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuButtonVO> list(DataBody<String> dataBody);

    List<SysMenuButtonVO> convertVO(List<SysMenu> menuList);

    List<SysMenuRouteVO> routeList();
}
