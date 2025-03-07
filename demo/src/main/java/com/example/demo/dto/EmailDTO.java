package com.example.demo.dto;

public class EmailDTO {
	private int eNO;
	private String email;
	private String state;
	
	public EmailDTO(int eNO, String email, String state) {
		super();
		this.eNO = eNO;
		this.email = email;
		this.state = state;
	}
	
	public int geteNO() {
		return eNO;
	}
	public void seteNO(int eNO) {
		this.eNO = eNO;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
