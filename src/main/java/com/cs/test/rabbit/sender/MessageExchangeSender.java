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
public class MessageExchangeSender {

	private RabbitTemplate rabbitTemplate;

	public MessageExchangeSender(ConnectionFactory connectionFactory, String exchangeName, MessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setExchange(exchangeName);
		rabbitTemplate.setMessageConverter(messageConverter);

		this.rabbitTemplate = rabbitTemplate;
	}

	private final static Logger log = LoggerFactory.getLogger(MessageQueueSender.class);

	private boolean needSendLog;

	public void setNeedSendLog(boolean needSendLog) {
		this.needSendLog = needSendLog;
	}

	public void send(String routingKey, MessageBean message){
		if (needSendLog){
			log.info(String.format("send message to exchange:%s routingKey:%s content:%s", rabbitTemplate.getExchange(),rabbitTemplate.getRoutingKey(), ""));
		}
		rabbitTemplate.convertAndSend(routingKey, message);
	}
}
