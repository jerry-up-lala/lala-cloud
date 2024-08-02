package com.jerry.up.lala.cloud.gateway.ctrl;

import cn.hutool.core.collection.CollUtil;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 服务实例
 *
 * @author FMJ
 * @date 2024/7/29 10:57
 */
@RestController
@RequestMapping("/discovery")
public class DiscoveryCtrl {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/instances")
    public R instances() {

        List<ServiceInstance> serviceInstanceList = new ArrayList<>();
        List<String> services = discoveryClient.getServices();
        if (CollUtil.isNotEmpty(services)) {
            services.forEach(service -> {
                List<ServiceInstance> instances = discoveryClient.getInstances(service);
                serviceInstanceList.addAll(instances);
            });
        }
        List<ServiceInstance> result = serviceInstanceList.stream()
                .sorted(Comparator.comparing(ServiceInstance::getServiceId))
                .collect(Collectors.toList());
        return R.succeed(result);
    }

}

