package com.cs.test.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/11/11.
 */
@Path("/rabbit")
public class RabbitApi {

	@Autowired
	private RabbitTemplate q1Sender;
	@Autowired
	private RabbitTemplate e1Sender;


	@GET
	@Path("/send")
	public Response sendMessage(@QueryParam("msg") String msg){
		Map<String, String> map = new HashMap<>();
		map.put("1", msg);
		q1Sender.convertAndSend(map);
		return Response.ok("succ").build();
	}

}
