package com.example.demo.vo;


public class EmailVO {
	private int emailNo;
	private String email;
	private String emailState;
	private int userNo;



	public EmailVO() {
		
	}
	
	public EmailVO(int emailNo, String email, String emailState) {
		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
		}
		this.emailNo = emailNo;
		this.email = email;
		this.emailState = emailState;
	}

	public int getEmailNo() {
		return emailNo;
	}
	
	public void setEmailNo(int emailNo) {
		this.emailNo = emailNo;
	}
	
	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getEmail() {
		return email;
	}

	public String getEmailState() {
		return emailState;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailState(String emailState) {
		this.emailState = emailState;
	}

	
	
	// Getter만 제공 (불변성 유지)
}