package com.somnus.springboot;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedissonLockTest {
	@Autowired
	private RedissonClient redisson ;
	
	private CountDownLatch cdl = new CountDownLatch(100);
	
	@Test
	public void tryLock() throws Exception {
	    String key = "秒杀";
	    ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 1; i <=100; i++){
            final int taskID = i;
            executor.execute(() -> {
            	RLock lock = redisson.getLock(key);
                boolean isLock = lock.tryLock();
                if(isLock) {
                	try{
            	        System.out.println("isLock=" + isLock + "|" +Thread.currentThread().getName() + " for task of " + taskID);
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
            	    }catch(Exception e){
            	    	e.printStackTrace();
            	    }finally{
            	        lock.unlock();
            	    }
                }else {
                	System.out.println("没有得到锁");
                }
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
}