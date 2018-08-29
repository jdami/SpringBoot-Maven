package com.somnus.springboot;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CuratorLockTest {
	@Autowired
	private CuratorFramework client;
	
	private CountDownLatch cdl = new CountDownLatch(100);
	
	private int total = 10;
	
	/** 模拟100个人抢 10张票 ，没抢到就等待，直到拿到锁*/
	@Test
	public void lock() throws Exception {
	    ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 1; i <=100; i++){
            executor.execute(() -> {
            	/* 全局可重入锁 */
            	InterProcessMutex lock = new InterProcessMutex(client, "/curator/lock");
                try {
                	lock.acquire();
                	if(total>0) {
                		System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));// 为了测试出效果，让每次任务执行都需要一定时间
                	}else {
                		System.out.println(Thread.currentThread().getName() + " 没有抢到票 ");
                	}
                }catch (Exception e) {
                	e.printStackTrace();
				}finally{
					try {
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
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
	    ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 1; i <=100; i++){
            executor.execute(() -> {
            	InterProcessMutex lock = new InterProcessMutex(client, "/curator/lock");
    			boolean isLock = false;
				try {
					isLock = lock.acquire(1000, TimeUnit.MILLISECONDS);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            	if(isLock) {
            		try {
            			System.out.println(Thread.currentThread().getName() + " 抢到第（" + (total--) +"）张票 ");
            	        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));// 为了测试出效果，让每次任务执行都需要一定时间
            		}catch (Exception e) {
            			e.printStackTrace();
					}finally{
						try {
							lock.release();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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