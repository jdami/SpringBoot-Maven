package com.somnus.springboot;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSON;
import com.somnus.springboot.contract.GreeterContract;
import com.somnus.springboot.contract.TokenContract;

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
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private CountDownLatch cdl = new CountDownLatch(20);
	
	private StopWatch sw = new StopWatch();
	
	
	@Before
    public void before() {
		sw.start();
		/*web3j.pendingTransactionObservable().subscribe(tx -> {
			System.out.println("pending-->nonce:"+tx.getNonce());
			System.out.println("pending-->from:"+tx.getFrom());
			System.out.println("pending-->Gas:"+tx.getGas());
			System.out.println("pending-->GasPrice:"+tx.getGasPrice());
			log.info("pending-->TransactionHash:"+tx.getHash());
		});*/
		/*web3j.transactionObservable().subscribe(tx -> {
			System.out.println("subscription-->nonce:"+tx.getNonce());
			System.out.println("subscription-->BlockNumber:"+tx.getBlockNumber());
			System.out.println("subscription-->BlockHash:"+tx.getBlockHash());
			System.out.println("subscription-->Gas:"+tx.getGas());
			System.out.println("subscription-->GasPrice:"+tx.getGasPrice());
		});*/
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@Test
	public void getClientVersion() throws Exception {
		Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
		System.out.println(web3ClientVersion.getWeb3ClientVersion());
		
		System.out.println(JSON.toJSONString(web3j.ethGetBlockByNumber(
				new DefaultBlockParameterNumber(44), true).sendAsync().get(),true));
		
		System.out.println(JSON.toJSONString(web3j.ethGetTransactionByHash("0x1cd8e99baed19a378c0189ec93481c396e54c6e1bd09e5bf8a44ff9a1ffb9ecf").sendAsync().get(),true));
		
		System.out.println(JSON.toJSONString(web3j.ethGetTransactionByBlockNumberAndIndex(
				new DefaultBlockParameterNumber(44), new BigInteger("0")).sendAsync().get(),true));
	}
	
	@Test
	public void redis() throws Exception {
		List<String> list = admin.personalListAccounts().send().getAccountIds();
		for(int i = 1; i <=20; i++) {
			/*stringRedisTemplate.opsForValue().set(list.get(i), "-1");*/
			/*stringRedisTemplate.opsForValue().increment(list.get(i), 1);*/
			System.out.println(i+"-redis->"+list.get(i)+"-->"+stringRedisTemplate.opsForValue().get(list.get(i)));
		}
	}
	
	@Test
	public void listAccounts() throws Exception {
		List<String> list = admin.personalListAccounts().send().getAccountIds();
		list.forEach(System.out::println);
	}
	
	@Test
	public void queryNonce() throws Exception {
		System.out.println("-->"+web3j.ethGetTransactionCount("0x2cd44f15a8d1a6cc9d51677d6adeac6e12d4af77",DefaultBlockParameterName.PENDING).sendAsync().get().getTransactionCount());
		List<String> list = admin.personalListAccounts().send().getAccountIds();
		for(int i = 1; i <=20; i++) {
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
					list.get(i),
					DefaultBlockParameterName.PENDING).sendAsync().get();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			System.out.println(i+"-nonce->"+list.get(i)+"-->期望的nonce:"+nonce);
		}
	}
	
	@Test
	public void queryBlance() throws Exception {
		/*BigInteger blance =  admin.ethGetBalance("0x3fdab138c4dec6e76fb81c36b044f22f22d44cfe",DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
		System.out.println(Convert.fromWei(blance.toString(), Convert.Unit.ETHER).toPlainString());
		
		BigInteger blance2 =  admin.ethGetBalance("0x693465f131580cc987fabd58b92e4a266db35c74",DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
		System.out.println(Convert.fromWei(blance2.toString(), Convert.Unit.ETHER).toPlainString());*/
		
		List<String> list = admin.personalListAccounts().send().getAccountIds();
		for(String data:list) {
			BigInteger blancee =  admin.ethGetBalance(data,DefaultBlockParameterName.LATEST).sendAsync().get().getBalance();
			System.out.println(Convert.fromWei(blancee.toString(), Convert.Unit.ETHER).toPlainString());
		}
	}
	
	@Test
	public void initUser() throws Exception {
		for(int i = 0; i <20; i++) {
			NewAccountIdentifier send = admin.personalNewAccount(password).sendAsync().get();
			String address = send.getResult();
			System.out.println(address);
		}
	}
	
	@Test
	public void transfer() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");
        for(int i = 1; i <= 1; i++) {
        	TransactionReceipt transferReceipt = Transfer.sendFunds(web3j, credentials,
        			"0x693465f131580cc987fabd58b92e4a266db35c74",
                    new BigDecimal("10"), Convert.Unit.ETHER).sendAsync().get();
    		System.out.println(JSON.toJSONString(transferReceipt));
            log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/");
        }
	}
	
	/**
	 * 同一个地址(挖矿账户)给不同地址转账
	 */
	@Test
	public void transferBatch2() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
				web3j.ethCoinbase().sendAsync().get().getAddress(), //挖矿账户
				password).sendAsync().get();
        for(int i = 1; i <= 20; i++) {
			try {
				List<String> list = admin.personalListAccounts().send().getAccountIds();
				long nonce = stringRedisTemplate.opsForValue().increment(web3j.ethCoinbase().sendAsync().get().getAddress(), 1);
				if (personalUnlockAccount.accountUnlocked()) {
					RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
							BigInteger.valueOf(nonce), 
							ManagedTransaction.GAS_PRICE, 
							Contract.GAS_LIMIT, 
							list.get(i), 
							new BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toPlainString()));
					byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
					String hexValue = Numeric.toHexString(signedMessage);
					EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
					log.info(JSON.toJSONString(ethSendTransaction));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void transfer3() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		LocalDateTime now = LocalDateTime.now();
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(
					web3j.ethCoinbase().sendAsync().get().getAddress(), //挖矿账户
					password).sendAsync().get();
			
			if (personalUnlockAccount.accountUnlocked()) {
				RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
						new BigInteger("30"), 
						BigInteger.valueOf(32_000_000_000L), 
						BigInteger.valueOf(4_500_000), 
						"0x911f4c02fc21b610a5c04eed3b00aab1637e7ca5", 
						new BigInteger(Convert.fromWei("100", Convert.Unit.WEI).toPlainString()));
				byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
				String hexValue = Numeric.toHexString(signedMessage);
				EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
				String transactionHash = ethSendTransaction.getTransactionHash();
				System.out.println(JSON.toJSONString(ethSendTransaction,true));
				LocalDateTime _now = LocalDateTime.now();
				long millis = Duration.between(now, _now).toMillis();
				log.info("交易transactionHash={},交易transaction时间time={}", transactionHash, millis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 同一个地址(挖矿账户)给不同地址转账
	 */
	@Test
	public void transferBatch4() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		for (int i = 1; i <=20; i++) {
			final int userID = i;
			List<String> list = admin.personalListAccounts().send().getAccountIds();
			String path = BundleUtil.getString("web3j.walletPath"+i);
			Credentials credentials = WalletUtils.loadCredentials(password,path);
			//解锁账户
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(list.get(i),password).sendAsync().get();
			executor.execute(() -> {
				try {
					for(int j = 1; j <= 100; j++) {
						/*long nonce = 0;
						synchronized (personalUnlockAccount){
							nonce = stringRedisTemplate.opsForValue().increment(list.get(userID), 1);
							System.out.println("userid:" + userID + "-->" + j + "-->" + nonce);
						}*/
						long nonce = stringRedisTemplate.opsForValue().increment(list.get(userID), 1);
						/*System.out.println("userid:" + userID + "-->" + j + "-->" + nonce);*/
						if (personalUnlockAccount.accountUnlocked()) {
							RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
									BigInteger.valueOf(nonce), 
									ManagedTransaction.GAS_PRICE, 
									BigInteger.valueOf(50_000), 
									web3j.ethCoinbase().sendAsync().get().getAddress(), //转给挖矿账户
									new BigInteger(Convert.fromWei("1", Convert.Unit.WEI).toPlainString()));
							byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
							String hexValue = Numeric.toHexString(signedMessage);
							EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
							log.info(ethSendTransaction.getTransactionHash()+"-->"+JSON.toJSONString(ethSendTransaction));
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
		        cdl.countDown();
			});
		}
		cdl.await();
		executor.shutdown();
	}
	
	@Test
	public void deploy() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");

        // Now lets deploy a smart contract
        log.info("Deploying smart contract");
        GreeterContract contract = GreeterContract.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,
                "①②③④").send();

        String contractAddress = contract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
    }
	
	@Test
	public void write() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		log.info("Credentials loaded");
		for (int j = 0; j < 10; j++) {
			try {
				String msg = FileUtils.readFileToString(
						new File(
								Thread.currentThread().getContextClassLoader().getResource("solidity/param/64BYTE.txt").getPath()),
								"UTF-8");
				System.out.println("#########" + msg.getBytes().length + "byte");
		        GreeterContract contract = GreeterContract.load("0x970ec0c752bc196e2220c48644c8be1877772d70",
		                web3j, credentials,
		                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

		        long start = System.currentTimeMillis();
		        TransactionReceipt tran = contract.write(msg).sendAsync().get();
		        long end = System.currentTimeMillis();
		        log.info("\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
						"BlockHash:" + tran.getBlockHash()+"\r\n" +
						"BlockNumber:" + tran.getBlockNumber()+"\r\n" +
						"GasUsed:" + tran.getGasUsed());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 不同用户写入字符串  */
	@Test
	public void batchWrite() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <=10; i++) {
			final int taskID = i;
			Credentials credentials = WalletUtils.loadCredentials(password,BundleUtil.getString("web3j.walletPath"+i));
	        log.info("Credentials loaded");
			executor.execute(() -> {
				for (int j = 0; j < 1; j++) {
					try {
						String msg = FileUtils.readFileToString(
								new File(
										Thread.currentThread().getContextClassLoader().getResource("solidity/param/64BYTE.txt").getPath()),
										"UTF-8");
						System.out.println("#########" + msg.getBytes().length + "byte");
				        GreeterContract contract = GreeterContract.load("0xc3f59f520d4dcb9611104aa0c5b4d8bc57332c04",
				                web3j, credentials,
				                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

				        long start = System.currentTimeMillis();
				        TransactionReceipt tran = contract.write(msg).sendAsync().get();
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
	
	/** 同一用户写入字符串  */
	@Test
	public void batchWrite2() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");
		for (int i = 0; i <10; i++) {
			final int taskID = i;
			executor.execute(() -> {
				for (int j = 0; j < 1; j++) {
					try {
				        GreeterContract contract = GreeterContract.load("0xf29f61a83e0b76344258ad57f6620be4f4f8122a",
				                web3j, credentials,
				                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

				        long start = System.currentTimeMillis();
				        TransactionReceipt tran = contract.write("hello world").sendAsync().get();
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
	
	/** 同一用户写入字符串  */
	
	@Test
	public void queryString() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		log.info("Credentials loaded");
		GreeterContract contract = GreeterContract.load("0xf29f61a83e0b76344258ad57f6620be4f4f8122a",
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

        String tran = contract.greet().sendAsync().get();
        System.out.println(tran);
	}
	
	@After
    public void after() {
		sw.stop();
		System.out.println("共消耗：" + sw.getTotalTimeMillis());
	}
	
	@Test
	public void deployToken() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
        log.info("Credentials loaded");

        // Now lets deploy a smart contract
        log.info("Deploying smart contract");
        GreeterContract contract = TokenContract.deploy(
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();

        String contractAddress = contract.getContractAddress();
        log.info("Smart contract deployed to address " + contractAddress);
    }
	
	/** 同一个用户代币转账 */
	@Test
	public void transferToken() throws Exception {
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		log.info("Credentials loaded");
		for (int i = 0; i < 10; i++) {
			try {
				TokenContract contract = TokenContract.load("0xed3d07f1ce83e93b4ce372cb8ddcc4d0a9d384d4",
		                web3j, credentials,
		                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
				List<String> list = admin.personalListAccounts().send().getAccountIds();
		        long start = System.currentTimeMillis();
		        TransactionReceipt tran = contract.transfer(list.get(i),BigInteger.TEN).sendAsync().get();
		        long end = System.currentTimeMillis();
		        log.info("\r\n 消耗时间:" +(end - start) + "-->TransactionHash:"+ tran.getTransactionHash() +"\r\n" +
						"BlockHash:" + tran.getBlockHash()+"\r\n" +
						"BlockNumber:" + tran.getBlockNumber()+"\r\n" +
						"GasUsed:" + tran.getGasUsed());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void transferToken4() throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 1; i <=100; i++) {
			final int userID = i;
			List<String> list = admin.personalListAccounts().send().getAccountIds();
			Credentials credentials = WalletUtils.loadCredentials(password,BundleUtil.getString("web3j.walletPath"+i));
			executor.execute(() -> {
				try {
					for(int j = 1; j <= 10; j++) {
						long nonce = stringRedisTemplate.opsForValue().increment(list.get(userID), 1);
						System.out.println(list.get(userID)+"******************"+nonce);
						Function function = new Function(
								"_transfer", //交易的方法名称
				                Arrays.asList(new Address("0x7227385614b0da0ff2390d3fd6b00a39c8aaa986"),new Uint256(BigInteger.TEN)), 
				                Arrays.asList(new TypeReference<Address>(){},new TypeReference<Uint256>(){}));
					    String functionEncoder = FunctionEncoder.encode(function);
					    //智能合约事务
					    RawTransaction rawTransaction = RawTransaction.createTransaction(
					    		BigInteger.valueOf(nonce),
								ManagedTransaction.GAS_PRICE, 
								Contract.GAS_LIMIT,  
								"0x8d049e2b53470b3c30a06e15c13826fb64ad5268", //contractAddress
								functionEncoder);
					    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
					    String hexValue = Numeric.toHexString(signedMessage);
					    EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
					    log.info(JSON.toJSONString(ethSendTransaction));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
		        cdl.countDown();
			});
		}
		cdl.await();
	}
	
	@Test
	public void queryToken() throws Exception{
		Credentials credentials = WalletUtils.loadCredentials(password,walletPath);
		log.info("Credentials loaded");
		try {
			TokenContract contract = TokenContract.load("0x77df347ec4550a96fe3a847d69e363da15ec98e7",
	                web3j, credentials,
	                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
	        BigInteger tran = contract.balanceOf("0xb5564ebffe37525ab8abb3d9403530b0f99c153d").sendAsync().get();
	        log.info(tran.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
