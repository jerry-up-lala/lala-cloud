package com.jerry.up.lala.cloud.api.system.client;

import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.api.system.constant.SystemFeignConstant;
import com.jerry.up.lala.framework.cloud.feign.FallbackFactoryUtil;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import com.jerry.up.lala.framework.common.model.DataBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>Description: 集团服务
 *
 * @author FMJ
 * @date 2024/7/29 13:30
 */
@FeignClient(contextId = "sysTenantClient", name = SystemFeignConstant.FEIGN_NAME, fallbackFactory = SysTenantClient.SysTenantClientFallbackFactory.class)
public interface SysTenantClient {

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/tenant/list")
    List<SysTenantBO> list(@RequestBody DataBody<List<String>> dataBody);

    @Component
    @Slf4j
    class SysTenantClientFallbackFactory implements FallbackFactory<SysTenantClient> {

        @Override
        public SysTenantClient create(Throwable throwable) {
            log.error("请求服务异常", throwable);

            return new SysTenantClient() {
                @Override
                public List<SysTenantBO> list(DataBody<List<String>> dataBody) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

            };
        }
    }

}
