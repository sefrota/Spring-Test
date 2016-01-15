package com.braincode.soft.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.braincode.soft.dao.FormValidationGroup;
import com.braincode.soft.dao.Message;
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
	
	@RequestMapping("/denied")
	public String showDenied(){
		return "denied";
	}
	
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		
		List<User> users = usersService.getAllUsers();
		model.addAttribute("users", users);
		return "admin";
	}
	
	@RequestMapping("/loggedout")
	public String showLoggedOut(){
		return "loggedout";
	}
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model){
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String doCreate(@Validated(FormValidationGroup.class) User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		user.setEnabled(true);
		user.setAuthority("ROLE_USER");
		
		if(usersService.exists(user.getUsername())){
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		try {
			usersService.create(user);
		} catch (DuplicateKeyException ex) {
			// TODO: handle exception
			System.out.println("Caught duplicate username.");
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		return "accountcreated";
	}
	
	@RequestMapping(value="/getmessages", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getMessages(Principal principal){
		List<Message> messages= null;
		
		if(principal == null){
			messages = new ArrayList<Message>();
		}else{
			String username = principal.getName();
			messages = usersService.getMessages(username);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("messages", messages);
		data.put("number", messages.size());
		
		return data;
		
	}
}
