package com.somnus.springboot.activemq.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable{
	private static final long serialVersionUID = 1L;

	public String acctCode;
	
	public BigDecimal balance;
	
	public Account(String acctCode, BigDecimal balance) {
		super();
		this.acctCode = acctCode;
		this.balance = balance;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}