package com.cs.test.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import java.io.IOException;

/**
 * Created by admin on 2016/11/11.
 */
@PreMatching
@Priority(Priorities.USER)
public class PreMatchFilter implements ContainerRequestFilter {
	
	private final static Logger log = LoggerFactory.getLogger(PreMatchFilter.class);
		
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.info("execute PreMatchFilter");
		requestContext.getHeaders().add("requestTime", String.valueOf(System.currentTimeMillis()));
	}
}
