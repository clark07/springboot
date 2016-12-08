package com.cs.test.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2016/12/6.
 */
@Component
public class ScheduleTest {

	private final static Logger log = LoggerFactory.getLogger(ScheduleTest.class);

	private static AtomicInteger ai1 = new AtomicInteger();
	private static AtomicInteger ai2 = new AtomicInteger();

	@Scheduled(fixedRate = 60000)
	public void testFixRate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(String.format("spring boot heartbeat:%s-->%s", sdf.format(new Date()), ai1.incrementAndGet()));
	}


	//@Scheduled(cron = "*/2 * * * * ?")
	public void test(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(String.format("任务2:%s-->%s", sdf.format(new Date()), ai2.incrementAndGet()));
	}
}
