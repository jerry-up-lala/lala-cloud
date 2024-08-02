package com.jerry.up.lala.cloud.api.system.client;

import com.jerry.up.lala.cloud.api.system.constant.SystemFeignConstant;
import com.jerry.up.lala.framework.cloud.feign.FallbackFactoryUtil;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>Description: 菜单服务
 *
 * @author FMJ
 * @date 2024/7/29 13:30
 */
@FeignClient(contextId = "sysMenuClient", name = SystemFeignConstant.FEIGN_NAME, fallbackFactory = SysMenuClient.SysMenuClientFallbackFactory.class)
public interface SysMenuClient {

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/menu/count")
    Long count();

    @Component
    @Slf4j
    class SysMenuClientFallbackFactory implements FallbackFactory<SysMenuClient> {

        @Override
        public SysMenuClient create(Throwable throwable) {
            log.error("请求服务异常", throwable);

            return new SysMenuClient() {
                @Override
                public Long count() {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

            };
        }
    }

}
