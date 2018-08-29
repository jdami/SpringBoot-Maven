package com.somnus.springboot;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ZookeeperTest {
	
	@Autowired
	private CuratorFramework client;
	
	/**
     * 测试创建节点
     * @throws Exception 
     */
    @Test
    public void testCreate() throws Exception{
        //创建永久节点
        client.create().forPath("/curator","/curator data".getBytes());
        
        //创建永久有序节点
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_sequential","/curator_sequential data".getBytes());
        
        //创建临时节点
        client.create().withMode(CreateMode.EPHEMERAL)
            .forPath("/curator/ephemeral","/curator/ephemeral data".getBytes());
 
        //创建临时有序节点
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
            .forPath("/curator/ephemeral_path1","/curator/ephemeral_path1 data".getBytes());
        
        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
        .forPath("/curator/ephemeral_path2","/curator/ephemeral_path2 data".getBytes());
    }
	
    /**
     * 测试检查某个节点是否存在
     * @throws Exception
     */
    @Test
    public void testCheck() throws Exception{
        Stat stat1 = client.checkExists().forPath("/curator");
        Stat stat2 = client.checkExists().forPath("/curator2");
        
        System.out.println("'/curator'是否存在： " + (stat1 != null ? true : false));
        System.out.println("'/curator2'是否存在： " + (stat2 != null ? true : false));
    }
    
    /**
     * 测试获取和设置节点数据
     * @throws Exception
     */
    @Test
    public void testGetAndSet() throws Exception{
        //获取某个节点的所有子节点
        System.out.println(client.getChildren().forPath("/"));
        
        //获取某个节点数据
        System.out.println(new String(client.getData().forPath("/curator")));
        
        //设置某个节点数据
        client.setData().forPath("/curator","/curator modified data".getBytes());
    }
    
    /**
     * 测试删除节点
     * @throws Exception 
     */
    @Test
    public void testDelete() throws Exception{
        //创建测试节点
        client.create().orSetData().creatingParentContainersIfNeeded()
            .forPath("/curator/del_key1","/curator/del_key1 data".getBytes());
 
        client.create().orSetData().creatingParentContainersIfNeeded()
        .forPath("/curator/del_key2","/curator/del_key2 data".getBytes());
        
        client.create().forPath("/curator/del_key2/test_key","test_key data".getBytes());
        
        
        //删除该节点
        client.delete().forPath("/curator/del_key1");
        
        //级联删除子节点
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/curator/del_key2");
    }
    
    @After
    public void tearDown() {
    	CloseableUtils.closeQuietly(client);
    }
}
