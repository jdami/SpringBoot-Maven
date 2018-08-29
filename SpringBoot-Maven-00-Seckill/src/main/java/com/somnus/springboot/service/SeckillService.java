package com.somnus.springboot.service;

import java.util.List;

import com.somnus.springboot.commons.base.entity.Seckill;
import com.somnus.springboot.commons.base.result.LogicResult;

public interface SeckillService {

	/**
	 * 查询全部的秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 查询秒杀售卖商品
	 * @param seckillId
	 * @return
	 */
	Long getSeckillCount(long seckillId);
	
	/**
	 * 删除秒杀售卖商品记录
	 * @param seckillId
	 * @return
	 */
	void deleteSeckill(long seckillId);
	
	/**
	 * 秒杀 一、会出现数量错误
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckil(long seckillId,long userId);
	
	/**
	 * 秒杀 二、程序锁
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckilLock(long seckillId,long userId);
	
	/**
	 * 秒杀 二、程序锁AOP
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckilAopLock(long seckillId,long userId);
	
	/**
	 * 秒杀 二、数据库悲观锁
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckilDBPCC_ONE(long seckillId,long userId);
	
	/**
	 * 秒杀 三、数据库悲观锁
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckilDBPCC_TWO(long seckillId,long userId);
	
	/**
	 * 秒杀 三、数据库乐观锁
	 * @param seckillId
	 * @param userId
	 * @return
	 */
	LogicResult startSeckilDBOCC(long seckillId,long userId,long number);
    
}
