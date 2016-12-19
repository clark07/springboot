package com.cs.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2016/12/13.
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String index(){
		return "redirect:/index.html";
	}
	@GetMapping("/exception")
	public String exception(){

		String s = "".split("")[1];

		return "";
	}


}
