package com.somnus.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.somnus.springboot.event.EmailEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EventAnnotationTest {
	
	/** 父子容器都可以注入 */
	@Autowired  
    private ApplicationContext applicationConnect;
	
	@Autowired  
    private WebApplicationContext webApplicationConnect;
	
	/**
	 * 注解的形式实现的spring事件驱动，
	 * 只需在监听类的方法中加入@EventListener，
	 * 另外监听还支持springel表达式，如果条件满足才会执行
	 */
	@Test
	public void handleEmail(){
		//满足条件，会触发
		applicationConnect.publishEvent(new EmailEvent(this, "10000@qq.com", "I love you"));
		
		System.out.println("上面的，你走你的异步去，我已经执行了，come on！");
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
        try {
			String source = bf.readLine();
			System.out.println(source);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 这个测试则是条件不满足，事件驱动并不会执行
	 */
	@Test
	public void handleEmailUndo(){
		//不满足条件，会触发
		webApplicationConnect.publishEvent(new EmailEvent(this, "10086@qq.com", "I love you"));
		
		System.out.println("上面的，你走你的异步去，我已经执行了，come on！");
		try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
