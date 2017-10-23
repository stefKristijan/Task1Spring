package com.EM2.MoneyTransferApplication.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

//@Entity
//@Table(name="user")
public class User {

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(name="user_id")
	private int id;
//	
//	@Column(name = "username")
//	@Length(min=5)
//	@NotEmpty()
	private String username;
	
//	@Column(name="password")
//	@Length(min=5)
//	@NotEmpty
	private String password;
	
//	@Column(name="age")
	private int age;
	
//	@Column(name="creation_time")
	private Timestamp creationTime;
	
//	@Column(name="role")
//	@NotNull
	private String role;	
	
	public User(int id, String username, String password, int age, Timestamp dateCreated, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
		this.creationTime = dateCreated;
		this.role = role;
}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", age=" + age
				+ ", creationTime=" + creationTime + ", role=" + role + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
