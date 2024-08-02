package com.jerry.up.lala.cloud.api.system.client;

import com.jerry.up.lala.cloud.api.system.bo.SysDictItemValuesQueryBO;
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
import java.util.Map;

/**
 * <p>Description: 系统字典
 *
 * @author FMJ
 * @date 2024/8/1 16:18
 */
@FeignClient(contextId = "sysDictItemClient", name = SystemFeignConstant.FEIGN_NAME, fallbackFactory = SysDictItemClient.SysDictItemClientFallbackFactory.class)
public interface SysDictItemClient {

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/dict/item/label-value-map")
    Map<String, String> labelValueMap(@RequestBody DataBody<String> dataBody);

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/dict/item/value-label-map")
    Map<String, String> valueLabelMap(@RequestBody DataBody<String> dataBody);

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/dict/item/dict-values-list")
    List<String> dictValuesList(@RequestBody SysDictItemValuesQueryBO queryBO);

    @PostMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/dict/item/dict-labels-list")
    List<String> dictLabelsList(@RequestBody DataBody<String> dataBody);

    @Component
    @Slf4j
    public class SysDictItemClientFallbackFactory implements FallbackFactory<SysDictItemClient> {
        @Override
        public SysDictItemClient create(Throwable throwable) {
            log.error("请求服务异常", throwable);

            return new SysDictItemClient() {
                @Override
                public Map<String, String> labelValueMap(DataBody<String> dataBody) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

                @Override
                public Map<String, String> valueLabelMap(DataBody<String> dataBody) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

                @Override
                public List<String> dictValuesList(SysDictItemValuesQueryBO queryBO) {
                    FallbackFactoryUtil.throwException(throwable);
                    return null;
                }

                @Override
                public List<String> dictLabelsList(DataBody<String> dataBody) {
                    return null;
                }

            };
        }
    }
}
