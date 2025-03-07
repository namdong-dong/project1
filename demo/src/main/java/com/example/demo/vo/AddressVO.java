package com.example.demo.vo;


public class AddressVO {
	private int addressNo;
	private String address;
	private int zip;
	private int userNo;

	public AddressVO(int addressNo, String address, int zip) {
		this.addressNo = addressNo;
		this.address = address;
		this.zip = zip;
	}
	public AddressVO() {
		
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public void setAddressNo(int addressNo) {
		this.addressNo = addressNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getAddressNo() {
		return addressNo;
	}

	public String getAddress() {
		return address;
	}
	
	public int getZip() {
		return zip;
	}
	
}