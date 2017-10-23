package com.EM2.MoneyTransferApplication.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.EM2.MoneyTransferApplication.service.AccountService;
import com.EM2.MoneyTransferApplication.service.UserService;

@Controller
public class RegistrationController {
	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerUser() {
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView createCustomer(@RequestParam("username") String username, @RequestParam("password") String password,
										@RequestParam("age") int age, @RequestParam("role") String role, ModelMap modelMap) {
		
		ModelAndView modelAndView=new ModelAndView();
		modelMap = dataValidation(username,password,modelMap);
		if(modelMap.isEmpty()) {
			if(this.userService.checkIfUserExists(username)) {
				modelAndView.addObject("message","User with this username already exists");
			}else {
				this.userService.insertUser(username, password, age,role);
				modelAndView.addObject("message","User has been successfully created, you can now log in");
				modelAndView.setViewName("/register");		
			}
		}
		return modelAndView;
	}

	private ModelMap dataValidation(String username, String password, ModelMap modelMap) {
		if(username.equals("")||username==null) {
			modelMap.addAttribute("usernameError","*Username can't be empty!");
		}else if(username.contains(" ")) {
			modelMap.addAttribute("usernameError","*Username can't contain a white space");
		}else if(username.length()<=5) {
			modelMap.addAttribute("usernameError","*Username must have more than 5 characters");
		}
		if(password.equals("")||password==null) {
			modelMap.addAttribute("passwordError","*Password can't be empty!");
		}else if(password.length()<=5) {
			modelMap.addAttribute("passwordError","*Password must have more than 5 characters");
		}else if(password.contains(" ")) {
			modelMap.addAttribute("passwordError","*Password can't containt a white space");
		}
		return modelMap;
	}
}
