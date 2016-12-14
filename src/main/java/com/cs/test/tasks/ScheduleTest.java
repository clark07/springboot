package com.cs.test.tasks;

import com.cs.test.mail.MailService;
import com.cs.test.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private MailService mailService;
	@Autowired
	private BookService bookService;

	@Scheduled(fixedRate = 60000)
	public void testFixRate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(String.format("spring boot heartbeat:%s-->%s", sdf.format(new Date()), ai1.incrementAndGet()));
	}


	@Scheduled(cron = "30 27 * * * ?")
	public void test(){
		//mailService.sendTemplateMail();
		//Chapter chapter = bookService.getChapter(176736);
		//mailService.sendChapterNotify(chapter);
	}
}
