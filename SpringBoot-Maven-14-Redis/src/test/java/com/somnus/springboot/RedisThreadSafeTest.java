package com.somnus.springboot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisThreadSafeTest {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public static final List<String> list = Collections.unmodifiableList(new ArrayList<String>() {
        private static final long serialVersionUID = 1L;

        {
        	add("0");
            add("one");add("two");add("three");add("four");add("five");
            add("six");add("seven");add("eight");add("nine");add("ten");
            
        }
    });
	
	private CountDownLatch cdl = new CountDownLatch(10);
	
	@Test
	public void newSingleThreadExecutor() throws InterruptedException {
		/*创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务*/
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 1; i <=10; i++){
            final int taskID = i;
            executor.execute(() -> {
            	for (int j = 1; j <= 10; j++){
                    long nonce = stringRedisTemplate.opsForValue().increment(list.get(taskID), 1);
                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + taskID +"-->" + nonce);
                }
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
	@Test
	public void newFixedThreadPool() throws InterruptedException {
		/*该方法返回一个固定数量的线程池，该方法的线程池数始终不变，当有一个任务提交时，
		 * 若线程池中空闲，则立即执行，若没有，则会被暂缓在一个任务队列总等待有空闲的线程去执行 */
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <=10; i++){
            final int taskID = i;
            executor.execute(() -> {
            	for (int j = 1; j <= 10; j++){
                    long nonce = stringRedisTemplate.opsForValue().increment("c", 1);
                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + taskID +"-->" + nonce);
                }
            	cdl.countDown();
            });
        }
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
	@Test
	public void newCachedThreadPool() throws InterruptedException {
		/*返回一个可根据实际情况调整线程个数的线程池，不限制最大线程数量，若用空闲的线程则执行任务，
		 * 若无任务则不创建线程。并且每一个空闲线程会在60s后自动回收*/
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <=10; i++){
            final int taskID = i;
            executor.execute(() -> {
            	for (int j = 1; j <= 10; j++){
                    long nonce = stringRedisTemplate.opsForValue().increment("d", 1);
                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + taskID +"-->" + nonce);
                }
            	cdl.countDown();
            });
        }
        /** 
         * shutdown 允许之前已经提交但未执行或未完成的任务继续完成它，平滑的关闭ExecutorService，
         * 当此方法被调用时，ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。
         * 当所有提交任务执行完毕，线程池即被关闭。
         * 
         * shutdownNow 阻止已经提交(但尚未运行的)的任务运行并且尝试停止正在运行的任务
         */
        cdl.await();
        executor.shutdown();// 任务执行完毕，关闭线程池
	}
	
}
