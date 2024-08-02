package com.jerry.up.lala.cloud.api.system.client;

import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.api.system.bo.UserQueryBO;
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
 * <p>Description: 账号服务
 *
 * @author FMJ
 * @date 2024/7/29 13:30
 */
@FeignClient(contextId = "userClient", name = SystemFeignConstant.FEIGN_NAME, fallbackFactory = UserClient.UserClientFallbackFactory.class)
public interface UserClient {

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/user/list")
    List<UserBO> list(@RequestBody UserQueryBO userQueryBO);

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/user/info")
    UserBO info(@RequestBody DataBody<String> dataBody);

    @Component
    @Slf4j
    class UserClientFallbackFactory implements FallbackFactory<UserClient> {

        @Override
        public UserClient create(Throwable throwable) {
            log.error("请求服务异常", throwable);

            return new UserClient() {
                @Override
                public List<UserBO> list(UserQueryBO userQueryBO) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

                @Override
                public UserBO info(DataBody<String> dataBody) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }
            };
        }
    }

}
