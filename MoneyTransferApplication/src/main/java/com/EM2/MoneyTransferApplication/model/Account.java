package com.EM2.MoneyTransferApplication.model;

public class Account {

	private int accId;
	private double balance;
	private int userId;
	
	public Account() {}
	
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accId != other.accId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [accId=" + accId + ", balance=" + balance + ", userId=" + userId + "]";
	}
	public Account(int accId, double balance, int userId) {
		super();
		this.accId = accId;
		this.balance = balance;
		this.userId = userId;
	}
	
	
	
	
	
}
