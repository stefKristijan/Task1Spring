package com.EM2.MoneyTransferApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.EM2.MoneyTransferApplication.model.User;
import com.EM2.MoneyTransferApplication.service.AccountService;
import com.EM2.MoneyTransferApplication.service.UserService;

@Controller
public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value="admin/users")
	public String users(Model model) {
		model.addAttribute("users",this.userService.getAllUsers());
		return"admin/users";
	}
	
	@RequestMapping(value="/admin/users", method=RequestMethod.DELETE)
	public void deleteUser(@RequestParam(value="id") int id, Model model) {
		logger.info("Trying to delete user: "+id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User admin = this.userService.getUserByUsername(authentication.getName());
		if(admin.getId()!=id) {
			this.userService.deleteUser(id);
		}
		model.addAttribute("users",this.userService.getAllUsers());
	}
	
	@RequestMapping(value="/admin/accounts", method=RequestMethod.GET)
	public String accountsAll(Model model) {
		model.addAttribute("accounts",this.accountService.getAllAccounts());
		return"admin/accounts";
	}
}
