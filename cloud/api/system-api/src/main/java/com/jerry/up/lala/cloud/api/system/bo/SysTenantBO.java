package com.jerry.up.lala.cloud.api.system.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Description: 集团 信息 bo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
public class SysTenantBO {

    private String id;

    /**
     * 集团名称
     */
    private String tenantName;

}
