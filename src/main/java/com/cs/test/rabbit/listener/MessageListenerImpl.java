package com.cs.test.rabbit.listener;

import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

public class MessageListenerImpl implements ChannelAwareMessageListener {

	private final static Logger logger = Logger.getLogger(MessageListenerImpl.class);
	
	/**
	 * 消息格式转换出错,直接丢掉消息
	 * 消息处理出错 basicnack
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			Object scMessage= messageConverter.fromMessage(message);
			try {
				try {
					//logger.info(String.format("begin process [message:%s] ", new String(message.getBody())));
				} catch (Exception ignore) {
				}

				messageProcessor.process(scMessage);
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

				try {
					//logger.info(String.format("end process [message:%s] ", new String(message.getBody())));
				} catch (Exception ignore) {
				}
			} catch (Exception e) {
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
				logger.error(String.format("process [message:%s] failed, basicNack!", new String(message.getBody())), e);
			}
		} catch (Exception e) {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
			logger.error(String.format("process message failed, error message:%s!", new String(message.getBody())), e);
		}
		
	}
	
	private MessageProcessor messageProcessor;
	
	private MessageConverter messageConverter;

	public void setMessageProcessor(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

}
