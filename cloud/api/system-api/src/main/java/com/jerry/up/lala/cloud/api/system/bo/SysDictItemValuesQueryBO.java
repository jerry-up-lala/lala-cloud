package com.jerry.up.lala.cloud.api.system.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 系统字典 查询值
 *
 * @author FMJ
 * @date 2024/8/1 16:29
 */
@Data
@Accessors(chain = true)
public class SysDictItemValuesQueryBO {

   private String sysDictKey;

    private List<String> valuesList;
}
