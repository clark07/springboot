package com.cs.test.rabbit.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by admin on 2016/11/17.
 */
public class MessageListenerHandler implements MessageHandler,InitializingBean,DisposableBean {

	private final static Logger log = LoggerFactory.getLogger(MessageListenerHandler.class);

	private SimpleMessageListenerContainer container;

	public MessageListenerHandler(String queueName, ConnectionFactory connectionFactory,
								  MessageProcessor messageProcessor, MessageConverter messageConverter) {
		this.container = new SimpleMessageListenerContainer();
		container.setQueueNames(queueName);
		container.setConnectionFactory(connectionFactory);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

		MessageListenerImpl listener = new MessageListenerImpl();
		listener.setMessageProcessor(messageProcessor);
		listener.setMessageConverter(messageConverter);
		container.setMessageListener(listener);
	}

	public void setConcurrentConsumers(int concurrentConsumers){
		container.setConcurrentConsumers(concurrentConsumers);
	}

	@Override
	public void start() {
		log.info(String.format("rabbit mq receive msg start, %s", container.getQueueNames())); container.initialize();
		this.container.initialize();
		this.container.start();
	}

	@Override
	public void stop() {
		if (container != null) {
			container.stop();
		} log.info("rabbit mq receive msg stoped");
	}

	@Override
	public void shutdown() {
		if (container != null) {
			container.shutdown();
		} log.info("rabbit mq receive msg shutdowned");
	}

	@Override
	public void destroy() throws Exception {
		this.shutdown();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.start();
	}
}
