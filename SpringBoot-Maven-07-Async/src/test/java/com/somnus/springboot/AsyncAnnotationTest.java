package com.somnus.springboot;

import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.somnus.springboot.async.AsyncCallbackTask;
import com.somnus.springboot.async.AsyncTask;
import com.somnus.springboot.async.ListenableFutureTask;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AsyncAnnotationTest {
	
	@Autowired
	private AsyncTask asynctask;
	
	@Autowired
	private AsyncCallbackTask asyncCallbackTask;
	
	@Autowired
	private ListenableFutureTask listenableFutureTask;
	
	/**
	 * 可以反复执行单元测试，您可能会遇到各种不同的结果，比如：
	 * 			1、没有任何任务相关的输出
	 * 			2、有部分任务相关的输出
	 * 			3、乱序的任务相关的输出
	 * 原因是目前 doTaskOne 、 doTaskTwo 、 doTaskThree 三个函数的时候已经是异步执行了。
	 * 主程序在异步调用之后，主程序并不会理会这三个函数是否执行完成了，由于没有其他需要执行的内容，
	 * 所以程序就自动结束了，导致了不完整或是没有输出任务相关内容的情况。
	 * @throws Exception
	 */
	@Test
	public void asyncTask() throws Exception {
		asynctask.doTaskOne();
		asynctask.doTaskTwo();
		asynctask.doTaskThree();
	}

	/**
	 * 当前方法是用来测试异步回调的
	 * 异步本身是没有返回值的，当我们要异步回调，要拿到返回值又该怎么做，
	 * jdk本身提供了相关的concurrent类可以实现，
	 * 而结合spring的注解则使用Srping提供的的AsyncResult
	 * @throws Exception
	 */
	@Test
	public void asyncCallbackTask() throws Exception {
		long start = System.currentTimeMillis();

		Future<String> task1 = asyncCallbackTask.doTaskOne();
		Future<String> task2 = asyncCallbackTask.doTaskTwo();
		Future<String> task3 = asyncCallbackTask.doTaskThree();

		while (true) {
			if (task1.isDone() && task2.isDone() && task3.isDone()) {
				// 三个任务都调用完成，退出循环等待
				break;
			}
			Thread.sleep(1000);
		}

		long end = System.currentTimeMillis();

		System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

	}

	@Test
	public void listenableFutureTask() throws Exception {

		ListenableFuture<String> listenableFuture = listenableFutureTask.doTask();

		SuccessCallback<String> successCallback = new SuccessCallback<String>() {
			@Override
			public void onSuccess(String str) {
				System.out.println("异步回调成功了, return : " + str);
			}
		};
		FailureCallback failureCallback = new FailureCallback() {
			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("异步回调失败了, exception message : "
						+ throwable.getMessage());
			}
		};

		listenableFuture.addCallback(successCallback, failureCallback);

		Assert.assertEquals("任务完成", listenableFuture.get());
	}

}
