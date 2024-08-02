package com.jerry.up.lala.cloud.server.system.error;

import com.jerry.up.lala.framework.common.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 错误信息
 *
 * @author FMJ
 * @date 2023/9/5 14:26
 */
@Getter
@AllArgsConstructor
public enum SystemErrors implements Error {

    /**
     * 全局异常
     */
    LOGIN_NAME_PASSWORD_INVALID("120001", "用户名或者密码错误，请确认"),

    LOGIN_TENANT_NAME_INVALID("120002", "集团名称错误，请确认"),

    TENANT_NAME_EXISTS("120003", "集团名称已存在，请确认"),

    USER_LOGIN_NAME_EXISTS("120004", "用户名已存在，请确认"),

    ROLE_NAME_EXISTS("120005", "角色名已存在，请确认"),

    USER_PERSONAL_OLD_PASSWORD_INVALID("120006", "原密码错误，请确认"),

    GEN_PREVIEW_ERROR("120007", "预览异常"),

    DICT_NAME_EXISTS("120008", "字典名称已存在，请确认"),

    DICT_KEY_EXISTS("120009", "字典标识已存在，请确认"),

    DICT_ITEM_LABEL_EXISTS("120010", "展示名已存在，请确认"),

    DICT_ITEM_VALUE_EXISTS("120011", "值已存在，请确认");

    private final String code;

    private final String msg;

}
