package com.EM2.MoneyTransferApplication.service;

import java.util.List;

import com.EM2.MoneyTransferApplication.model.Account;

public interface AccountService {
	void createAccount(int customerId);
	void depositMoney(int accountId, double moneyAmount);
	void transferMoney(int destinationAccId, double moneyAmount, Account sourceAccount);
	List<Account> getAllAccounts();
	List<Account> getAccountsByCustomerId(int customerId);
	Account getAccountById(int accountId);
	void deleteAccount(int accId, int customerId);
}
