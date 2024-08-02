package com.jerry.up.lala.cloud.server.system.dto;

import com.jerry.up.lala.cloud.server.system.entity.Role;
import com.jerry.up.lala.cloud.server.system.vo.RoleInfoVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.annotation.DataFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * 角色
 *
 * @author FMJ
 * @TableName role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@FieldNameConstants
@DataBean(targetTypes = {RoleInfoVO.class})
public class RoleDTO extends Role {

    private String menuIdStrs;

    @DataFormat(sourceFieldName = Fields.menuIdStrs, split = ",")
    private List<Long> menuIds;

    private String userIdStrs;

    @DataFormat(sourceFieldName = Fields.userIdStrs, split = ",")
    private List<String> userIds;

}