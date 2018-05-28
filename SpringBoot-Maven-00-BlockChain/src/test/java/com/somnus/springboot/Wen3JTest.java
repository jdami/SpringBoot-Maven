package com.somnus.springboot;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSON;

public class Wen3JTest {
	
	private static transient Logger	log = LoggerFactory.getLogger(Wen3JTest.class);
	
	public static void main(String[] args) throws Exception {
		 /*new Thread(() -> {
			 Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
				web3j.pendingTransactionObservable().subscribe(tx -> {
					log.info("http://114.141.173.24:945-->pending-->TransactionHash:"+tx.getHash());
				});
		 }).start();
		 
		 new Thread(() -> {
			 Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
				web3j.transactionObservable().subscribe(tx -> {
					log.info("http://114.141.173.24:945-->transaction-->TransactionHash:"+tx.getHash());
				});
		 }).start();*/

		 /*new Thread(() -> {
			 Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:1145/"));
				web3j.pendingTransactionObservable().subscribe(tx -> {
					log.info("http://114.141.173.24:1145-->pendingpending-->TransactionHash:"+JSON.toJSONString(tx));
				});
		 }).start();*/
		 
		 new Thread(() -> {
			 Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
			 Admin admin = Admin.build(new HttpService("http://114.141.173.24:945/"));
				web3j.transactionObservable().subscribe(tx -> {
					log.info("http://114.141.173.24:1145-->transactionObservable:"+JSON.toJSONString(tx));
					try {
						BigInteger blance =  admin.ethGetBalance("0xd5748425057ab4c046acd3555d9ccaba37554dfb",
								DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
						log.info("http://114.141.173.24:1145-->transactionObservable发送者:"+Convert.fromWei(blance.toString(), Convert.Unit.ETHER).toPlainString());
						
						BigInteger blance2 =  admin.ethGetBalance("0xaa796932f13367e701cbc129a1892a0a74cac6e3",
								DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
						log.info("http://114.141.173.24:1145-->transactionObservable接收者:"+Convert.fromWei(blance2.toString(), Convert.Unit.ETHER).toPlainString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				});
		 }).start();
		 
		 new Thread(() -> {
			 Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
			 Admin admin = Admin.build(new HttpService("http://114.141.173.24:945/"));
			 web3j.blockObservable(false).subscribe(block -> {
				 log.info("http://114.141.173.24:1145-->blockObservable-->"+JSON.toJSONString(block));
					try {
						BigInteger blance =  admin.ethGetBalance("0xd5748425057ab4c046acd3555d9ccaba37554dfb",
								DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
						log.info("http://114.141.173.24:1145-->blockObservable发送者-->"+Convert.fromWei(blance.toString(), Convert.Unit.ETHER).toPlainString());
						
						BigInteger blance2 =  admin.ethGetBalance("0xaa796932f13367e701cbc129a1892a0a74cac6e3",
								DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
						log.info("http://114.141.173.24:1145-->blockObservable接收者-->"+Convert.fromWei(blance2.toString(), Convert.Unit.ETHER).toPlainString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				});
		 }).start();
		 
		/* Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:1145/"));
		 TransactionReceiptProcessor transactionReceiptProcessor = 
				 new QueuingTransactionReceiptProcessor(web3j, 
						 new Callback() {

							@Override
							public void accept(TransactionReceipt transactionReceipt) {
								log.info("http://114.141.173.24:1145-->transaction-->TransactionHash:"+JSON.toJSONString(transactionReceipt));
							}

							@Override
							public void exception(Exception exception) {
								exception.printStackTrace();
							}
					 
				 }, 10000, 50000);
		 transactionReceiptProcessor.waitForTransactionReceipt("0x042198eb5d8d285122308eb2cde0e1d15ad3de1d2374fea500e142bf412e4f08");*/
		Thread.sleep(100000000000000000L);
	}
	
	@Test
	public void test945() throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
		Admin admin = Admin.build(new HttpService("http://114.141.173.24:945/")); 
		Credentials credentials = WalletUtils.loadCredentials("123456","E:/Apache Tomcat Server/UTC--2018-05-17T01-56-44.994400412Z--a22d660d863ebeb56243cbf43d311bf617acbdff");
		
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
				"0x21fd1b8307c2a441f85b80c90995e2adbd36fe29", 
				"123456").sendAsync().get();
		if (personalUnlockAccount.accountUnlocked()) {
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
					new BigInteger("10"), 
					ManagedTransaction.GAS_PRICE, 
					Contract.GAS_LIMIT, 
					"0x364008b1171a65823900a445e5c9a814d3c45a64", 
					new BigInteger(Convert.toWei("1", Convert.Unit.ETHER).toPlainString()));
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
			System.out.println(JSON.toJSONString(ethSendTransaction));
		}
		
		/*TransactionReceipt transferReceipt = Transfer.sendFunds(web3j, credentials,
                "0x364008b1171a65823900a445e5c9a814d3c45a64",
                BigDecimal.ONE, Convert.Unit.ETHER).sendAsync().get();
		System.out.println(JSON.toJSONString(transferReceipt));*/
	}
	
	@Test
	public void test1145() throws Exception{
		Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:1145/"));
		Admin admin = Admin.build(new HttpService("http://114.141.173.24:1145/")); 
		Credentials credentials = WalletUtils.loadCredentials("123456","E:/Apache Tomcat Server/UTC--2018-05-17T01-56-44.994400412Z--a22d660d863ebeb56243cbf43d311bf617acbdff");
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
				"0xa22d660d863ebeb56243cbf43d311bf617acbdff", 
				"123456").sendAsync().get();
		if (personalUnlockAccount.accountUnlocked()) {
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
					new BigInteger("11"), 
					ManagedTransaction.GAS_PRICE, 
					Contract.GAS_LIMIT, 
					"0x364008b1171a65823900a445e5c9a814d3c45a64", 
					new BigInteger(Convert.toWei("1", Convert.Unit.ETHER).toPlainString()));
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
			System.out.println(JSON.toJSONString(ethSendTransaction));
		}
		
	}
	
	@Test
	public void test945_2() throws Exception{
		Web3j web3j = Web3j.build(new HttpService("http://114.141.173.24:945/"));
		Admin admin = Admin.build(new HttpService("http://114.141.173.24:945/")); 
		Credentials credentials = WalletUtils.loadCredentials("123456","E:/Apache Tomcat Server/UTC--2018-05-17T01-56-44.994400412Z--a22d660d863ebeb56243cbf43d311bf617acbdff");
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
				"0x21fd1b8307c2a441f85b80c90995e2adbd36fe29", 
				"123456").sendAsync().get();
		if (personalUnlockAccount.accountUnlocked()) {
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
					new BigInteger("12"), 
					ManagedTransaction.GAS_PRICE, 
					Contract.GAS_LIMIT, 
					"0x364008b1171a65823900a445e5c9a814d3c45a64", 
					new BigInteger(Convert.toWei("1", Convert.Unit.ETHER).toPlainString()));
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
			System.out.println(JSON.toJSONString(ethSendTransaction));
		}
		
	}

}
