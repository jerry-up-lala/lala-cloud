package com.jerry.up.lala.cloud.server.system.dto;

import com.jerry.up.lala.cloud.api.system.bo.UserBO;
import com.jerry.up.lala.cloud.server.system.entity.SysMenu;
import com.jerry.up.lala.cloud.server.system.entity.User;
import com.jerry.up.lala.cloud.server.system.vo.UserInfoVO;
import com.jerry.up.lala.cloud.server.system.vo.UserPersonalVO;
import com.jerry.up.lala.cloud.server.system.vo.UserVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 用户DTO
 *
 * @author FMJ
 * @date 2023/12/21 09:50
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {UserVO.class, UserInfoVO.class, UserPersonalVO.class, UserBO.class})
public class UserDTO extends User {

    private List<RoleUserDTO> roleList;

    private List<String> effectiveRoleNames;

    private List<String> invalidRoleNames;

    private List<Long> roleIds;

    private List<SysMenu> menuList;

}
