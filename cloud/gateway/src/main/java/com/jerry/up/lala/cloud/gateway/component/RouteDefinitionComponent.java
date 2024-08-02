package com.jerry.up.lala.cloud.gateway.component;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Description: 动态路由
 *
 * @author FMJ
 * @date 2024/7/25 15:44
 * @see org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
 */
@Component
public class RouteDefinitionComponent implements RouteDefinitionRepository {

    private final ConcurrentMap<String, RouteDefinition> routeMap = new ConcurrentHashMap<>();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routeMap.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> definition) {
        return definition.flatMap(r -> {
            routeMap.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> Mono.fromRunnable(() -> routeMap.remove(id))).then();
    }
}
