package com.jerry.up.lala.cloud.gateway.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jerry.up.lala.cloud.gateway.error.GatewayErrors;
import com.jerry.up.lala.cloud.gateway.properties.CommonProperties;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.r.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: <a href="https://sa-token.cc/doc.html#/micro/gateway-auth">网关统一鉴权</a>
 *
 * @author FMJ
 * @date 2024/7/26 10:38
 */
@Slf4j
@Configuration
public class SaTokenFilterConfig {

    @Autowired
    private CommonProperties commonProperties;

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter().addInclude("/**")
                .setAuth(r -> SaRouter.match("/**")
                        .notMatch(commonProperties.getOpenUrls()).check(StpUtil::checkLogin))
                .setError(e -> {
                    // 无效token 100002-请先登录
                    if (StrUtil.containsAnyIgnoreCase(e.getMessage(), NotLoginException.NOT_TOKEN_MESSAGE,
                            NotLoginException.INVALID_TOKEN_MESSAGE, NotLoginException.DEFAULT_MESSAGE)) {
                        return JSONUtil.toJsonStr(R.error(Errors.LOGIN_INVALID_TOKEN_ERROR));
                    }
                    // token失效 100003-登录状态已过期，请重新登录
                    if (StrUtil.containsAnyIgnoreCase(e.getMessage(), NotLoginException.TOKEN_TIMEOUT_MESSAGE,
                            NotLoginException.BE_REPLACED_MESSAGE, NotLoginException.KICK_OUT_MESSAGE)) {
                        return JSONUtil.toJsonStr(R.error(Errors.LOGIN_TIME_OUT_ERROR));
                    }
                    return JSONUtil.toJsonStr(R.error(Errors.SYSTEM_ERROR));
                });
    }

    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}
