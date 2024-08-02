package com.jerry.up.lala.cloud.gateway.config;

import cn.hutool.core.collection.CollUtil;
import com.jerry.up.lala.cloud.gateway.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Description: Redisson 配置
 * <p>
 * 特别的 由于引入 {@link org.redisson.spring.data.connection.RedissonConnection 连接池}.<br>
 * 未实现的方法例如 {@link org.redisson.spring.data.connection.RedissonConnection#lPop(byte[], long)}  lPop方法} 调取时会出现 死循环栈溢出
 *
 * @author FMJ
 * @date 2023/8/30 17:13
 */
@Configuration
@Slf4j
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();

        List<String> nodes = redisProperties.getNodes();
        if (CollUtil.isNotEmpty(nodes)) {
            log.info("Redisson 集群模式");
            String[] addresses = nodes.stream().map(item -> "redis://" + item).toArray(String[]::new);
            // 添加集群地址
            ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(addresses);
            clusterServersConfig.setPassword(redisProperties.getPassword());
        } else if (StringUtils.hasText(redisProperties.getHost())) {
            log.info("Redisson 单节点模式");
            SingleServerConfig singleServerConfig = config.useSingleServer().setAddress("redis://" + redisProperties.getHost());
            singleServerConfig.setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
