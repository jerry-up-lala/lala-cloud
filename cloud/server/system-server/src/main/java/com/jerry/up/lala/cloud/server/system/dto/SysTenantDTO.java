package com.jerry.up.lala.cloud.server.system.dto;

import com.jerry.up.lala.cloud.api.system.bo.SysTenantBO;
import com.jerry.up.lala.cloud.server.system.entity.SysTenant;
import com.jerry.up.lala.cloud.server.system.vo.SysTenantInfoVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 集团表
 *
 * @author FMJ
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {SysTenantInfoVO.class, SysTenantBO.class})
public class SysTenantDTO extends SysTenant {

    /**
     * 账号ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 状态[true-启用]
     */
    private Boolean userState;

    /**
     * 集团管理员
     */
    private Boolean tenantAdmin;

}