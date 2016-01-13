package com.braincode.soft.controllers;


import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braincode.soft.dao.Offer;
import com.braincode.soft.service.OffersService;
import com.mysql.jdbc.log.Slf4JLogger;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private OffersService offersService = new OffersService();
	
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) {
		
		List<Offer> offers = offersService.getCurrent();
		
		model.addAttribute("offers", offers);
		
		boolean hasOffer = false;
		
		if(principal != null){
			hasOffer = offersService.hasOffer(principal.getName());
		}
		
		model.addAttribute("hasOffer", hasOffer);
		
		System.out.println("hello from the other side");
		return "home";
	}
	
}
