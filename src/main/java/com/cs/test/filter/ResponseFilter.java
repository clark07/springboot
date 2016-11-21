package com.cs.test.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by admin on 2016/11/11.
 */
@Priority(Priorities.USER)
public class ResponseFilter implements ContainerResponseFilter {

	private final static Logger log = LoggerFactory.getLogger(ResponseFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext,
					   ContainerResponseContext responseContext) throws IOException {
		log.info("execute ResponseFilter");

		responseContext.getHeaders().add("cs-Header", ":)-cs-(:");
	}
}
