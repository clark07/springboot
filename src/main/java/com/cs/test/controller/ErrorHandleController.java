package com.cs.test.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2016/12/14.
 */
@Controller
public class ErrorHandleController implements ErrorController {
	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(value = "/error")
	public String handleError(){
		return "404";
	}
}
