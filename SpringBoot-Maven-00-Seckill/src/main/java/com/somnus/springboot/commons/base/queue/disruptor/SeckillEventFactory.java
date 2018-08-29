package com.somnus.springboot.commons.base.queue.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @ClassName: SeckillEventFactory
 * @Description: 事件生成工厂（用来初始化预分配事件对象）
 * @author pc
 * @date 2018年8月29日
 */
public class SeckillEventFactory implements EventFactory<SeckillEvent> {

	public SeckillEvent newInstance() {
		return new SeckillEvent();
	}
}
