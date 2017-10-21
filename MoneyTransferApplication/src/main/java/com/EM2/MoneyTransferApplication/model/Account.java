package com.EM2.MoneyTransferApplication.model;

public class Account {

	private Long accId;
	private double balance;
	private Long userId;
	
	public Account(Long accId, double balance, Long userId) {
		this.accId = accId;
		this.balance = balance;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accId == null) ? 0 : accId.hashCode());
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
		if (accId == null) {
			if (other.accId != null)
				return false;
		} else if (!accId.equals(other.accId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", balance=" + balance + ", userId=" + userId + "]";
	}
	
	
	
	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
}
