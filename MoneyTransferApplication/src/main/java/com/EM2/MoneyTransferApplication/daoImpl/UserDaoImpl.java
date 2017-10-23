package com.EM2.MoneyTransferApplication.daoImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.EM2.MoneyTransferApplication.dao.UserDao;
import com.EM2.MoneyTransferApplication.model.User;
import com.EM2.MoneyTransferApplication.rowmap.UserRowMapper;


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
		try {
			getJdbcTemplate().update(insertQuery, new Object[] {
				username,bCryptPasswordEncoder.encode(password),age,role
			});
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError inserting new user");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while inserting new user");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(int userId) {
		logger.info("Deleting user: "+userId+ "from database");
		if(userId!=1) {
			String deleteAccountsQuery="DELETE FROM account WHERE user_id=?";
			String deleteQuery = "DELETE FROM user WHERE user_id=?";
			try {
				getJdbcTemplate().update(deleteAccountsQuery,userId);
				getJdbcTemplate().update(deleteQuery,userId);
			} catch (CannotGetJdbcConnectionException e) {
				logger.error("JDBCConnectionError while deleting user and his accounts");
				e.printStackTrace();
			}catch(DataAccessException e) {
				logger.error("DataAccessEx while deleting user and his accounts");
				e.printStackTrace();
			}
		}else throw new DataAccessResourceFailureException("You can't delete the main account!");
	}

	@Override
	public List<User> getAllUsers() {
		logger.info("Getting all customers from database");
		String allCustomersQuery="SELECT * FROM user";
		List<Map<String, Object>> rows = null;
		
		try {
			rows= getJdbcTemplate().queryForList(allCustomersQuery);
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while getting all users");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while getting all users");
			e.printStackTrace();
		}
		
		List<User> users = new ArrayList<User>();
		for(Map<String, Object> row: rows) {
			int id =  (int) row.get("user_id");
			String username = (String) row.get("username");
			String password = (String)row.get("password");
			int age = (int) row.get("age");
			Timestamp dateCreated = (Timestamp) row.get("creation_time");
			String role = (String) row.get("role");
			User user = new User(id, username, password, age, dateCreated, role);
			users.add(user);
		}
		return users;
	}

	@Override
	public User getUserByUsername(String username) {
		String getUserQuery = "SELECT * FROM user WHERE username=?";
		User user = null;
		try {
			user = getJdbcTemplate().queryForObject(getUserQuery, new Object[] {username}, new UserRowMapper());
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while getting user by username");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while getting user by username");
			e.printStackTrace();
		}

		return user;
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
		int count=0;
		try {
			count = getJdbcTemplate().queryForObject(getUserQuery, new Object[] {username}, Integer.class);
		} catch (CannotGetJdbcConnectionException e) {
			logger.error("JDBCConnectionError while checking if user exists");
			e.printStackTrace();
		}catch(DataAccessException e) {
			logger.error("DataAccessEx while checking if user exists");
			e.printStackTrace();
		}
		if(count==0)
		return false;
		return true;
	}

}
