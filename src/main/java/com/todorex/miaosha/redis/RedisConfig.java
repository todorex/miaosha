package com.todorex.miaosha.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author rex
 * 2018/10/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private int port;
    /**
     * 超时时间 秒
     */
    private int timeout;
    /**
     * 最大连接数
     */
    private int poolMaxTotal;
    /**
     * 最大空闲数
     */
    private int poolMaxIdle;
    /**
     * 最大等待时间 秒
     */
    private int poolMaxWait;
}
