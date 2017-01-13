package com.cs.test.api;

import com.cs.test.annotation.JerseyResource;
import com.cs.test.response.BootReturnBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by admin on 2016/12/16.
 */
@JerseyResource
public class JerseyExceptionHandler implements ExceptionMapper<Exception> {

	private final static Logger log = LoggerFactory.getLogger(JerseyExceptionHandler.class);

	@Override
	public Response toResponse(Exception e) {

		log.info(String.format("handle rest exception"), e);
		BootReturnBean error = new BootReturnBean();
		error.setCode(-1);
		error.setMsg("处理rest请求出错");
		error.setData(e.getMessage());
		return Response.ok(error).build();
	}
}
