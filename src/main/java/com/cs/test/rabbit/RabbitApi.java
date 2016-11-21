package com.cs.test.rabbit;

import com.cs.test.rabbit.sender.DemoSender;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/11/11.
 */
@Path("/rabbit")
public class RabbitApi {

	@Autowired
	private DemoSender demoSender;

	@GET
	@Path("/send")
	public Response sendMessage(@QueryParam("msg") String msg){
		demoSender.sendMessage2DemoQueue(msg);
		return Response.ok("succ").build();
	}

}
