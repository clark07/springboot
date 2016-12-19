package com.cs.test.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/12/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	public BootReturnCode exceptionHandler(Exception e, HttpServletResponse response){
		return new BootReturnCode(500, "处理异常", e.getMessage());
	}
}
