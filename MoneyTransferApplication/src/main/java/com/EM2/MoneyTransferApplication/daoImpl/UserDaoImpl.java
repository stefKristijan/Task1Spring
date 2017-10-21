package com.EM2.MoneyTransferApplication.daoImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
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
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.EM2.MoneyTransferApplication.dao.UserDao;
import com.EM2.MoneyTransferApplication.model.User;


@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao{

	private final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	public void insertUser(String username, String password, int age, String role) {
		logger.info("Inserting user: "+username+ "into database");
		String insertQuery = "INSERT INTO user (username,password,age,role) VALUES (?,?,?,?)";
		getJdbcTemplate().update(insertQuery, new Object[] {
			username,bCryptPasswordEncoder.encode(password),age,role
		});
	}

	@Override
	public void deleteCustomer(int customerId) {
		logger.info("Deleting user: "+customerId+ "from database");
		String deleteQuery = "DELETE FROM user WHERE id=?";
		getJdbcTemplate().update(deleteQuery,customerId);
	}

	@Override
	public List<User> getAllCustomers() {
		logger.info("Getting all customers from database");
		String allCustomersQuery="SELECT * FROM user WHERE role='customer'";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(allCustomersQuery);
		
		List<User> users = new ArrayList<User>();
		for(Map<String, Object> row: rows) {
			long id = (long) row.get("user_id");
			String username = (String) row.get("username");
			String password = (String)row.get("password");
			int age = (int) row.get("age");
			Date dateCreated = (Date) row.get("creation_time");
			String role = (String) row.get("role");
			User user = new User(id, username, password, age, dateCreated, role);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserByUsername(String username) {
		logger.info("Getting user with username: "+username);
		String getUserQuery = "SELECT * FROM user WHERE username=?";
		return getJdbcTemplate().queryForObject(getUserQuery, new Object[] {username}, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet row, int rowNumber) throws SQLException {
				long id = (long) row.getInt("user_id");
				String username = (String) row.getString("username");
				String password = (String)row.getString("password");
				int age = (int) row.getInt("age");
				Date dateCreated = (Date) row.getDate("creation_time");
				String role = (String) row.getString("role");
				User user = new User(id, username, password, age, dateCreated, role);
				logger.info("Got user : "+user);
				return user;
			}
		});
	}
	

	@Override
	public boolean checkIfUserIsAdmin(int userId) {
		logger.info("Checking if user with id:"+userId+ " is an admin");
		String checkUserQuery = "SELECT role FROM user WHERE user_id=?;";
		String role = getJdbcTemplate().queryForObject(checkUserQuery, new Object[] {userId}, String.class);
		if(role=="admin") {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIfUserExists(String username) {
		logger.info("Checking if user: "+username+" exists");
		String getUserQuery = "SELECT COUNT(*) FROM user WHERE username=?";
		int count=getJdbcTemplate().queryForObject(getUserQuery, new Object[] {username}, Integer.class);
		if(count==0)
		return false;
		return true;
	}

}