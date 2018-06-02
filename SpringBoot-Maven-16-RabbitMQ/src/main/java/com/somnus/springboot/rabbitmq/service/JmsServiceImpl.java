package com.somnus.springboot.rabbitmq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JmsServiceImpl implements JmsService{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private AmqpTemplate rabbitTemplate;
    
    /**
	 * 发送一条字符串消息到指定的topic|queue
	 * @param message 消息内容
	 */
	public void sendStringMessage(String message){
		log.info("---------------生产者发送了一个字符串消息：" + message);
		this.rabbitTemplate.convertAndSend("hello", message);
	}

}
