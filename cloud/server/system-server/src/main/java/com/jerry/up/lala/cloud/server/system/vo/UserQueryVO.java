package com.jerry.up.lala.cloud.server.system.vo;

import com.jerry.up.lala.cloud.server.system.dto.UserQueryDTO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.model.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * <p>Description: 用户查询
 *
 * @author FMJ
 * @date 2023/12/8 17:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@DataBean(targetTypes = UserQueryDTO.class)
public class UserQueryVO extends PageQuery {
    /**
     * 账号ID
     */
    private String id;

    /**
     * 账号(集团唯一)
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    /**
     * 集团管理员
     */
    private Boolean tenantAdmin;

    private List<String> createTimeRang;

}
