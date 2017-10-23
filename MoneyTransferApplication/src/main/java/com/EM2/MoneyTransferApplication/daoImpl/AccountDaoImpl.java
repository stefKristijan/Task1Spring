package com.EM2.MoneyTransferApplication.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.EM2.MoneyTransferApplication.dao.AccountDao;
import com.EM2.MoneyTransferApplication.model.Account;

@Repository
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao{

	private final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	
	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	public void createAccount(int customerId) {
		logger.info("Creating a new account for customer: "+customerId);
		String createAccQuery = "INSERT INTO account(user_id) VALUES (?);";
		getJdbcTemplate().update(createAccQuery, new Object[] {
				customerId
			});
	}

	@Override
	public void depositMoney(int accountId, double moneyAmount) {
		logger.info("Depositing money to account: "+accountId);
		String depositMoneyQuery= "UPDATE account SET balance=balance+? WHERE account_id=?";
		getJdbcTemplate().update(depositMoneyQuery, new Object[] {
				moneyAmount,accountId
		});
	}

	@Override
	public void transferMoney(int destinationAccId, double moneyAmount, Account sourceAccount)  {
		if(moneyAmount<=sourceAccount.getBalance()) {
			logger.info("Transfering money from account:"+sourceAccount+" to account:"+destinationAccId);
			String reduceBalanceQuery="UPDATE account SET balance=balance-? WHERE user_id=? AND account_id=?";
			getJdbcTemplate().update(reduceBalanceQuery,new Object[] {
				moneyAmount,sourceAccount.getUserId(),sourceAccount.getAccId()	
			});
			depositMoney(destinationAccId, moneyAmount);
		}else {
			throw new RuntimeException("You can't transfer more money than you have");
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts from database");
		String allAccountsQuery="SELECT * FROM account";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(allAccountsQuery);
		return createAccountList(rows);
	}

	@Override
	public List<Account> getAccountsByCustomerId(int customerId) {
		logger.info("Getting all accounts of customer: "+customerId+" from database");
		String customerAccountsQuery="SELECT * FROM account WHERE user_id="+customerId;
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(customerAccountsQuery);
		return createAccountList(rows);
	}

	private List<Account> createAccountList(List<Map<String, Object>> rows) {
		List<Account> accounts = new ArrayList<Account>();
		for(Map<String, Object> row: rows) {
			int userId = (int) row.get("user_id");
			int accId = (int) row.get("account_id");
			double balance = (double) row.get("balance");
			
			Account account = new Account(accId, balance, userId);
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public Account getAccountById(int accountId) {
		logger.info("Getting account from account id:"+accountId);
		String getAccountQuery = "SELECT * FROM account WHERE account_id=?;";
		return getJdbcTemplate().queryForObject(getAccountQuery, new Object[] {accountId}, new RowMapper<Account>() {

			@Override
			public Account mapRow(ResultSet row, int rowNumber) throws SQLException {
				int userId = (int) row.getInt("user_id");
				int accId = (int) row.getInt("account_id");
				double balance = (double) row.getDouble("balance");
				
				Account account = new Account(accId, balance, userId);
				return account;
			}
		});
	}

	@Override
	public void deleteAccount(int accId) {
		logger.info("Deleting account: "+accId+ "from database");
		String deleteQuery = "DELETE FROM account WHERE account_id=?";
		getJdbcTemplate().update(deleteQuery,accId);
	}

}
