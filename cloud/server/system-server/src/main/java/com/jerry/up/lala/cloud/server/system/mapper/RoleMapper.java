package com.jerry.up.lala.cloud.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.up.lala.cloud.server.system.dto.RoleDTO;
import com.jerry.up.lala.cloud.server.system.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
* @author jerry
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-12-12 11:47:47
* @Entity com.jerry.up.lala.cloud.server.system.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    RoleDTO selectDTOById(@Param("id") Long id);

}




