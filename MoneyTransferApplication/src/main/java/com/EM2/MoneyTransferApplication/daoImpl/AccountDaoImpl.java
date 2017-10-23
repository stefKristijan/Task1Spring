package com.EM2.MoneyTransferApplication.daoImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.EM2.MoneyTransferApplication.dao.AccountDao;
import com.EM2.MoneyTransferApplication.model.Account;
import com.EM2.MoneyTransferApplication.rowmap.AccountRowMapper;

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
		try {
			getJdbcTemplate().update(createAccQuery, new Object[] {
					customerId
				});
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError inserting new account");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while inserting new account");
			e.printStackTrace();
		}
	}

	@Override
	public void depositMoney(int accountId, double moneyAmount) {
		logger.info("Depositing money to account: "+accountId);
		String depositMoneyQuery= "UPDATE account SET balance=balance+? WHERE account_id=?";
		try {
			getJdbcTemplate().update(depositMoneyQuery, new Object[] {
					moneyAmount,accountId
			});
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while depositing money");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while depositing money");
			e.printStackTrace();
		}
	}

	@Override
	public void transferMoney(int destinationAccId, double moneyAmount, Account sourceAccount)  {
		if(moneyAmount<=sourceAccount.getBalance()) {
			logger.info("Transfering money from account:"+sourceAccount+" to account:"+destinationAccId);
			String reduceBalanceQuery="UPDATE account SET balance=balance-? WHERE user_id=? AND account_id=?";
			try {
				getJdbcTemplate().update(reduceBalanceQuery,new Object[] {
					moneyAmount,sourceAccount.getUserId(),sourceAccount.getAccId()	
				});
			} catch (CannotGetJdbcConnectionException e) {
				logger.error("JDBCConnectionError while transfering money");
				e.printStackTrace();
			}catch(DataAccessException e) {
				logger.error("DataAccessEx while traansfering money");
				e.printStackTrace();
			}
			depositMoney(destinationAccId, moneyAmount);
		}else {
			throw new RuntimeException("You can't transfer more money than you have");
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts from database");
		String allAccountsQuery="SELECT * FROM account";
		List<Map<String, Object>> rows=null;
		try {
			rows = getJdbcTemplate().queryForList(allAccountsQuery);
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while getting all accounts");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while getting all accounts");
			e.printStackTrace();
		}
		return createAccountList(rows);
	}

	@Override
	public List<Account> getAccountsByCustomerId(int customerId) {
		logger.info("Getting all accounts of customer: "+customerId+" from database");
		String customerAccountsQuery="SELECT * FROM account WHERE user_id="+customerId;
		List<Map<String, Object>> rows=null;
		try {
			rows=getJdbcTemplate().queryForList(customerAccountsQuery);
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while getting customer accounts");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while getting customer accounts");
			e.printStackTrace();
		}
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
		Account account=null;
		try {
			account=getJdbcTemplate().queryForObject(getAccountQuery, new Object[] {accountId}, new AccountRowMapper());
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while getting account");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while getting account");
			e.printStackTrace();
		}
		return account;
		
	}

	@Override
	public void deleteAccount(int accId,int customerId) {
		logger.info("Trying to delete account: "+accId);
		String checkRequest="SELECT COUNT(*) FROM account WHERE account_id=? AND user_id=?";//check if account is customers
		try {
			int count =getJdbcTemplate().queryForObject(checkRequest, new Object[] {accId,customerId}, Integer.class);
			if(count==0) {
				throw new DataIntegrityViolationException("You can delete only your account!");
			}
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while deleting account");
			e.printStackTrace();
		}catch(DataIntegrityViolationException e) {
			logger.error("DataIntegrityException while deleting account");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while deleting account");
			e.printStackTrace();
		}
		String deleteQuery = "DELETE FROM account WHERE account_id=? AND user_id=?";
		try {
			logger.info("Deleting account: "+accId+ "from database");
			getJdbcTemplate().update(deleteQuery,new Object[]{accId,customerId});
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while deleting account");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while deleting account");
			e.printStackTrace();
		}
	}

}
