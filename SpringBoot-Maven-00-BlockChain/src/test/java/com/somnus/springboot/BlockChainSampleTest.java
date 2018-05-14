package com.somnus.springboot;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.somnus.springboot.contract.GreeterContract;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BlockChainSampleTest {
	
	private transient Logger	log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${web3j.walletPath}") 
	private String walletPath;
	
	@Value("${web3j.password}")
	private String password;

	@Autowired
	private Web3j web3j;
	
	@Autowired
	private Admin admin;
	
	private CountDownLatch cdl = new CountDownLatch(3);
	
	private StopWatch sw = new StopWatch();
	
	
	@Before
    public void before() {
		sw.start();
	}

	@Test
	public void getClientVersion() throws IOException {
		Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
		System.out.println(web3ClientVersion.getWeb3ClientVersion());
	}
	
	@Test
	public void initUser() throws Exception {
		for(int i = 1; i <=100; i++) {
			String address = admin.personalNewAccount("123456").sendAsync().get().getAccountId();
			System.out.println(address);
		}
	}
	
	@Test
	public void transfer() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");
        
		log.info("Sending 1 Ether (" + Convert.toWei("100", Convert.Unit.ETHER).toPlainString() + " Wei)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0x364008b1171a65823900a445e5c9a814d3c45a64",
                BigDecimal.ONE, Convert.Unit.ETHER).send();
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
	}
	
	/**
	 * 挖矿地址给不同地址转账
	 */
	@Test
	public void transferBatch() throws Exception {
		for(int i = 1; i <=100; i++) {
			Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
			log.info("Credentials loaded");
			
			log.info("Sending 1 Ether (" + Convert.toWei("10", Convert.Unit.ETHER).toPlainString() + " Wei)");
			List<String> list = admin.personalListAccounts().send().getAccountIds();
			TransactionReceipt transferReceipt = Transfer.sendFunds(
			        web3j, credentials,
			        list.get(i),
			        BigDecimal.ONE, Convert.Unit.ETHER).send();
			log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
			        + transferReceipt.getTransactionHash());
		}
	}
	
	/**
	 * 不同的地址给同一个地址转账
	 */
	@Test
	public void transferBatch2() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <=100; i++) {
			Credentials credentials = WalletUtils.loadCredentials(password,BundleUtil.getString("web3j.walletPath"+i));
	        log.info("Credentials loaded");
			executor.execute(() -> {
				for(int j = 0; j < 1000; j++) {
					log.info("Credentials loaded");
					
					log.info("Sending 1 Wei (" + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
					try {
						TransactionReceipt transferReceipt = Transfer.sendFunds(
						        web3j, credentials,
						        "0x3299170f239b7f90c29517293c6234aa1139f254", 
						        BigDecimal.ONE, Convert.Unit.ETHER).send();
						log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
						        + transferReceipt.getTransactionHash());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				cdl.countDown();
			});
		}
		cdl.await();
	}
	
	@Test
	public void deploy() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");

        // Now lets deploy a smart contract
        log.info("Deploying smart contract");
        //1024(byte)=1KB
		String msg = FileUtils.readFileToString(
				new File(
						Thread.currentThread().getContextClassLoader().getResource("solidity/param/128BYTE.txt").getPath()),
						"UTF-8");
		System.out.println("--------------------------------" + msg.getBytes().length + "byte");
        GreeterContract contract = GreeterContract.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                msg).send();

        String contractAddress = contract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);

        long start = System.currentTimeMillis();
        TransactionReceipt tran = contract.write(msg).send();
        long end = System.currentTimeMillis();
        log.info("\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
				"BlockHash:" + tran.getBlockHash()+"\r\n" +
				"BlockNumber:" + tran.getBlockNumber()+"\r\n" +
				"GasUsed:" + tran.getGasUsed());

    }
	
	@Test
	public void batchInsert() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <=3; i++) {
			final int taskID = i;
			Credentials credentials = WalletUtils.loadCredentials(password,BundleUtil.getString("web3j.walletPath"+i));
	        log.info("Credentials loaded");
			executor.execute(() -> {
				for (int j = 0; j < 10; j++) {
					try {
				        //1024(byte)=1KB
						String msg = FileUtils.readFileToString(
								new File(
										Thread.currentThread().getContextClassLoader().getResource("solidity/param/128BYTE.txt").getPath()),
										"UTF-8");
						System.out.println("--------------------------------" + msg.getBytes().length + "byte");
				        GreeterContract contract = GreeterContract.load("0xdf4c3e660789e2fe4640969f3a6770d918c83554",
				                web3j, credentials,
				                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

				        long start = System.currentTimeMillis();
				        TransactionReceipt tran = contract.write(msg).send();
				        long end = System.currentTimeMillis();
				        log.info("\r\n 用户:" +taskID +"-->" +"\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
								"BlockHash:" + tran.getBlockHash()+"\r\n" +
								"BlockNumber:" + tran.getBlockNumber()+"\r\n" +
								"GasUsed:" + tran.getGasUsed());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				cdl.countDown();
			});
		}
		cdl.await();
	}
	
	
	@After
    public void after() {
		sw.stop();
		System.out.println("共消耗：" + sw.getTotalTimeMillis());
	}

}
