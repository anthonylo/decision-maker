package com.decision.maker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

	@RequestMapping(value = "/")
	public @ResponseBody String printHelloWorld() { 
		return "Hello, World!";
	}
	
	@RequestMapping(value = "/{name:.+}")
	public @ResponseBody String printHelloWorld(@PathVariable String name) {
		return "Hello, " + name + "!";
	}
	
	
}
