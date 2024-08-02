package com.jerry.up.lala.cloud.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 异常配置
 *
 * @author FMJ
 * @date 2023/9/6 13:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "error")
public class ErrorProperties {

    private Boolean catchPrint;

    private Boolean servicePrint;

    private Boolean runTimePrint;

}
