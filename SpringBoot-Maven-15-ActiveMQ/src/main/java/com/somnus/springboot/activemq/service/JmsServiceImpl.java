package com.somnus.springboot.activemq.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.somnus.springboot.activemq.model.Account;

@Service
public class JmsServiceImpl implements JmsService{
	
	private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    /**
	 * 发送一条字符串消息到指定的topic|queue
	 * @param message 消息内容
	 */
	public void sendStringMessage(String message){
		log.info("---------------生产者发送了一个字符串消息：" + message);
		jmsTemplate.send("jms.somnus.string.sample", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
		/*jmsTemplate.convertAndSend("jms.somnus.string.sample", message);*/
	}
	
	/**
	 * 发送一条序列化的 Java对象消息到指定的topic|queue
	 * @param message 消息内容
	 */
	public void sendObjectMessage(Account account){
		log.info("---------------生产者发送了一个序列化的对象消息：" + account);
		jmsTemplate.send("jms.somnus.object.sample", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(account);
			}
		});
		/*jmsTemplate.convertAndSend("jms.somnus.object.sample", message);*/
	}

}
