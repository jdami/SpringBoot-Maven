package com.somnus.springboot.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class RabbitMQJmsListener{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RabbitHandler
	public void execute(String message) throws Exception {
		log.info("接收字符串消息：{}", message);
		//TODO
		//消费者拿到想要的字符串,至于怎么处理就是你自己的事情了
	}
}
