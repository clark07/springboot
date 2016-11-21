/*
package com.cs.test.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;



@RabbitListener(queues = "spring_boot_demo_queue")
@Component
public class DemoListener {

	private final static Logger log = LoggerFactory.getLogger(DemoListener.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitHandler
	public void process(@Payload String foo) {
		log.info("get message from queue-->: " + foo);
	}

	@Bean(name = "spring_boot_demo_queue")
	public Queue declareQueue() {
		return new Queue("spring_boot_demo_queue", true);
	}

}
*/
