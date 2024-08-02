package com.jerry.up.lala.cloud.gateway.handler;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jerry.up.lala.cloud.gateway.error.GatewayErrors;
import com.jerry.up.lala.cloud.gateway.properties.ErrorProperties;
import com.jerry.up.lala.framework.common.exception.Error;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.r.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * <p>Description: 统一异常处理
 *
 * @author FMJ
 * @date 2024/7/11 16:52
 */
@Component
@Order(-1)
@Slf4j
public class ExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private ErrorProperties errorProperties;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSONUtil.toJsonStr(loadR(ex)).getBytes());
        }));
    }

    private R loadR(Throwable throwable) {
        if (throwable instanceof UndeclaredThrowableException) {
            throwable = ((UndeclaredThrowableException) throwable).getUndeclaredThrowable();
        }
        if (throwable.getCause() != null && throwable.getCause().getCause() != null && throwable.getCause().getCause() instanceof ServiceException) {
            throwable = throwable.getCause().getCause();
        }
        if (throwable instanceof ServiceException) {
            // 处理GlobalException异常
            ServiceException serviceException = (ServiceException) throwable;
            // 异常信息
            Error error = serviceException.getError();
            // 捕获异常
            Exception catchException = serviceException.getCatchException();

            if (BooleanUtil.isTrue(errorProperties.getCatchPrint()) && catchException != null) {
                log.error("捕获异常", catchException);
            }

            if (BooleanUtil.isTrue(errorProperties.getServicePrint())) {
                log.error("业务异常", serviceException);
            }
            return R.error(error);
        }

        // 运行时异常
        if (BooleanUtil.isTrue(errorProperties.getRunTimePrint())) {
            log.error("运行时异常", throwable);
        }
        if (StrUtil.containsIgnoreCase(throwable.getMessage(), "404 NOT_FOUND")) {
            return R.empty();
        }
        if (StrUtil.containsIgnoreCase(throwable.getMessage(), "Unable to find instance for")) {
            return R.error(Errors.SERVER_ERROR);
        }
        return R.error(Errors.SYSTEM_ERROR);
    }
}
