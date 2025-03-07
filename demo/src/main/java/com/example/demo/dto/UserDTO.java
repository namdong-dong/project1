package com.example.demo.dto;

public class UserDTO {
	private String name;
	private String gender;
	private String birthday;
	private String joinDate;
	private String lastLogin;
	private String state;
	
	public UserDTO(String name, String gender, String birthday, String joinDate, String lastLogin, String state) {
		super();
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.joinDate = joinDate;
		this.lastLogin = lastLogin;
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
