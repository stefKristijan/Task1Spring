package com.EM2.MoneyTransferApplication.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.EM2.MoneyTransferApplication.model.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet row, int rowNum) throws SQLException {
		int userId = (int) row.getInt("user_id");
		int accId = (int) row.getInt("account_id");
		double balance = (double) row.getDouble("balance");
		
		Account account = new Account(accId, balance, userId);
		return account;
	}

}
