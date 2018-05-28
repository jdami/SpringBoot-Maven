package com.somnus.springboot;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.springboot.activemq.model.Account;
import com.somnus.springboot.activemq.service.JmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ActiveMQTest {
	
	@Autowired
	private JmsService jmsService;
	
	@Test
    public void initSpringContext() {
        try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/**
	 * 用来测试发送字符串类型的消息
	 * 当前配置是用来发送的Queue，p2p模型的jms消息
	 * 如果想发送Topic，sub/pub，需要修改配置，
	 * 			一个地方是JmsTemplate的pubSubDomain
	 * 			一个地方是是jms:listener-container的destination-type
	 */
    @Test
    public void sendStringMessage() {
    	jmsService.sendStringMessage("你好，生产者！这是字符串消息");
        try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
	 * 用来测试发送对象类型的消息
	 */
    @Test
    public void sendObjectMessage() {
    	jmsService.sendObjectMessage(new Account("10000",BigDecimal.ZERO));
        try {
			//防止Spring容器过早的关闭
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
