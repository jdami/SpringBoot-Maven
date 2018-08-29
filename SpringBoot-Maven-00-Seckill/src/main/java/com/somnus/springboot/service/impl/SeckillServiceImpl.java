package com.somnus.springboot.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.somnus.springboot.commons.base.aspect.ServiceLimit;
import com.somnus.springboot.commons.base.aspect.Servicelock;
import com.somnus.springboot.commons.base.entity.Seckill;
import com.somnus.springboot.commons.base.entity.SuccessKilled;
import com.somnus.springboot.commons.base.result.LogicResult;
import com.somnus.springboot.commons.base.result.ResultEnum;
import com.somnus.springboot.mapper.SeckillMapper;
import com.somnus.springboot.mapper.SuccessKilledMapper;
import com.somnus.springboot.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
    /**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
	private Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁  
	
	@Autowired
	private SeckillMapper seckillMapper;
	
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillMapper.selectAll();
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillMapper.selectByPrimaryKey(seckillId);
	}

	@Override
	public Long getSeckillCount(long seckillId) {
		return successKilledMapper.getSeckillCount(seckillId);
	}
	
	@Override
	@Transactional
	public void deleteSeckill(long seckillId) {
		successKilledMapper.deleteSuccessKilledBySeckillId(seckillId);
		Seckill seckill = new Seckill();
		seckill.setSeckillId(seckillId);
		seckill.setNumber(10);
		seckillMapper.updateByPrimaryKeySelective(seckill);
	}
	
	@Override
	@ServiceLimit
	@Transactional
	public LogicResult startSeckil(long seckillId,long userId) {
		//校验库存
		Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
		Assert.notNull(seckill,"该商品不存在");
		if(seckill.getNumber() > 0){
			//扣库存
			seckill.setNumber(seckill.getNumber()-1);
			seckillMapper.updateByPrimaryKeySelective(seckill);
			//创建订单
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((byte) 0);
			killed.setCreateTime(new Timestamp(new Date().getTime()));
			successKilledMapper.insert(killed);
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
		}else{
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
		}
	}
	
	@Override
	@Transactional
	public LogicResult startSeckilLock(long seckillId, long userId) {
		 try {
			lock.lock();
			//这里、不清楚为啥、总是会被超卖101、难道锁不起作用、lock是同一个对象
			//来自热心网友 zoain 的细心测试思考、然后自己总结了一下
			//事物未提交之前，锁已经释放(事物提交是在整个方法执行完)，导致另一个事物读取到了这个事物未提交的数据，也就是传说中的脏读。建议锁上移
			//给自己留个坑思考：为什么分布式锁(zk和redis)没有问题？难道不是事物的锅
			Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
			Assert.notNull(seckill,"该商品不存在");
			if(seckill.getNumber() > 0){
				seckill.setNumber(seckill.getNumber()-1);
				seckillMapper.updateByPrimaryKeySelective(seckill);
				SuccessKilled killed = new SuccessKilled();
				killed.setSeckillId(seckillId);
				killed.setUserId(userId);
				killed.setState((byte) 0);
				killed.setCreateTime(new Timestamp(new Date().getTime()));
				successKilledMapper.insert(killed);
			}else{
				return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
	}
	
	@Override
	@Servicelock
	@Transactional
	public LogicResult startSeckilAopLock(long seckillId, long userId) {
		//来自码云码友<马丁的早晨>的建议 使用AOP + 锁实现
		Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
		Assert.notNull(seckill,"该商品不存在");
		if(seckill.getNumber() > 0){
			seckill.setNumber(seckill.getNumber()-1);
			seckillMapper.updateByPrimaryKeySelective(seckill);
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((byte) 0);
			killed.setCreateTime(new Timestamp(new Date().getTime()));
			successKilledMapper.insert(killed);
		}else{
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
		}
		return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
	}
	
	@Override
	/*@ServiceLimit*/
	@Transactional
	public LogicResult startSeckilDBPCC_ONE(long seckillId, long userId) {
		//单用户抢购一件商品或者多件都没有问题
		Seckill seckill = seckillMapper.selectByPrimaryKeyForUpdate(seckillId);
		Assert.notNull(seckill,"该商品不存在");
		if(seckill.getNumber() > 0){
			seckill.setNumber(seckill.getNumber()-1);
			seckillMapper.updateByPrimaryKeySelective(seckill);
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((byte) 0);
			killed.setCreateTime(new Timestamp(new Date().getTime()));
			successKilledMapper.insert(killed);
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
		}else{
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
		}
	}
	
	/**
     * SHOW STATUS LIKE 'innodb_row_lock%'; 
     * 如果发现锁争用比较严重，如InnoDB_row_lock_waits和InnoDB_row_lock_time_avg的值比较高
     */
	@Override
	@Transactional
	public LogicResult startSeckilDBPCC_TWO(long seckillId, long userId) {
		//单用户抢购一件商品没有问题、但是抢购多件商品不建议这种写法
		int count = seckillMapper.updateSeckillByPessimistic(seckillId);
		if(count>0){
			SuccessKilled killed = new SuccessKilled();
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((byte)0);
			killed.setCreateTime(new Timestamp(new Date().getTime()));
			successKilledMapper.insert(killed);
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
		}else{
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
		}
	}
	
	@Override
	@Transactional
	public LogicResult startSeckilDBOCC(long seckillId, long userId, long number) {
		Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
		//if(kill.getNumber()>0){
		if(seckill.getNumber() >= number){//剩余的数量应该要大于等于秒杀的数量
			//乐观锁
			int count = seckillMapper.updateSeckillByOptimistic(number, seckillId, seckill.getVersion());
			if(count>0){
				SuccessKilled killed = new SuccessKilled();
				killed.setSeckillId(seckillId);
				killed.setUserId(userId);
				killed.setState((byte)0);
				killed.setCreateTime(new Timestamp(new Date().getTime()));
				successKilledMapper.insert(killed);
				return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_SUCCESS);
			}else{
				return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
			}
		}else{
			return LogicResult.builder().build().complete(ResultEnum.SEC_KILL_END);
		}
	}

}
