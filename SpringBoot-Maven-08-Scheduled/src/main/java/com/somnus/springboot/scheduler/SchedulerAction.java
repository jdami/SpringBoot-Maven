package com.somnus.springboot.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerAction {
	
    private transient Logger log = LoggerFactory.getLogger(this.getClass());
    
    /*@Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行*/
    /*@Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行*/
    /*@Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每10秒执行一次*/
    @Scheduled(initialDelay=1000, fixedRate=10000)
	public void execute(){
    	log.info("现在时间：" + new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
	}
	
}
