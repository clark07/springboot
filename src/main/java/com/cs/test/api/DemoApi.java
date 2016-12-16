package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/11/11.
 */
@JerseyResource
@Path("/demo")
public class DemoApi {

	private final static Logger log = LoggerFactory.getLogger(DemoApi.class);

	@Autowired
	private DemoService demoService;

	@GET
	public Response hello(){
		log.info("hello");
		return Response.ok("hello").build();
	}

	@GET
	@Path("/test")
	public Response test(){

		demoService.testRequired();

		return Response.ok("succ").build();
	}


}
