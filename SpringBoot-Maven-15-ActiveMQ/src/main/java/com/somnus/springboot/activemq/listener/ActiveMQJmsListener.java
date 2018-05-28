package com.somnus.springboot.activemq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.somnus.springboot.activemq.model.Account;


@Component
public class ActiveMQJmsListener{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	@JmsListener(destination = "${jms.somnus.string.sample}")
	public void execute(String message) throws Exception {
		log.info("接收字符串消息：{}", message);
		//TODO
		//消费者拿到想要的字符串,至于怎么处理就是你自己的事情了
	}
	
	@JmsListener(destination = "${jms.somnus.object.sample}")
	public void execute(Account account) throws Exception {
		log.info("接收序列化的 Java对象消息：{}", account);
		//TODO
		//消费者拿到想要的Obejct,至于怎么处理就是你自己的事情了
	}
}
