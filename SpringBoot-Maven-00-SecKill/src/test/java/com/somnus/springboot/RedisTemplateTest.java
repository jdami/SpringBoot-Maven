package com.somnus.springboot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTemplateTest {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void stringSet(){
		//添加记录,如果记录已存在将覆盖原有的value
		stringRedisTemplate.opsForValue().set("username", "Somnus");
		
		System.out.println(stringRedisTemplate.opsForValue().get("username"));
	}
	
	@Test
	public void stringSetNx(){
		//添加一条记录，仅当给定的key不存在时才插入
		System.out.println(stringRedisTemplate.opsForValue().setIfAbsent("username", "somnus"));
		
		System.out.println(stringRedisTemplate.opsForValue().get("username"));
	}
	
	@Test
	public void stringSetEx() throws InterruptedException{
		//添加有过期时间的记录
		stringRedisTemplate.opsForValue().set("password", "passw0rd", 10, TimeUnit.SECONDS);
		
		Thread.sleep(15*1000);
		
		System.out.println(stringRedisTemplate.opsForValue().get("password"));
	}
	
	@Test
	public void stringSetrange(){
		stringRedisTemplate.opsForValue().set("email", "928200207@qqq.com");
		//从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
		stringRedisTemplate.opsForValue().set("email", "163", 10);
		
		System.out.println(stringRedisTemplate.opsForValue().get("email"));
		//对指定key对应的value进行截取
		System.out.println(stringRedisTemplate.opsForValue().get("email",0, 8));
	}
	
	@Test
	public void stringMset(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "1");
		map.put("key2", "2");
		map.put("key3", "3");
		map.put("key4", "4");
		stringRedisTemplate.opsForValue().multiSet(map);//批量存储记录
		
		//批量获取记录,如果指定的key不存在返回List的对应位置将是null
		System.out.println(stringRedisTemplate.opsForValue().multiGet(
				Arrays.asList(new String[]{"key1","key2","key3","key4"})));
	}
	
	@Test
	public void stringMsetnx(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key1", "1");
		map.put("key2", "2");
		map.put("key3", "3");
		map.put("key5", "5");
		System.out.println(stringRedisTemplate.opsForValue().multiSetIfAbsent(map));//批量存储记录
		
		//批量获取记录,如果指定的key不存在返回List的对应位置将是null
		System.out.println(stringRedisTemplate.opsForValue().multiGet(
				Arrays.asList(new String[]{"key1","key2","key3","key5"})));
	}
	
	@Test
	public void stringGetset(){
		//获取并设置指定key对应的value
		System.out.println(stringRedisTemplate.opsForValue().getAndSet("email", "love@qq.com"));
		
		System.out.println(stringRedisTemplate.opsForValue().get("email"));
	}
	
	@Test
	public void stringIncrBy(){
		stringRedisTemplate.opsForValue().set("age", "25");
		
		//将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		System.out.println(stringRedisTemplate.opsForValue().increment("age", 2));
		
		//将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
		System.out.println(stringRedisTemplate.opsForValue().increment("age", -4));
	}
	
	@Test
	public void stringAppend(){
		//在指定的key中追加value
		System.out.println(stringRedisTemplate.opsForValue().append("email", ".cn"));
	}
	
	@Test
	public void stringLen(){
		//获取key对应的值的长度
		System.out.println(stringRedisTemplate.opsForValue().size("email"));
	}
	// ******************对存储结构为String类型的操作结束*********************************************//
	@Test
	public void hashSet(){
		stringRedisTemplate.opsForHash().put("user", "username", "admin");//添加一个对应关系
		stringRedisTemplate.opsForHash().put("user", "password", "123456");//添加一个对应关系
		stringRedisTemplate.opsForHash().put("user", "age", "25");//添加一个对应关系
		
		//返回hash中指定存储位置的值
		System.out.println(stringRedisTemplate.opsForHash().get("user", "username"));
		System.out.println(stringRedisTemplate.opsForHash().get("user", "password"));
		System.out.println(stringRedisTemplate.opsForHash().get("user", "age"));
	}
	
	@Test
	public void hashSetnx(){
		//添加对应关系，只有在fieid不存在时才执行
		System.out.println(stringRedisTemplate.opsForHash().putIfAbsent("user", "username", "guest"));
		//添加对应关系，只有在fieid不存在时才执行
		System.out.println(stringRedisTemplate.opsForHash().putIfAbsent("user", "password", "passw0rd"));
		
		System.out.println(stringRedisTemplate.opsForHash().get("user", "username"));
		System.out.println(stringRedisTemplate.opsForHash().get("user", "password"));
	}
	
	@Test
	public void hashMset(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "admin");
		map.put("password", "passw0rd");
		//添加对应关系，如果对应关系已存在，则覆盖
		stringRedisTemplate.opsForHash().putAll("people", map);
		
		//根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
		System.out.println(stringRedisTemplate.opsForHash().multiGet(
				"people", Arrays.asList(new Object[]{"username","password"})));
	}
	
	@Test
	public void hashHincrBy(){
		//指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
		System.out.println(stringRedisTemplate.opsForHash().increment("user", "age", 2));
	}
	
	@Test
	public void hashExists(){
		//测试hash中指定的存储是否存在
		System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "age"));
		//测试hash中指定的存储是否存在
		System.out.println(stringRedisTemplate.opsForHash().hasKey("people", "age"));
	}
	
	@Test
	public void hashLen(){
		//获取hash中存储的个数，类似Map中size方法
		System.out.println(stringRedisTemplate.opsForHash().size("user"));
		//获取hash中存储的个数，类似Map中size方法
		System.out.println(stringRedisTemplate.opsForHash().size("people"));
	}
	
	@Test
	public void hashDel(){
		//从hash中删除指定的存储
		stringRedisTemplate.opsForHash().delete("user", "username", "age");
		//测试hash中指定的存储是否存在
		System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "username"));
		//测试hash中指定的存储是否存在
		System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "password"));
		//测试hash中指定的存储是否存在
		System.out.println(stringRedisTemplate.opsForHash().hasKey("user", "age"));
	}
	
	@Test
	public void hashKeys(){
		//返回指定hash中的所有存储名字,类似Map中的keySet方法
		System.out.println(stringRedisTemplate.opsForHash().keys("user"));
		//获取hash中value的集合
		System.out.println(stringRedisTemplate.opsForHash().values("user"));
		//以Map的形式返回hash中的存储和值
		System.out.println(stringRedisTemplate.opsForHash().entries("user"));
	}
	// *****************对存储结构为HashMap类型的操作结束*********************************************//
	@Test
	public void listLpush(){
		stringRedisTemplate.delete("arraylist");
		//向List尾部追加记录
		System.out.println(stringRedisTemplate.opsForList().leftPush("arraylist", "a"));
		//向List尾部追加记录
		System.out.println(stringRedisTemplate.opsForList().leftPush("arraylist", "b"));
		//向List尾部追加记录
		System.out.println(stringRedisTemplate.opsForList().leftPush("arraylist", "c"));
		//向List尾部追加记录
		System.out.println(stringRedisTemplate.opsForList().leftPush("arraylist", "d"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listRpush(){
		stringRedisTemplate.delete("arraylist");
		//向List头部追加记录
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		//向List头部追加记录
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		//向List头部追加记录
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "c"));
		//向List头部追加记录
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listInsert(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		
		//在value的相对位置前面插入记录
		System.out.println(stringRedisTemplate.opsForList().leftPush("arraylist", "a", "1"));
		//在value的相对位置后面插入记录
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b", "2"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listSet(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		
		//覆盖操作,将覆盖List中指定位置的值
		stringRedisTemplate.opsForList().set("arraylist", 1, "$");
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listRem(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
		
		//删除List中c条记录，被删除的记录值为value
		System.out.println(stringRedisTemplate.opsForList().remove("arraylist", 2, "b"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listTrim(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "c"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
		
		stringRedisTemplate.opsForList().trim("arraylist", 1, 2);//算是删除吧，只保留start与end之间的记录
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listPop(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "c"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "d"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
		
		//将List中的第一条记录移出List
		System.out.println(stringRedisTemplate.opsForList().leftPop("arraylist"));
		//将List中最后第一条记录移出List
		System.out.println(stringRedisTemplate.opsForList().rightPop("arraylist"));
		
		System.out.println(stringRedisTemplate.opsForList().range("arraylist", 0, -1));
	}
	
	@Test
	public void listIndex(){
		stringRedisTemplate.delete("arraylist");
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "a"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "b"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "c"));
		System.out.println(stringRedisTemplate.opsForList().rightPush("arraylist", "d"));
		
		//获取List中指定位置的值
		System.out.println(stringRedisTemplate.opsForList().index("arraylist8",2));
		System.out.println(stringRedisTemplate.opsForList().size("arraylist"));//List长度
	}
	// ******************对存储结构为List类型的操作 结束***********************************************//
	@Test
	public void setAdd(){
		stringRedisTemplate.delete("hashset");
		System.out.println(stringRedisTemplate.opsForSet().add("hashset", "a","b","c","c"));
		
		System.out.println(stringRedisTemplate.opsForSet().remove("hashset", "a"));
		
		System.out.println(stringRedisTemplate.opsForSet().members("hashset"));
		
		//从集合中随机弹出（删除）成员
		System.out.println(stringRedisTemplate.opsForSet().pop("hashset"));
		
		System.out.println(stringRedisTemplate.opsForSet().members("hashset"));
		
	}
	
	@Test
	public void setDiff(){
		stringRedisTemplate.delete("hashset");
		stringRedisTemplate.delete("hashset2");
		stringRedisTemplate.delete("hashset3");
		System.out.println(stringRedisTemplate.opsForSet().add("hashset", "a","b"));
		System.out.println(stringRedisTemplate.opsForSet().add("hashset2", "b","c"));
		
		//返回从第一组和所有的给定集合之间的差异的成员
		System.out.println(stringRedisTemplate.opsForSet().difference("hashset", "hashset2"));
		//这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
		System.out.println(stringRedisTemplate.opsForSet().differenceAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(stringRedisTemplate.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setInter(){
		stringRedisTemplate.delete("hashset");
		stringRedisTemplate.delete("hashset2");
		stringRedisTemplate.delete("hashset3");
		System.out.println(stringRedisTemplate.opsForSet().add("hashset", "a","b"));
		System.out.println(stringRedisTemplate.opsForSet().add("hashset2", "b","c"));
		
		//返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
		System.out.println(stringRedisTemplate.opsForSet().intersect("hashset", "hashset2"));
		//这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
		System.out.println(stringRedisTemplate.opsForSet().intersectAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(stringRedisTemplate.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setUnion(){
		stringRedisTemplate.delete("hashset");
		stringRedisTemplate.delete("hashset2");
		stringRedisTemplate.delete("hashset3");
		System.out.println(stringRedisTemplate.opsForSet().add("hashset", "a","b"));
		System.out.println(stringRedisTemplate.opsForSet().add("hashset2", "b","c"));
		
		//合并多个集合并返回合并后的结果，合并后的结果集合并不保存
		System.out.println(stringRedisTemplate.opsForSet().union("hashset", "hashset2"));
		//合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
		System.out.println(stringRedisTemplate.opsForSet().unionAndStore("hashset", "hashset2", "hashset3"));
		System.out.println(stringRedisTemplate.opsForSet().members("hashset3"));
	}
	
	@Test
	public void setMove(){
		stringRedisTemplate.delete("hashset");
		stringRedisTemplate.delete("hashset2");
		System.out.println(stringRedisTemplate.opsForSet().add("hashset", "a","b"));
		System.out.println(stringRedisTemplate.opsForSet().add("hashset2", "c","d"));
		
		//将成员从源集合移出放入目标集合 
		System.out.println(stringRedisTemplate.opsForSet().move("hashset", "a","hashset2"));
		System.out.println(stringRedisTemplate.opsForSet().members("hashset2"));
		//获取给定key中元素个数
		System.out.println(stringRedisTemplate.opsForSet().size("hashset2"));
		//确定一个给定的值是否存在
		System.out.println(stringRedisTemplate.opsForSet().isMember("hashset2","a"));
	}
	// *****************对存储结构为Set(排序的)类型的操作结束*******************************************//
	@Test
	public void sortSetAdd(){
		stringRedisTemplate.delete("sortset");
		//向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "a", 1));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "b", 2));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "c", 3));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "d", 3));
		
		//从集合中删除成员
		System.out.println(stringRedisTemplate.opsForZSet().remove("sortset", "c"));
		
		//返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
		System.out.println(stringRedisTemplate.opsForZSet().range("sortset",0 , -1));
	}
	
	@Test
	public void sortSetIncrby(){
		stringRedisTemplate.delete("sortset");
		//向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "a", 1));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "b", 2));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "c", 3));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "d", 4));
		System.out.println(stringRedisTemplate.opsForZSet().add("sortset", "c", 5));
		
		//权重增加给定值，如果给定的member已存在
		System.out.println(stringRedisTemplate.opsForZSet().incrementScore("sortset", "b", 2));

		//返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
		System.out.println(stringRedisTemplate.opsForZSet().range("sortset",0 , -1));
		
		//获取指定值在集合中的位置，集合排序从低到高
		System.out.println(stringRedisTemplate.opsForZSet().rank("sortset", "b"));
		//获取指定值在集合中的位置，集合排序从高到低
		System.out.println(stringRedisTemplate.opsForZSet().reverseRank("sortset", "b"));
		
		System.out.println(stringRedisTemplate.opsForZSet().size("sortset"));

	}
}
