package com.somnus.springboot.commons.base.queue.disruptor;

import com.lmax.disruptor.EventHandler;
import com.somnus.springboot.commons.base.holder.ApplicationContextHolder;
import com.somnus.springboot.service.SeckillService;

/**
 * @ClassName: SeckillEventConsumer
 * @Description: 消费者(秒杀处理器)
 * @author Somnus
 * @date 2018年8月29日
 */
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {
	
	private SeckillService seckillService = ApplicationContextHolder.getBean(SeckillService.class);
	
	public void onEvent(SeckillEvent seckillEvent, long seq, boolean bool) throws Exception {
		seckillService.startSeckil(seckillEvent.getSeckillId(), seckillEvent.getUserId());
	}
}
