package com.somnus.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somnus.springboot.commons.base.entity.SuccessKilled;
import com.somnus.springboot.commons.base.queue.disruptor.DisruptorUtil;
import com.somnus.springboot.commons.base.queue.disruptor.SeckillEvent;
import com.somnus.springboot.commons.base.queue.jvm.SeckillQueue;
import com.somnus.springboot.commons.base.result.LogicResult;
import com.somnus.springboot.commons.base.result.ResultEnum;
import com.somnus.springboot.service.SeckillService;

@Api(tags ="秒杀")
@Slf4j
@RestController
@RequestMapping("/seckill")
public class SeckillController {
	
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	
	//创建线程池  调整队列数 拒绝服务
	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(100));
	
	@Autowired
	private SeckillService seckillService;
	
	@ApiOperation(value="秒杀一(最low实现)",nickname="秒杀")
	@PostMapping("/start")
	public LogicResult start(long seckillId) throws Exception {
		CountDownLatch cdl = new CountDownLatch(100);
		/* 清理数据 */
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀一(会出现超卖)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				LogicResult result = seckillService.startSeckil(killId, userId);
				if(result != null){
					log.info("用户:{}->{}",userId,result.getData());
				}else{
					log.info("用户:{}->{}",userId,ResultEnum.SEC_KILL_MUCH.getMsg());
				}
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	
	@ApiOperation(value="秒杀二(程序锁)",nickname="秒杀")
	@PostMapping("/startLock")
	public LogicResult startLock(long seckillId) throws Exception {
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀二(不正常)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				LogicResult result = seckillService.startSeckilLock(killId, userId);
				log.info("用户:{}{}",userId,result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀三(AOP程序锁)",nickname="秒杀")
	@PostMapping("/startAopLock")
	public LogicResult startAopLock(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀三(正常)");
		for(int i=0;i<100;i++){
			final long userId = i;
			executor.execute(() -> {
				LogicResult result = seckillService.startSeckilAopLock(killId, userId);
				log.info("用户:{}{}",userId, result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀四(数据库悲观锁)",nickname="秒杀")
	@PostMapping("/startDBPCC_ONE")
	public LogicResult startDBPCC_ONE(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀四(正常)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				LogicResult result = seckillService.startSeckilDBPCC_ONE(killId, userId);
				log.info("用户:{}{}",userId,result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀五(数据库悲观锁)",nickname="秒杀")
	@PostMapping("/startDPCC_TWO")
	public LogicResult startDPCC_TWO(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀五(正常、数据库锁最优实现)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				LogicResult result = seckillService.startSeckilDBPCC_TWO(killId, userId);
				log.info("用户:{}{}",userId,result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀六(数据库乐观锁)",nickname="秒杀")
	@PostMapping("/startDBOCC")
	public LogicResult startDBOCC(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀六(正常、数据库锁最优实现)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				//这里使用的乐观锁、可以自定义抢购数量、如果配置的抢购人数比较少、比如120:100(人数:商品) 会出现少买的情况
				//用户同时进入会出现更新失败的情况
				LogicResult result = seckillService.startSeckilDBOCC(killId, userId, 1);
				log.info("用户:{}{}",userId,result.getData());
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀七(进程内队列)",nickname="秒杀")
	@PostMapping("/startQueue")
	public LogicResult startQueue(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀七(正常)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				SuccessKilled kill = new SuccessKilled();
				kill.setSeckillId(killId);
				kill.setUserId(userId);
				try {
					Boolean flag = SeckillQueue.getMailQueue().produce(kill);
					if(flag){
						log.info("用户:{}{}",kill.getUserId(),"秒杀成功");
					}else{
						log.info("用户:{}{}",userId,"秒杀失败");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					log.info("用户:{}{}",userId,"秒杀失败");
				}
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
	
	@ApiOperation(value="秒杀八(Disruptor队列)",nickname="秒杀")
	@PostMapping("/startDisruptorQueue")
	public LogicResult startDisruptorQueue(long seckillId) throws Exception{
		CountDownLatch cdl = new CountDownLatch(100);
		seckillService.deleteSeckill(seckillId);
		final long killId =  seckillId;
		log.info("开始秒杀八(正常)");
		for(int i=0; i<100; i++){
			final long userId = i;
			executor.execute(() -> {
				SeckillEvent kill = new SeckillEvent();
				kill.setSeckillId(killId);
				kill.setUserId(userId);
				DisruptorUtil.producer(kill);
				cdl.countDown();
			});
		}
		cdl.await();
        Long seckillCount = seckillService.getSeckillCount(seckillId);
		log.info("一共秒杀出{}件商品",seckillCount);
		return LogicResult.builder().build().complete();
	}
}
