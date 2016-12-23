package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.rabbit.bean.DemoMessageBean;
import com.cs.test.rabbit.sender.MessageExchangeSender;
import com.cs.test.rabbit.sender.MessageQueueSender;
import io.swagger.annotations.Api;
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
@JerseyResource
@Path("/rabbit")
@Api(value = "rabbitmq", description = "MQ测试用API")
public class RabbitApi {

	@Autowired
	private MessageQueueSender q1Sender;
	@Autowired
	private MessageExchangeSender e1Sender;
	@Autowired
	private MessageExchangeSender e2Sender;
	@Autowired
	private MessageExchangeSender e3Sender;


	@GET
	@Path("/e/1/send")
	public Response sendMessageE1(@QueryParam("msg") String msg, @QueryParam("key") String key){
		Map<String, String> map = new HashMap<>();
		map.put("e1Sender", msg);
		e1Sender.send(key, new DemoMessageBean(map));
		return Response.ok("succ").build();
	}
	@GET
	@Path("/e/2/send")
	public Response sendMessageE2(@QueryParam("msg") String msg){
		Map<String, String> map = new HashMap<>();
		map.put("e2Sender", msg);
		e2Sender.send("", new DemoMessageBean(map));
		return Response.ok("succ").build();
	}
	@GET
	@Path("/e/3/send")
	public Response sendMessageE3(@QueryParam("msg") String msg, @QueryParam("key") String key){
		Map<String, String> map = new HashMap<>();
		map.put("e3Sender", msg);
		e3Sender.send(key, new DemoMessageBean(map));
		return Response.ok("succ").build();
	}
	@GET
	@Path("/q/1/send")
	public Response sendMessageQ1(@QueryParam("msg") String msg){
		Map<String, String> map = new HashMap<>();
		map.put("q1Sender", msg);
		q1Sender.send(new DemoMessageBean(map));
		return Response.ok("succ").build();
	}

}
