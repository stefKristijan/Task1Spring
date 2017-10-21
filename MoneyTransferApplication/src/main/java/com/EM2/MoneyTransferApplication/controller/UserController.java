package com.EM2.MoneyTransferApplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.EM2.MoneyTransferApplication.model.User;
import com.EM2.MoneyTransferApplication.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value= {"/","/home"}, method=RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerUser() {
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView createCustomer(@RequestParam("username") String username, @RequestParam("password") String password,
										@RequestParam("age") int age, @RequestParam("role") String role) {
		
		ModelAndView modelAndView=new ModelAndView();
		if(this.userService.checkIfUserExists(username)) {
			
		}else {
			this.userService.insertUser(username, password, age,role);
			modelAndView.addObject("successMessage","User has been successfully created, you can now log in");
			modelAndView.setViewName("/home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value={"/login"})
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
}
