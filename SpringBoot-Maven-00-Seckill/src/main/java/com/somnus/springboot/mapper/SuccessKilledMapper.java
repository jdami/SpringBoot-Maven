package com.somnus.springboot.mapper;

import com.somnus.springboot.commons.base.entity.SuccessKilled;
import tk.mybatis.mapper.TKMapper;

public interface SuccessKilledMapper extends TKMapper<SuccessKilled> {
	
	Long getSeckillCount(long seckillId);
	
	void deleteSuccessKilledBySeckillId(long seckillId);
}