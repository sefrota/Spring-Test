package com.braincode.soft.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.braincode.soft.dao.Offer;
import com.braincode.soft.dao.User;
import com.braincode.soft.dao.UsersDao;
import com.braincode.soft.service.UsersService;

@Controller
public class LoginController {
	
	private UsersService usersService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/login")
	public String showLogin(){
		return "login";
	}
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model){
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String doCreate(@Valid User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "createaccount";
		}
		user.setEnabled(true);
		user.setAuthority("user");
		usersService.create(user);
		
		return "accountcreated";
	}
}
