package com.EM2.MoneyTransferApplication.dao;

import java.util.List;

import com.EM2.MoneyTransferApplication.model.Account;

public interface AccountDao {
	void createAccount(int customerId);
	void depositMoney(int accountId, double moneyAmount);
	void transferMoney(int destinationAccId, double moneyAmount, Account sourceAccount);
	List<Account> getAllAccounts(int adminId);
	List<Account> getAccountsByCustomerId(int customerId);
	Account getAccountById(int accountId);
}
