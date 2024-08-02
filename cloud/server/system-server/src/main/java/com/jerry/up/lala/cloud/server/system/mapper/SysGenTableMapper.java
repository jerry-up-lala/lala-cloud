package com.jerry.up.lala.cloud.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jerry.up.lala.cloud.server.system.dto.GenTableDTO;
import com.jerry.up.lala.cloud.server.system.dto.GenTableQueryDTO;
import com.jerry.up.lala.cloud.server.system.dto.InformationSchemaTableDTO;
import com.jerry.up.lala.cloud.server.system.entity.SysGenTable;
import org.apache.ibatis.annotations.Param;

/**
* @author jerry
* @description 针对表【sys_gen_table(数据库表)】的数据库操作Mapper
* @createDate 2024-02-12 22:10:49
* @Entity com.jerry.up.lala.cloud.server.system.entity.SysGenTable
*/
public interface SysGenTableMapper extends BaseMapper<SysGenTable> {

    IPage<InformationSchemaTableDTO> page(Page<GenTableDTO> page, @Param("param") GenTableQueryDTO genTableQueryBO);

    InformationSchemaTableDTO selectByTableName(@Param("tableName") String tableName);
}




