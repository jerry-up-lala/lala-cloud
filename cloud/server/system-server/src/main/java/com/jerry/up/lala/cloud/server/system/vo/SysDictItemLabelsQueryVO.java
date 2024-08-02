package com.jerry.up.lala.cloud.server.system.vo;

import com.jerry.up.lala.cloud.api.system.enums.SysDictKey;
import lombok.Data;

/**
 * <p>Description: 字典项展示名列表查询
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictItemLabelsQueryVO {

    private SysDictKey dictKey;

    private Boolean root;

    private Boolean lowest;

}
