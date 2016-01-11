package com.braincode.soft.controllers;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.log.Slf4JLogger;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping("/")
	public String showHome() {
		System.out.println("hello from the other side");
		return "home";
	}
	
}
