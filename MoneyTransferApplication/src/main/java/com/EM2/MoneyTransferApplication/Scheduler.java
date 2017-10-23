package com.EM2.MoneyTransferApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.EM2.MoneyTransferApplication.service.AccountService;

@Component
public class Scheduler {
	 private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

	    @Autowired
	    AccountService accountService;
	    
	    @Scheduled(fixedRate = 60000)
	    public void reportCurrentTime() {
	        log.info("Accounts: "+this.accountService.getAllAccounts().toString());
	    }
}
