package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/11/30.
 */
@JerseyResource
@Path("/redis")
//@Api(value = "redis", description = "REDIS操作相关API", basePath = "/rest")
public class RedisApi {

	@Autowired
	private RedisService redisService;

	@GET
	@Path("/get")
	public Response getKey(@QueryParam("key") String key){
		String value =  redisService.get(key);

		return Response.ok(value).build();
	}


	@GET
	@Path("/set")
	public Response setKey(@QueryParam("key") String key, @QueryParam("value") String value, @QueryParam("ttl") Long ttl){
		if(ttl != null){
			redisService.setExpire(key, value, ttl);
		}else{
			redisService.set(key, value);
		}
		return Response.ok("succ").build();
	}

	@GET
	@Path("/list/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListKey(@QueryParam("key") String key){
		return Response.ok(redisService.getList(key)).build();
	}
	@GET
	@Path("/list/add")
	public Response setListAdd(@QueryParam("key") String key, @QueryParam("value") String value){
		redisService.setListAdd(key, value);

		return Response.ok("succ").build();
	}
}
