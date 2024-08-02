package com.jerry.up.lala.cloud.server.system.api;

import com.jerry.up.lala.cloud.api.system.bo.SysDictItemValuesQueryBO;
import com.jerry.up.lala.cloud.api.system.enums.SysDictKey;
import com.jerry.up.lala.cloud.server.system.service.SysDictItemService;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import com.jerry.up.lala.framework.common.model.DataBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 系统字典
 *
 * @author FMJ
 * @date 2024/8/1 16:18
 */
@RestController
@RequestMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/dict/item")
public class SysDictItemApi {

    @Autowired
    private SysDictItemService sysDictItemService;

    @PostMapping("/label-value-map")
    public Map<String, String> labelValueMap(@RequestBody DataBody<String> dataBody) {
        return sysDictItemService.labelValueMap(SysDictKey.valueOf(dataBody.getValue()));
    }

    @PostMapping("/value-label-map")
    public Map<String, String> valueLabelMap(@RequestBody DataBody<String> dataBody) {
        return sysDictItemService.valueLabelMap(SysDictKey.valueOf(dataBody.getValue()));
    }

    @PostMapping("/dict-values-list")
    public List<String> dictValuesList(@RequestBody SysDictItemValuesQueryBO queryBO) {
        return sysDictItemService.dictValuesList(SysDictKey.valueOf(queryBO.getSysDictKey()), queryBO.getValuesList());
    }

    @PostMapping("/dict-labels-list")
    public List<String> dictLabelsList(@RequestBody DataBody<String> dataBody) {
        return sysDictItemService.dictLabelsList(SysDictKey.valueOf(dataBody.getValue()));
    }


}
