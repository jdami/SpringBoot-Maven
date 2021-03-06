package com.somnus.springboot;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedissonLockTest {
	
	private CountDownLatch cdl = new CountDownLatch(100);
	
	private int total = 10;
	
	/** 模拟100个人抢 10张票 ，没抢到就等待，直到拿到锁*/
	@Test
	public void lock() throws Exception {
	    String key = UUID.randomUUID().toString();
	    ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 1; i <=100; i++){
            executor.execute(() -> {
            	RedissLockUtil.lock(key);
                try {
                	if(total>0) {
                		System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
                	}else {
                		System.out.println(Thread.currentThread().getName() + " 票已经卖完了 ");
                	}
                }catch (Exception e) {
                	e.printStackTrace();
				}finally{
					RedissLockUtil.unlock(key);
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
                boolean isLock = RedissLockUtil.tryLock(key, 10, 20);
                System.out.println(isLock);
            	if(isLock) {
            		try {
            			if(total>0) {
            				System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
                	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));// 为了测试出效果，让每次任务执行都需要一定时间
            			}else {
            				System.out.println(Thread.currentThread().getName() + " 票已经卖完了");
            			}
            		}catch (Exception e) {
            			e.printStackTrace();
					}finally{
						RedissLockUtil.unlock(key);
	        	    }
            	}else {
            		System.out.println(Thread.currentThread().getName() + " 哎呦喂，人也太多了，请稍后！");
            	}
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
}