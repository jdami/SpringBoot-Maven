package com.somnus.springboot.activemq.service;

import com.somnus.springboot.activemq.model.Account;

public interface JmsService {
	
	public void sendStringMessage(String message);
	
	
	public void sendObjectMessage(Account account);
}
