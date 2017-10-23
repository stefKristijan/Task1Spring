package com.EM2.MoneyTransferApplication.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EM2.MoneyTransferApplication.dao.AccountDao;
import com.EM2.MoneyTransferApplication.model.Account;
import com.EM2.MoneyTransferApplication.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService{
	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public void createAccount(int customerId) {
		logger.info("Customer "+customerId+" is creating a new account");
		this.accountDao.createAccount(customerId);
	}

	@Override
	public void depositMoney(int accountId, double moneyAmount) {
		logger.info("Account "+accountId+" is depositing money");
		this.accountDao.depositMoney( accountId, moneyAmount);
	}

	@Override
	public void transferMoney(int destinationAccId, double moneyAmount, Account sourceAccount) {
		logger.info("Account "+sourceAccount+" is transfering money to: "+destinationAccId);
		this.accountDao.transferMoney(destinationAccId, moneyAmount,sourceAccount);
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Admin is getting data about all accounts");
		return this.accountDao.getAllAccounts();
	}

	@Override
	public List<Account> getAccountsByCustomerId(int customerId) {
		logger.info("Customer: "+customerId +" is getting data about his accounts");
		return this.accountDao.getAccountsByCustomerId(customerId);
	}

	@Override
	public Account getAccountById(int accountId) {
		logger.info("getting data about account: "+accountId);
		return this.accountDao.getAccountById(accountId);
	}

	@Override
	public void deleteAccount(int accId) {
		logger.info("deleting account with id:"+accId);
		this.accountDao.deleteAccount(accId);
		
	}

}
