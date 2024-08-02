package com.jerry.up.lala.cloud.server.system.api;

import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.server.system.service.SysTenantService;
import com.jerry.up.lala.framework.common.constant.CommonConstant;
import com.jerry.up.lala.framework.common.model.DataBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 集团服务
 *
 * @author FMJ
 * @date 2023/12/12 11:26
 */
@RestController
@RequestMapping(CommonConstant.FEIGN_URL_PREFIX + "/sys/tenant")
public class SysTenantApi {

    @Autowired
    private SysTenantService sysTenantService;

    @PostMapping("/list")
    public List<SysTenantBO> list(@RequestBody DataBody<List<String>> dataBody) {
        return sysTenantService.listBO(dataBody);
    }

}
