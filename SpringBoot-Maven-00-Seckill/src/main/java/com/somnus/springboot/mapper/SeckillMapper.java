package com.somnus.springboot.mapper;

import org.apache.ibatis.annotations.Param;

import com.somnus.springboot.commons.base.entity.Seckill;
import tk.mybatis.mapper.TKMapper;

public interface SeckillMapper extends TKMapper<Seckill> {
	
	int updateSeckillByOptimistic(@Param(value="number")long number, 
			@Param(value="seckillId")long seckillId, 
			@Param(value="version")int version);
	
	int updateSeckillByPessimistic(long seckillId);
	
	Seckill selectByPrimaryKeyForUpdate(long seckillId);
}