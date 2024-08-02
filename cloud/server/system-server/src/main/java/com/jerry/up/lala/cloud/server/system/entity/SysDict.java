package com.jerry.up.lala.cloud.server.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jerry.up.lala.cloud.server.system.vo.SysDictInfoVO;
import com.jerry.up.lala.cloud.server.system.vo.SysDictVO;
import com.jerry.up.lala.framework.boot.entity.Entity;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典类型表
 *
 * @author FMJ
 * @TableName sys_dict
 */
@TableName(value = "sys_dict")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {SysDictVO.class, SysDictInfoVO.class})
public class SysDict extends Entity {
    /**
     * 字典ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictKey;

}