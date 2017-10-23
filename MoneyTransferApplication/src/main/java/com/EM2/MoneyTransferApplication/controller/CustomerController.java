package com.EM2.MoneyTransferApplication.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Objects;

import javax.validation.Valid;

import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.EM2.MoneyTransferApplication.model.Account;
import com.EM2.MoneyTransferApplication.model.User;
import com.EM2.MoneyTransferApplication.service.AccountService;
import com.EM2.MoneyTransferApplication.service.UserService;

@Controller
public class CustomerController {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	
	@RequestMapping(value="/customer-accounts", method=RequestMethod.GET)
	public ModelAndView accounts(ModelMap model) {
		User customer=getUserFromDB();
		return setCustomerModelAndView(model, customer);
	}
	
	@RequestMapping(value="/customer-accounts", method=RequestMethod.POST)
	public ModelAndView createAccount(ModelMap model) {
		User customer = getUserFromDB();
		this.accountService.createAccount(customer.getId());
		return setCustomerModelAndView(model,customer);
	}
	
	@RequestMapping(value="/customer-accounts", method=RequestMethod.DELETE)
	public ModelAndView deleteAccount(ModelMap model, @RequestParam(value="id") int accId) {
		User customer = getUserFromDB();
		this.accountService.deleteAccount(accId,customer.getId());
		return setCustomerModelAndView(model,customer);
	}
	
	@RequestMapping(value="/customer-accounts", method=RequestMethod.PUT)
	public ModelAndView depositMoney(ModelMap model, @RequestParam("amount") double moneyAmount, 
													@RequestParam(value="id") int accId) {
		if(Objects.isNull(moneyAmount)) {
			throw new NullPointerException("Amount can't be null");
		}
		User customer = getUserFromDB();
		if(moneyAmount!=0) {
			this.accountService.depositMoney(accId, moneyAmount);
		}
		return setCustomerModelAndView(model,customer);
	}
	
	
	@RequestMapping(value="/transfer", method=RequestMethod.GET)
	public ModelAndView getTransferMoney(Model model, @RequestParam(value="id", required=true) int sourceAccId) {
		ModelAndView modelAndView = new ModelAndView("transfer");
		modelAndView.addObject("sourceAccId",sourceAccId);
		model.addAttribute("allAccounts",this.accountService.getAllAccounts());
		return modelAndView;
	}
	
	@RequestMapping(value="/transfer", method=RequestMethod.PUT)
	public ModelAndView transferMoney(Model model, @RequestParam(value="id", required=true) int sourceAccId,
										@RequestParam(value="amount") double amount,
										@RequestParam(value="destAccId", required=true) int destinationAccId) {
		if(Objects.isNull(amount)) {
			throw new NullPointerException("Amount can't be null");
		}
		ModelAndView modelAndView = new ModelAndView("transfer");
		if(sourceAccId!=destinationAccId) {
			try {
				this.accountService.transferMoney(destinationAccId, amount, this.accountService.getAccountById(sourceAccId));
				model.addAttribute("transferMessage","Money transfer was successful");
			} catch (RuntimeException e) {
				model.addAttribute("transferMessage",e.getMessage());
			}
		}
		else {
			model.addAttribute("transferMessage","There's no point sending money to the same account!");
		}
		modelAndView.addObject("sourceAccId",sourceAccId);
		model.addAttribute("allAccounts",this.accountService.getAllAccounts());
		return modelAndView;
	}

	private ModelAndView setCustomerModelAndView(ModelMap model,User customer) {
		ModelAndView modelAndView=new ModelAndView("customer-accounts");
		model.addAttribute("allAccounts",this.accountService.getAllAccounts());
		model.addAttribute("accounts",this.accountService.getAccountsByCustomerId(customer.getId()));
		return modelAndView;
	}
	
	private User getUserFromDB() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User customer = this.userService.getUserByUsername(authentication.getName());
		return customer;
	}
	
}
