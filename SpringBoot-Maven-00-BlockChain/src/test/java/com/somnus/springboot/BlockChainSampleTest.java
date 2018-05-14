package com.somnus.springboot;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
	
	private CountDownLatch cdl = new CountDownLatch(2);
	
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
	public void transfer() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");
        
		log.info("Sending 1 Ether (" + Convert.toWei("100", Convert.Unit.ETHER).toPlainString() + " wei)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0xd3cb317c04230af21759cde39198b1e14130aa5f", 
                BigDecimal.ONE, Convert.Unit.ETHER).send();
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());
	}
	
	@Test
	public void run() throws Exception {
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
        TransactionReceipt tran = contract.greet2().send();
        long end = System.currentTimeMillis();
        log.info("\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
				"BlockHash:" + tran.getBlockHash()+"\r\n" +
				"BlockNumber:" + tran.getBlockNumber()+"\r\n" +
				"GasUsed:" + tran.getGasUsed());

    }
	
	@Test
	public void batchInsert() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <=1; i++) {
			final int taskID = i;
			Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
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
				        GreeterContract contract = GreeterContract.load("0xb1ac7167e3f13c8f8aad9be2a01e80999c206569",
				                web3j, credentials,
				                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

				        long start = System.currentTimeMillis();
				        TransactionReceipt tran = contract.greet2().send();
				        long end = System.currentTimeMillis();
				        log.info("\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
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
