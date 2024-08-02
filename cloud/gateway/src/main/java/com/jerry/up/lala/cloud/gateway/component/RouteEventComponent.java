package com.jerry.up.lala.cloud.gateway.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jerry.up.lala.cloud.gateway.entity.Route;
import com.jerry.up.lala.cloud.gateway.error.GatewayErrors;
import com.jerry.up.lala.cloud.gateway.mapper.RouteMapper;
import com.jerry.up.lala.cloud.gateway.vo.RouteVO;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 路由组件
 *
 * @author FMJ
 * @date 2024/7/25 10:56
 */
@Component
@Slf4j
public class RouteEventComponent implements ApplicationEventPublisherAware, CommandLineRunner {

    @Autowired
    private RouteDefinitionComponent routeDefinitionComponent;

    @Autowired
    private RouteMapper routeMapper;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        refresh();
    }

    public List<RouteVO> list() {
        List<Route> routeList = routeMapper.selectList(new LambdaQueryWrapper<>());
        return BeanUtil.toBeanList(routeList, RouteVO.class);
    }

    public void add(RouteVO routeVO) {
        checkArg(routeVO);

        // 路由ID
        String id = routeVO.getId();
        // 路由路径
        String path = routeVO.getPath();
        // 服务名称
        String serverName = routeVO.getServerName();

        boolean idExists = routeMapper.exists(new LambdaQueryWrapper<Route>().eq(Route::getId, id));
        if (idExists) {
            throw ServiceException.error(GatewayErrors.ROUTE_ID_ERROR);
        }

        boolean pathExists = routeMapper.exists(new LambdaQueryWrapper<Route>().eq(Route::getPath, path));
        if (pathExists) {
            throw ServiceException.error(GatewayErrors.ROUTE_PATH_ERROR);
        }
        Route route = new Route();
        route.setId(id);
        route.setPath(path);
        route.setServerName(serverName);
        routeMapper.insert(route);

        routeSave(route);

    }

    public void update(RouteVO routeVO) {
        checkArg(routeVO);

        // 路由ID
        String id = routeVO.getId();
        // 路由路径
        String path = routeVO.getPath();
        // 服务名称
        String serverName = routeVO.getServerName();

        boolean idExists = routeMapper.exists(new LambdaQueryWrapper<Route>().eq(Route::getId, id));
        if (!idExists) {
            throw ServiceException.notFound();
        }

        boolean pathExists = routeMapper.exists(new LambdaQueryWrapper<Route>().eq(Route::getPath, path).ne(Route::getId, id));
        if (pathExists) {
            throw ServiceException.error(GatewayErrors.ROUTE_PATH_ERROR);
        }
        Route route = new Route();
        route.setId(id);
        route.setPath(path);
        route.setServerName(serverName);
        routeMapper.updateById(route);

        routeSave(route);
    }

    private void checkArg(RouteVO routeVO) {
        // 路由ID
        String id = routeVO.getId();
        // 路由路径
        String path = routeVO.getPath();
        // 服务名称
        String serverName = routeVO.getServerName();
        if (StringUtil.isNull(id) || StringUtil.isNull(path) || StringUtil.isNull(serverName)) {
            throw ServiceException.args();
        }
    }

    public void delete(String id) {
        routeMapper.deleteById(id);
        routeDefinitionComponent.delete(Mono.just(id)).subscribe();
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        routeRefreshEvent();
    }

    private void routeSave(Route route) {
        routeDefinitionComponent.save(Mono.just(definition(route))).subscribe();
        routeRefreshEvent();
    }

    public void refresh() {
        List<Route> routeList = routeMapper.selectList(new LambdaQueryWrapper<>());
        if (CollUtil.isEmpty(routeList)) {
            return;
        }
        routeList.forEach((Route route) -> routeDefinitionComponent.save(Mono.just(definition(route))).subscribe());
        routeRefreshEvent();
    }

    private void routeRefreshEvent() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private RouteDefinition definition(Route route) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(route.getId());
        definition.setUri(URLUtil.toURI("lb://" + route.getServerName()));
        definition.setPredicates(predicates(route.getPath()));
        definition.setFilters(filters());
        return definition;
    }

    private List<PredicateDefinition> predicates(String path) {
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
        Map<String, String> predicateParams = new HashMap<>(8);
        predicateParams.put("pattern", path + "/**");
        predicate.setArgs(predicateParams);
        return ListUtil.toList(predicate);
    }

    private List<FilterDefinition> filters() {
        FilterDefinition filter = new FilterDefinition();
        Map<String, String> filterParams = new HashMap<>(8);
        filter.setName("StripPrefix");
        filterParams.put("_genkey_0", "1");
        filter.setArgs(filterParams);
        return ListUtil.toList(filter);
    }

}
