package com.example.demo.vo;


public class PhoneVO {
	private int phoneNo;
	private String phoneNumber;
	private String company;
	private String phoneState;
	private int userNo;


	public PhoneVO() {
		// 기본 생성자 (필드 초기값은 필요하면 설정)
	}
	
	public void setPhoneNumber(String phoneNumber) {
//		if (phoneNumber != null && !phoneNumber.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
//			throw new IllegalArgumentException("유효하지 않은 전화번호 형식입니다.");
//		}
		this.phoneNumber = phoneNumber;
	}

	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneState() {
		return phoneState;
	}

	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}

}