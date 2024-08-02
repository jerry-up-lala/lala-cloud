package com.jerry.up.lala.cloud.server.system.dto;

import com.jerry.up.lala.cloud.server.system.entity.SysLogRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 请求日志DTO
 *
 * @author FMJ
 * @date 2023/12/22 13:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogRequestDTO extends SysLogRequest {

    /**
     * 集团名称
     */
    private String tenantName;

}
