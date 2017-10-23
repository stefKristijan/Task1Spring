package com.EM2.MoneyTransferApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.EM2.MoneyTransferApplication.service.AccountService;
import com.EM2.MoneyTransferApplication.service.UserService;

@Controller
public class MainController {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value= "/", method=RequestMethod.GET)
	public ModelAndView home() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getAuthorities().toString().contains("admin")||authentication.getAuthorities().toString().contains("customer"))
			return homeRequest();
		return new ModelAndView("welcome");
	}
	
	private ModelAndView homeRequest() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelAndView = new ModelAndView("home");
		if(authentication.getAuthorities().toString().contains("admin")) {
			modelAndView.addObject("isAdmin",true);
			logger.info("User is admin");
		}
		else if(authentication.getAuthorities().toString().contains("customer")) {
			logger.info("User is customer");
			modelAndView.addObject("isAdmin",false);
		}
		
		modelAndView.addObject("username",authentication.getName());
		return modelAndView;
	}
	
		@RequestMapping(value={"/login"})
		public ModelAndView login() {
			ModelAndView modelAndView = new ModelAndView("login");
			return modelAndView;
		}
}
