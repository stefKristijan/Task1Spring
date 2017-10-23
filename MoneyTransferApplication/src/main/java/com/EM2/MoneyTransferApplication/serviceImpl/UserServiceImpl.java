package com.EM2.MoneyTransferApplication.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EM2.MoneyTransferApplication.dao.UserDao;
import com.EM2.MoneyTransferApplication.model.User;
import com.EM2.MoneyTransferApplication.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void insertUser(String username, String password, int age, String role) {
		logger.info("Admin is creating a new customer");
		this.userDao.insertUser(username, password, age,role);
	}

	@Override
	public void deleteUser(int userId) {
		logger.info("Admin is deleting customer: "+userId);
		this.userDao.deleteUser(userId);
	}

	@Override
	public List<User> getAllUsers() {
		logger.info("Admin is getting all the customers");
		return this.userDao.getAllUsers();
	}

	@Override
	public User getUserByUsername(String username) {
		logger.info("getting user: "+username);
		return this.userDao.getUserByUsername(username);
	}

	@Override
	public boolean checkIfUserIsAdmin(int userId) {
		logger.info("Checking if user: "+userId+" is an admin");
		return this.userDao.checkIfUserIsAdmin(userId);
	}

	@Override
	public boolean checkIfUserExists(String username) {
		logger.info("Checking if user: "+username+" exists");
		return this.userDao.checkIfUserExists(username);
	}

}
