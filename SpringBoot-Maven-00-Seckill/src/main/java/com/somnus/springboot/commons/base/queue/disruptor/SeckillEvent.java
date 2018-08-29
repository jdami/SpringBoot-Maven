package com.somnus.springboot.commons.base.queue.disruptor;

import java.io.Serializable;

/**
 * @ClassName: SeckillEvent
 * @Description: 事件对象（秒杀事件）
 * @author pc
 * @date 2018年8月29日
 */
public class SeckillEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private long seckillId;
	private long userId;
	
	public SeckillEvent(){
		
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}