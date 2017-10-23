package com.EM2.MoneyTransferApplication.service;

import java.util.List;

import com.EM2.MoneyTransferApplication.model.User;

public interface UserService {
	
	void insertUser(String username, String password, int age, String role);
	void deleteUser(int userId);
	List<User> getAllUsers();
	User getUserByUsername(String username);
	boolean checkIfUserIsAdmin(int userId);
	boolean checkIfUserExists(String username);
}
