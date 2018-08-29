package com.somnus.springboot.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfiguration {
    @Value("${zookeeper.server}")
    private String zookeeperServer;
    @Value(("${zookeeper.sessionTimeoutMs}"))
    private int sessionTimeoutMs;
    @Value("${zookeeper.connectionTimeoutMs}")
    private int connectionTimeoutMs;
    @Value("${zookeeper.maxRetries}")
    private int maxRetries;
    @Value("${zookeeper.baseSleepTimeMs}")
    private int baseSleepTimeMs;

    @Bean
    public CuratorFramework zkClient() {
    	CuratorFramework client = CuratorFrameworkFactory.builder()
        		.connectString(zookeeperServer)
        		// 刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
        		.retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries))
                .sessionTimeoutMs(sessionTimeoutMs)// 连接超时时间
                .connectionTimeoutMs(connectionTimeoutMs)// 会话超时时间
                .build();
    	client.start();
    	return client;
    }

}
