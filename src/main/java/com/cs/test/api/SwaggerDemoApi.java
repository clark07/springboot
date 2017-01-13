package com.cs.test.api;

import com.cs.test.api.bean.SwaggerDemoBean;
import com.cs.test.response.BootReturnBean;
import com.cs.test.response.BootReturnCode;
import com.cs.test.response.ResponseBuilder;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 产品资源服务API
 */
@Component
@Path("/swaggerdemo")
@Api(value = "/swaggerdemo", description = "swagger demo")
public class SwaggerDemoApi {

	private final static AtomicInteger ai = new AtomicInteger();
	private final static Map<Integer, SwaggerDemoBean> swaggerMap = new ConcurrentHashMap<>();

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "create swaggerDemoBean", notes = "More notes about this method", response = BootReturnBean.class, httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid ID supplied"),
			@ApiResponse(code = 404, message = "SwaggerDemoBean not found")
	})
	public Response createSwaggerDemo(SwaggerDemoBean swaggerDemoBean) {
		String name = swaggerDemoBean.getName();
		if(StringUtils.isBlank(name)){
			return ResponseBuilder.build(BootReturnCode.ERROR,String.format("%s不允许为空!", name));
		}

		swaggerDemoBean.setId(ai.incrementAndGet());
		swaggerMap.put(swaggerDemoBean.getId(), swaggerDemoBean);

		return ResponseBuilder.build(BootReturnCode.SUCC,"", swaggerDemoBean);
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Find SwaggerDemo by ID", notes = "More notes about this method", response = SwaggerDemoBean.class, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid ID supplied"),
			@ApiResponse(code = 404, message = "SwaggerDemoBean not found")
	})
	public Response getSwaggerDemo(@ApiParam(value = "Resource identifier", required = false) @QueryParam("id") Integer id) {
		if(id ==null){
			return ResponseBuilder.build(BootReturnCode.SUCC);
		}else {
			SwaggerDemoBean swaggerDemoBean = swaggerMap.get(id);
			return ResponseBuilder.build(BootReturnCode.SUCC, "", swaggerDemoBean);
		}
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_HTML})
	@ApiOperation(value = "update swaggerDemoBean", notes = "More notes about this method", response = String.class, httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "SwaggerDemoBean not found")
	})
	public Response updateSwaggerDemo(SwaggerDemoBean swaggerDemoBean) {
		Integer id = swaggerDemoBean.getId();
		if(swaggerMap.containsKey(id)){
			swaggerMap.put(id, swaggerDemoBean);
		}
		return ResponseBuilder.build(BootReturnCode.SUCC);
	}

	@DELETE
	@Produces({MediaType.TEXT_HTML})
	@ApiOperation(value = "remove SwaggerDemo by ID", notes = "More notes about this method", response = String.class, httpMethod = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "SwaggerDemoBean not found")
	})
	public Response removeSwaggerDemoById(@ApiParam(value = "ID values must", required = true)
									  @QueryParam("id") Integer id) {
		if(id != null){
			swaggerMap.remove(id);
		}
		return ResponseBuilder.build(BootReturnCode.SUCC);
	}
}
