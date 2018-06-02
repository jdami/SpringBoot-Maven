package com.somnus.springboot.rabbitmq.service;


public interface JmsService {
	
	public void sendStringMessage(String message);
	
}
