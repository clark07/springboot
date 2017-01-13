package com.cs.test.response;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

/**
 * Created by admin on 2016/12/26.
 */
public class ResponseBuilder {

	public static Response build(BootReturnCode returnCode, String msg, Object obj){
		if(StringUtils.isBlank(msg)){
			msg = returnCode.getDefaultMsg();
		}

		return Response.ok(new BootReturnBean(returnCode.getValue(), msg, obj)).build();
	}

	public static Response build(BootReturnCode returnCode, String msg){
		return build(returnCode, msg, null);
	}
	public static Response build(BootReturnCode returnCode){
		return build(returnCode, "", null);
	}

}