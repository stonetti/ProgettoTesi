package com.example.Repository;

import java.util.Date;

public class UserDao {
	
	private int id;
	private String name;
	private String lastname;
	private int age;
	private String address;
	private String username;
	private String email;
	private String password;
	private String gender;
	private String profilePic; 	
	private Date registrationDate;
	private Date lastLogin;
	private int[] friends;
	private int[] posts;
	private boolean publicProfile;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int[] getFriends() {
		return friends;
	}
	public void setFriends(int[] friends) {
		this.friends = friends;
	}
	public int[] getPosts() {
		return posts;
	}
	public void setPosts(int[] posts) {
		this.posts = posts;
	}
	public boolean isPublicProfile() {
		return publicProfile;
	}
	public void setPublicProfile(boolean publicProfile) {
		this.publicProfile = publicProfile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
    
}
