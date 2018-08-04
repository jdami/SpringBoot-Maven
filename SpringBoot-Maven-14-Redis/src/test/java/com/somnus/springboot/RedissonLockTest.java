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
	
	private CountDownLatch cdl = new CountDownLatch(10);
	
	private int total = 100;
	
	@Test
	public void lock() throws Exception {
	    String key = UUID.randomUUID().toString();
	    ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 1; i <=10; i++){
            executor.execute(() -> {
            	while(total>0) {
            		RLock lock = redisson.getLock(key);
                    lock.lock();
                    try{
            	        System.out.println(Thread.currentThread().getName() + " 剩余票数 " + (--total));
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
            	    }catch(Exception e){
            	    	e.printStackTrace();
            	    }finally{
            	        lock.unlock();
            	    }
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
	    ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 1; i <=10; i++){
            executor.execute(() -> {
            	while(total>0) {
            		RLock lock = redisson.getLock(key);
            		try {
            			//尝试加锁，支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
                        /*boolean isLock = lock.tryLock(10, TimeUnit.SECONDS);*/
            			
                		//尝试加锁，最多等待3秒，上锁以后10秒自动解锁
                        /*boolean isLock = lock.tryLock(3, 10, TimeUnit.SECONDS);*/
            			
            			boolean isLock = lock.tryLock();
                        if(isLock) {
                        	System.out.println("isLock=" + isLock + "|" +Thread.currentThread().getName() + " 剩余票数 " + (--total));
                	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
                        }
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
            	}
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
}