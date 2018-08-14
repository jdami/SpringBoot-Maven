package com.somnus.springboot;

import java.util.Random;
import java.util.UUID;
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
	
	private int total = 10;
	
	/** 模拟100个人抢 10张票 ，没抢到就等待，直到拿到锁*/
	@Test
	public void lock() throws Exception {
	    String key = UUID.randomUUID().toString();
	    ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 1; i <=100; i++){
            executor.execute(() -> {
            	RLock lock = redisson.getLock(key);
            	lock.lock();
                try {
                	if(total>0) {
                		System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));// 为了测试出效果，让每次任务执行都需要一定时间
                	}else {
                		System.out.println(Thread.currentThread().getName() + " 没有抢到票 ");
                	}
                }catch (Exception e) {
                	e.printStackTrace();
				}finally{
        	        lock.unlock();
        	    }
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
	@Test
	public void tryLock() throws Exception {
	    String key = UUID.randomUUID().toString();
	    ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 1; i <=100; i++){
            executor.execute(() -> {
            	RLock lock = redisson.getLock(key);
            	//尝试加锁，支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
                /*boolean isLock = lock.tryLock(10, TimeUnit.SECONDS);*/
        		//尝试加锁，最多等待3秒，上锁以后10秒自动解锁
                /*boolean isLock = lock.tryLock(3, 10, TimeUnit.SECONDS);*/
    			boolean isLock = lock.tryLock();
            	if(isLock) {
            		try {
            			System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));// 为了测试出效果，让每次任务执行都需要一定时间
            		}catch (Exception e) {
            			e.printStackTrace();
					}finally{
	        	        lock.unlock();
	        	    }
            	}else {
            		System.out.println(Thread.currentThread().getName() + " 没有抢到票 ");
            	}
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
}