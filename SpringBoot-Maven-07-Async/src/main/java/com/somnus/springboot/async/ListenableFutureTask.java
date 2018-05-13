package com.somnus.springboot.async;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ListenableFutureTask {
	
	private transient Logger	log = LoggerFactory.getLogger(this.getClass());

	@Async
	public ListenableFuture<String> doTask() throws Exception {
		log.info("开始做任务");
		long start = System.currentTimeMillis();
		TimeUnit.MILLISECONDS.sleep(new Random().nextInt(10000));
		long end = System.currentTimeMillis();
		log.info("完成任务，耗时：" + (end - start) + "毫秒");
		return new AsyncResult<String>("任务完成");
	}

}
