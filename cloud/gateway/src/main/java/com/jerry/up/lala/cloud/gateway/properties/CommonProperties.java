package com.jerry.up.lala.cloud.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Description: 通用配置
 *
 * @author FMJ
 * @date 2023/9/6 13:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "common")
public class CommonProperties {

    /**
     * 数据存储目录
     */
    private String dataPath;

    /**
     * 免登录url
     */
    private List<String> openUrls;

}
