package com.somnus.springboot.commons.base.distributedlock.redis;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class RedisLockConfig {
	
	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() throws IOException {
		RedissonClient redisson = Redisson.create(
				Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
		return redisson;
	}
	
	/**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    public RedissLockUtil redissLockUtil(RedissonClient redissonClient) {
    	RedissLockUtil redissLockUtil = new RedissLockUtil();
    	redissLockUtil.setRedissonClient(redissonClient);
		return redissLockUtil;
    }
}