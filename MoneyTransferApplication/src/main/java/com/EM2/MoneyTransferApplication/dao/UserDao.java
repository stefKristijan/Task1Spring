package com.EM2.MoneyTransferApplication.dao;

import java.util.List;

import com.EM2.MoneyTransferApplication.model.User;

public interface UserDao {
	void insertUser(String username, String password, int age, String role);
	void deleteCustomer(int customerId);
	List<User> getAllCustomers();
	User getUserByUsername(String username);
	boolean checkIfUserIsAdmin(int userId);
	boolean checkIfUserExists(String username);
}
