package com.jerry.up.lala.cloud.gateway.ctrl;

import com.jerry.up.lala.cloud.gateway.component.RouteEventComponent;
import com.jerry.up.lala.cloud.gateway.vo.RouteVO;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 路由配置
 *
 * @author FMJ
 * @date 2024/7/24 17:13
 */
@RestController
@RequestMapping("/router")
public class RouteCtrl {

    @Autowired
    private RouteEventComponent routeEventComponent;

    @GetMapping
    public R<List<RouteVO>> list() {
        List<RouteVO> routeList = routeEventComponent.list();
        return R.succeed(routeList);
    }

    @PostMapping
    public R add(@RequestBody RouteVO routeVO) {
        routeEventComponent.add(routeVO);
        return R.empty();
    }

    @PutMapping
    public R update(@RequestBody RouteVO routeVO) {
        routeEventComponent.update(routeVO);
        return R.empty();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") String id) {
        routeEventComponent.delete(id);
        return R.empty();
    }


    @PostMapping("/refresh")
    public R refresh() {
        routeEventComponent.refresh();
        return R.empty();
    }
}
