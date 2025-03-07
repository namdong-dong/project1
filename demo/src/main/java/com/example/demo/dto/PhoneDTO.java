package com.example.demo.dto;

public class PhoneDTO {

	private int pNO;
	private String phoneNumber;
	private String company; // 1: SKT, 2: KT, 3: LG U+, 4: 알뜰폰
	private String state;
	
	public PhoneDTO(int pNO, String phoneNumber, String company, String state) {
		super();
		this.pNO = pNO;
		this.phoneNumber = phoneNumber;
		this.company = company;
		this.state = state;
	}
	
	public int getpNO() {
		return pNO;
	}
	public void setpNO(int pNO) {
		this.pNO = pNO;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
