package com.jerry.up.lala.cloud.server.system.dto;

import com.jerry.up.lala.cloud.server.system.entity.SysDictItem;
import com.jerry.up.lala.cloud.server.system.vo.SysDictItemInfoVO;
import com.jerry.up.lala.cloud.server.system.vo.SysDictItemTreeVO;
import com.jerry.up.lala.cloud.server.system.vo.SysDictItemVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 字典项
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {SysDictItemTreeVO.class, SysDictItemVO.class, SysDictItemInfoVO.class})
public class SysDictItemDTO extends SysDictItem {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictKey;
}
