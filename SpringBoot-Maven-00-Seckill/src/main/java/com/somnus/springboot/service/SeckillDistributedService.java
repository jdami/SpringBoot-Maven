package com.somnus.springboot.service;

import com.somnus.springboot.commons.base.result.LogicResult;

public interface SeckillDistributedService {

	/**
	 * 秒杀 一  单个商品
	 * @param seckillId 秒杀商品ID
	 * @param userId 用户ID
	 * @return
	 */
	LogicResult startSeckilRedisLock(long seckillId,long userId);
	
	/**
	 * 秒杀 一  单个商品
	 * @param seckillId 秒杀商品ID
	 * @param userId 用户ID
	 * @return
	 */
	LogicResult startSeckilZksLock(long seckillId,long userId);
	
	/**
	 * 秒杀 二 多个商品
	 * @param seckillId 秒杀商品ID
	 * @param userId 用户ID
	 * @param number 秒杀商品数量
	 * @return
	 */
	LogicResult startSeckilLock(long seckillId,long userId,long number);
	
}
