package com.example.demo.vo;

public class UserVO {
	private int userNo;
	private String name;

	private String gender;
	private String birthday;
	private String joinDate;
	private String lastLogin;
	private String state;
	private PhoneVO phoneVO;
	private EmailVO emailVO;
	private AddressVO addressVO;

	public UserVO(int userNo,
			String name, String gender, String birthday,
			String joinDate, String lastLogin, String state) {
		this.userNo = userNo;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.joinDate = joinDate;
		this.lastLogin = lastLogin;
		this.state = state;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
		if (phoneVO != null) {
			phoneVO.setUserNo(userNo);
		}
		if (emailVO != null) {
			emailVO.setUserNo(userNo);
		}
		if (addressVO != null) {
			addressVO.setUserNo(userNo);
		}
		
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "UserVO [userNo=" + userNo + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday
				+ ", joinDate=" + joinDate + ", lastLogin=" + lastLogin + ", state=" + state + ", phoneVO=" + phoneVO
				+ ", emailVO=" + emailVO + ", addressVO=" + addressVO + "]";
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getUserNo() {
		return userNo;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public String getState() {
		return state;
	}

	public PhoneVO getPhoneVO() {
		return phoneVO;
	}

	public void setPhoneVO(PhoneVO phoneVO) {
		this.phoneVO = phoneVO;
	}

	public EmailVO getEmailVO() {
		return emailVO;
	}

	public void setEmailVO(EmailVO emailVO) {
		this.emailVO = emailVO;
	}

	public AddressVO getAddressVO() {
		return addressVO;
	}

	public void setAddressVO(AddressVO addressVO) {
		this.addressVO = addressVO;
	}


	// Getter만 제공 (불변성 유지)
}