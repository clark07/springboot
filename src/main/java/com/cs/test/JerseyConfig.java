package com.cs.test;

import com.cs.test.filter.PreMatchFilter;
import com.cs.test.filter.ResponseFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

/**
 * Created by admin on 2016/1/11.
 */
public class JerseyConfig extends ResourceConfig{

	private final static Logger log = LoggerFactory.getLogger(JerseyConfig.class);

	public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	public JerseyConfig(){
		log.info("init Jersey config...");

	try {
		packages("com.cs.test.api");
		packages("io.swagger.jaxrs.listing");


		//register(LoggingFilter.class);
		register(ResponseFilter.class);
		register(PreMatchFilter.class);
		}catch (Exception e){}

	}

}
