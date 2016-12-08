package com.cs.test.rabbit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

/**
 *
 */
//@EnableRabbit
//@Configuration
//@ImportResource(locations = {"classpath:spring/application-rabbit.xml"})
public class RabbitConfig {

/*
	@Value("${spring.rabbitmq.host}")
	private String rabbitHost;
	@Value("${spring.rabbitmq.username}")
	private String rabbitUserName;
	@Value("${spring.rabbitmq.password}")
	private String rabbitPassword;
	@Value("${spring.rabbitmq.requested-heartbeat}")
	private int rabbitRequestedHeartbeat;*/
/*	@Value("${spring.rabbitmq.connection-timeout}")
	private int rabbitConnectionTimeout;
	@Value("${spring.rabbitmq.cache.channel.checkout-timeout}")
	private int rabbitChannelCheckoutTimeout;*/
/*
	private String springBootDemoQueue= "spring_boot_demo_queue";

	@Autowired
	private MessageProcessor demoMessageHandlerProcessor;*/

	/*@Bean
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
	}*/

	@Bean
	public Jackson2JsonMessageConverter jsonConverter(){
		Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		jackson2JsonMessageConverter.setJsonObjectMapper(objectMapper);
		return jackson2JsonMessageConverter;
	}
/*	@Bean
	public SimpleMessageConverter simpleMessageConverter(){
		return new SimpleMessageConverter();
	}*/

/*	@Bean
	public RabbitTemplate springBootDemoQueueSender() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory());
		rabbitTemplate.setQueue(springBootDemoQueue);
		rabbitTemplate.setRoutingKey(springBootDemoQueue);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

		return rabbitTemplate;
	}*/
/*
	@Bean("springBootDemoQueueListenerHandler")
	public MessageListenerHandler springBootDemoQueueListenerHandler(){
		MessageListenerHandler messageListenerHandler = new MessageListenerHandler(springBootDemoQueue, rabbitConnectionFactory(), demoMessageHandlerProcessor, jackson2JsonMessageConverter());
		messageListenerHandler.setConcurrentConsumers(1, 10);

		return messageListenerHandler;

	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitConnectionFactory) {
		return new RabbitAdmin(rabbitConnectionFactory);
	}


	private final static String demo_q1 = "demo_q1";
	private final static String demo_q2 = "demo_q2";
	private final static String demo_q3 = "demo_q3";
	private final static String demo_q4 = "demo_q4";
	@Bean
	public Queue demo_q1(){
		return new Queue(demo_q1,true, false, false, queueHaArgs());
	}
	@Bean
	public Queue demo_q2(){
		return new Queue(demo_q2,true, false, false, queueHaArgs());
	}
	@Bean
	public Queue demo_q3(){
		return new Queue(demo_q3,true, false, false, queueHaArgs());
	}
	@Bean
	public Queue demo_q4(){
		return new Queue(demo_q4,true, false, false, queueHaArgs());
	}*/

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
