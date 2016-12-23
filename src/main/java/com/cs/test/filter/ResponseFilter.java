package com.cs.test.filter;

import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/11.
 */
@Priority(Priorities.USER)
public class ResponseFilter implements ContainerResponseFilter {

	private final static Logger log = LoggerFactory.getLogger(ResponseFilter.class);

	@Value("${swagger.log}")
	private boolean debug;

	@Override
	public void filter(ContainerRequestContext requestContext,
					   ContainerResponseContext responseContext) throws IOException {
		log.info("execute ResponseFilter");

		List<String> requestTimes = requestContext.getHeaders().get("requestTime");
		if(debug && CollectionUtils.isNotEmpty(requestTimes)){
			long st = NumberUtils.toLong(requestTimes.get(0));
			if(st>0){
				log.info(String.format("%s-->%s cost:%sms",  requestContext.getMethod(), requestContext.getUriInfo().getPath(),System.currentTimeMillis()-st));
			}
		}

		if(requestContext.getUriInfo().getPath().equals("swagger.json")){
			Object entity = responseContext.getEntity();
			if(entity != null && entity instanceof Swagger){
				Swagger swagger = (Swagger) entity;

				Map<String, Path> paths = swagger.getPaths();
				Map<String, Path> fixedMap = new HashMap<>();

				final String basePath = "/rest";

				paths.forEach((k, v)->fixedMap.put(String.format("%s%s", k.startsWith(basePath)?"":basePath, k), v));

				swagger.setPaths(fixedMap);
			}
		}
		responseContext.getHeaders().add("cs-Header", ":)-cs-(:");
	}
}
