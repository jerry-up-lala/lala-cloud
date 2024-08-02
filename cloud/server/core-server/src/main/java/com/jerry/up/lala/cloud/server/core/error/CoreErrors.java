package com.jerry.up.lala.cloud.server.core.error;

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
public enum CoreErrors implements Error {

    /**
     * 全局异常
     */
    REDIS_KEY_EXISTS("130001", "Key已存在，请确认"),

    REDIS_KEY_INVALID("130002", "Key已失效，请确认"),

    REDIS_KEY_DATA_TYPE_INVALID("130003", "Key数据类型错误，请确认"),

    NOTICE_USER_EMPTY("130004", "目标用户不能为空，请确认"),

    NOTICE_SEND_STATE_UNSENT("130005", "未发送状态才允许操作，请确认");

    private final String code;

    private final String msg;

}
