package com.cs.test;

import com.cs.test.api.DemoApi;
import com.cs.test.api.JpaApi;
import com.cs.test.api.RabbitApi;
import com.cs.test.api.RedisApi;
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
		register(RedisApi.class);
		register(JpaApi.class);
		//register(ResponseFilter.class);
		//register(PreMatchFilter.class);
	}

}
