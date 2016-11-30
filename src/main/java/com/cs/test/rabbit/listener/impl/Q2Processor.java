package com.cs.test.rabbit.listener.impl;

import com.cs.test.rabbit.listener.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/11/17.
 */

@Component
public class Q2Processor implements MessageProcessor {

	private final static Logger log = LoggerFactory.getLogger(Q2Processor.class);
		
	@Override
	public void process(Object message) {
		log.info("Q2Processor receive-->"+message);

		try{
			Thread.sleep(100);
		}catch (Exception e){
		}

	}
}
