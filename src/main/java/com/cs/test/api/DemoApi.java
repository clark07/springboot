package com.cs.test.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/11/11.
 */
@Path("/demo")
public class DemoApi {

	private final static Logger log = LoggerFactory.getLogger(DemoApi.class);
	
	@GET
	public Response hello(){
		log.info("hello");
		return Response.ok("hello").build();
	}

}
