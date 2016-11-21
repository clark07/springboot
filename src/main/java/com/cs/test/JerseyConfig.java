package com.cs.test;

import com.cs.test.api.DemoApi;
import com.cs.test.filter.PreMatchFilter;
import com.cs.test.filter.ResponseFilter;
import com.cs.test.rabbit.RabbitApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by admin on 2016/1/11.
 */
public class JerseyConfig extends ResourceConfig{

	private final static Logger log = LoggerFactory.getLogger(JerseyConfig.class);


	public JerseyConfig(){
		log.info("init Jersey config...");
		register(DemoApi.class);
		register(RabbitApi.class);
		register(ResponseFilter.class);
		register(PreMatchFilter.class);
	}

}
