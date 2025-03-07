package com.example.demo.dto;

public class AddressDTO {

	private int aNO;
	private String address;
	private int zip;
	
	public AddressDTO(int aNO, String address, int zip) {
		super();
		this.aNO = aNO;
		this.address = address;
		this.zip = zip;
	}
	
	public int getaNO() {
		return aNO;
	}
	public void setaNO(int aNO) {
		this.aNO = aNO;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	

}
