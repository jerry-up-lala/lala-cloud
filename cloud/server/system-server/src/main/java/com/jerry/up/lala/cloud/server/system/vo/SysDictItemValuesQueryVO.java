package com.jerry.up.lala.cloud.server.system.vo;

import com.jerry.up.lala.cloud.api.system.enums.SysDictKey;
import lombok.Data;

import java.util.List;

/**
 * <p>Description: 字典项值列表查询
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
public class SysDictItemValuesQueryVO {

    private SysDictKey dictKey;


    private List<String> valuesList;

}
