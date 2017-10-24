package com.EM2.MoneyTransferApplication.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.EM2.MoneyTransferApplication.model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet row, int rowNum) throws SQLException {
		int id = (int) row.getInt("user_id");
		String username = (String) row.getString("username");
		String password = (String)row.getString("password");
		int age = (int) row.getInt("age");
		Timestamp dateCreated = (Timestamp) row.getTimestamp("creation_time");
		String role = (String) row.getString("role");
		User user = new User(id, username, password, age, dateCreated, role);
		return user;
	}

}
