package com.cs.test.rabbit.sender;

import com.cs.test.rabbit.RabbitTestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/11/11.
 */

@Service
public class DemoSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitTemplate springBootDemoQueueSender;

	
	private final static Logger log = LoggerFactory.getLogger(DemoSender.class);
		
	public void sendMessage2DemoQueue(String msg){
		for (int i = 0; i < 10; i++) {
			Object content = i + msg;
			//rabbitTemplate.convertAndSend("spring_boot_demo_queue", content);
			if(i%3==0){
				content = Arrays.asList(content).stream().collect(Collectors.toList());
			}else if(i%3==1){
				content = new RabbitTestBean(content.toString());
			}
			springBootDemoQueueSender.convertAndSend(content);
			log.info("send-->"+content);
		}
	}

}
