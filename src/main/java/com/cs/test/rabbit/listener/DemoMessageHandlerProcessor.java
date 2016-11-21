package com.cs.test.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/11/17.
 */

@Component
public class DemoMessageHandlerProcessor implements MessageProcessor {

	
	private final static Logger log = LoggerFactory.getLogger(DemoMessageHandlerProcessor.class);
		
	@Override
	public void process(Object message) {
		log.info("receive-->"+message);
	}
}
