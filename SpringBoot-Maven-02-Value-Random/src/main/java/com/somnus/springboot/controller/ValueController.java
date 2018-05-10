package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValueController {
	
	@Value("${com.somnus.email}")
	private String email;
	
	@Value("${com.somnus.value}")
	private String value;
	
	@Value("${com.somnus.number}")
	private String number;
	
	@Value("${com.somnus.bignumber}")
	private String bignumber;
	
	@Value("${com.somnus.test1}")
	private String test1;
	
	@Value("${com.somnus.test1}")
	private String test2;

	@GetMapping(value = "/email")
	public String email() {
		return email;
	}
	
	@GetMapping(value = "/random")
	public String random() {
		return "随机字符串:"		+ value		+ "<br/><br/>" +
			   "随机int:"			+ number	+ "<br/><br/>" +
			   "随机long:"		+ bignumber + "<br/><br/>" +
			   "10以内的随机数:"	+ test1		+ "<br/><br/>" +
			   "10-20的随机数:"	+ test2		+ "<br/><br/>" ;
	}

}