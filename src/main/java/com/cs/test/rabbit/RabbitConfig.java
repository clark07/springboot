package com.cs.test.rabbit;

import com.cs.test.rabbit.listener.MessageListenerHandler;
import com.cs.test.rabbit.listener.MessageProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@EnableRabbit
@Configuration
public class RabbitConfig {


	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	@Value("${spring.rabbitmq.username}")
	private String rabbitUserName;
	@Value("${spring.rabbitmq.password}")
	private String rabbitPassword;
	@Value("${spring.rabbitmq.requested-heartbeat}")
	private int rabbitRequestedHeartbeat;
/*	@Value("${spring.rabbitmq.connection-timeout}")
	private int rabbitConnectionTimeout;
	@Value("${spring.rabbitmq.cache.channel.checkout-timeout}")
	private int rabbitChannelCheckoutTimeout;*/

	private String springBootDemoQueue= "spring_boot_demo_queue";

	@Autowired
	private MessageProcessor demoMessageHandlerProcessor;

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
		rabbitConnectionFactory.setHost(rabbitHost);
		rabbitConnectionFactory.setRequestedHeartbeat(rabbitRequestedHeartbeat);

		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
		connectionFactory.setUsername(rabbitUserName);
		connectionFactory.setPassword(rabbitPassword);
	//	connectionFactory.setConnectionTimeout(rabbitConnectionTimeout);

		//connectionFactory.setChannelCheckoutTimeout(rabbitChannelCheckoutTimeout);

		return connectionFactory;
	}

	@Bean("haArgs")
	public Map<String, Object> queueHaArgs(){
		Map<String, Object> argsMap = new HashMap<>();
		argsMap.put("x-ha-policy", "all");
		return argsMap;
	}
	@Bean
	public Map<String, Object> queueHaTtlArgs(){
		Map<String, Object> argsMap = new HashMap<>();
		argsMap.put("x-ha-policy", "all");
		argsMap.put("x-message-ttl", 60000);
		return argsMap;
	}

	@Bean
	public Queue springBootDemoQueue() {
		return new Queue(springBootDemoQueue, true, false, false, queueHaArgs());
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
		return jackson2JsonMessageConverter;
	}
	@Bean
	public SimpleMessageConverter simpleMessageConverter(){
		return new SimpleMessageConverter();
	}

	@Bean
	public RabbitTemplate springBootDemoQueueSender() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
		rabbitTemplate.setQueue(springBootDemoQueue);
		rabbitTemplate.setRoutingKey(springBootDemoQueue);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

		return rabbitTemplate;
	}

	@Bean("springBootDemoQueueListenerHandler")
	public MessageListenerHandler springBootDemoQueueListenerHandler(){
		MessageListenerHandler messageListenerHandler = new MessageListenerHandler(springBootDemoQueue, rabbitConnectionFactory(), demoMessageHandlerProcessor, jackson2JsonMessageConverter());
		messageListenerHandler.setConcurrentConsumers(1);

		return messageListenerHandler;

	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitConnectionFactory) {
		return new RabbitAdmin(rabbitConnectionFactory);
	}

/*	@Bean
	public DirectExchange e1() {
		return new DirectExchange("e1", false, true);
	}*/

/*	@Bean
	public Binding b1() {
		return BindingBuilder.bind(q1()).to(e1()).with("k1");
	}*/

/*	@Bean
	public List<Exchange> es() {
		return Arrays.<Exchange>asList(new DirectExchange("e2", false, true), new DirectExchange("e3", false, true));
	}*/

/*	@Bean
	public List<Queue> qs() {
		return Arrays.asList(new Queue("q2", false, false, true), new Queue("q3", false, false, true));
	}

	@Bean
	public List<Binding> bs() {
		return Arrays
				.asList(new Binding("q2", Binding.DestinationType.QUEUE, "e2", "k2", null), new Binding("q3", Binding.DestinationType.QUEUE, "e3", "k3", null));
	}

	@Bean
	public List<Declarable> ds() {
		return Arrays.<Declarable>asList(new DirectExchange("e4", false, true), new Queue("q4", false, false, true), new Binding("q4", Binding.DestinationType.QUEUE, "e4", "k4", null));
	}*/

}
