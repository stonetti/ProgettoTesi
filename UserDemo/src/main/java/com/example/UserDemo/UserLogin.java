package com.example.UserDemo;

public class UserLogin {
	String username;
	String fullName;
	String password;
	
	public UserLogin(String name, String fullName, String password){
		this.fullName = fullName;
		this.username = name;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}