package com.jerry.up.lala.cloud.api.system.client;

import com.jerry.up.lala.cloud.api.system.constant.SystemFeignConstant;
import com.jerry.up.lala.framework.cloud.feign.FallbackFactoryUtil;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>Description: 角色服务
 *
 * @author FMJ
 * @date 2024/7/29 13:30
 */
@FeignClient(contextId = "roleClient", name = SystemFeignConstant.FEIGN_NAME, fallbackFactory = RoleClient.RoleClientFallbackFactory.class)
public interface RoleClient {

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/role/access-codes")
    List<String> getAccessCodes();

    @Component
    @Slf4j
    class RoleClientFallbackFactory implements FallbackFactory<RoleClient> {

        @Override
        public RoleClient create(Throwable throwable) {
            log.error("请求服务异常", throwable);

            return new RoleClient() {
                @Override
                public List<String> getAccessCodes() {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }
            };
        }
    }

}
