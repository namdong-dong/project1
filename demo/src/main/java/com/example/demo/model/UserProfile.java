package com.example.demo.model;

public class UserProfile {
	private String id;
	private String name;
	private String phone;
	private String address;
	private String pw;
	private String phoneCompany;
	private String email;
	
	public UserProfile(String id, String name, String phone, String address, String pw, String phoneCompany, String email) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.pw = pw;
		this.phoneCompany = phoneCompany;
		this.email = email;
	}

	public String getPhoneCompany() {
		return phoneCompany;
	}

	public void setPhoneCompany(String phoneCompany) {
		this.phoneCompany = phoneCompany;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}