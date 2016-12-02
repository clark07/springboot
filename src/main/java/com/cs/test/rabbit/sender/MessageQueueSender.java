package com.cs.test.rabbit.sender;

import com.cs.test.rabbit.bean.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Created by admin on 2016/11/30.
 */
public class MessageQueueSender {
	private RabbitTemplate rabbitTemplate;
	public MessageQueueSender(ConnectionFactory connectionFactory, String queueName, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setQueue(queueName);
		rabbitTemplate.setRoutingKey(queueName);
		rabbitTemplate.setMessageConverter(messageConverter);

		this.rabbitTemplate = rabbitTemplate;
	}

	private final static Logger log = LoggerFactory.getLogger(MessageQueueSender.class);

	private boolean needSendLog;

	public void setNeedSendLog(boolean needSendLog) {
		this.needSendLog = needSendLog;
	}

	public void send(MessageBean messageBean){
		if (needSendLog){
			log.info(String.format("send message to queue:%s content:%s", rabbitTemplate.getRoutingKey(), ""));
		}

		rabbitTemplate.convertAndSend(messageBean);
	}
}
