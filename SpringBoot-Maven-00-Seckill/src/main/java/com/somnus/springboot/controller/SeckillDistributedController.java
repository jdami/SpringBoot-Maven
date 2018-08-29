package com.somnus.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.springboot.commons.base.result.LogicResult;
import com.somnus.springboot.service.SeckillDistributedService;
import com.somnus.springboot.service.SeckillService;

@Api(tags ="分布式秒杀")
@Slf4j
@RestController
@RequestMapping("/seckillDistributed")
public class SeckillDistributedController {
	private final static Logger LOGGER = LoggerFactory.getLogger(SeckillDistributedController.class);
	
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	//调整队列数 拒绝服务
	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(10000));
	
	@Autowired
	private SeckillService seckillService;
	
	@Autowired
	private SeckillDistributedService seckillDistributedService;
	
	@ApiOperation(value="秒杀一(Rediss分布式锁)",nickname="秒杀")
	@PostMapping("/startRedisLock")
	public LogicResult startRedisLock(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		LOGGER.info("开始秒杀一");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() ->{
				LogicResult result = seckillDistributedService.startSeckilRedisLock(killId, userId);
				LOGGER.info("用户:{}{}",userId,result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
}
