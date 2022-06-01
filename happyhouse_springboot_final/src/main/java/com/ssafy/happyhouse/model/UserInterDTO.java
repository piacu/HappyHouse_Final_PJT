package com.ssafy.happyhouse.model;

public class UserInterDTO{
	private int no;
	private String id;
	private String donCode;
	
	
	public UserInterDTO(int no, String id, String donCode) {
		super();
		this.no = no;
		this.id = id;
		this.donCode = donCode;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDonCode() {
		return donCode;
	}


	public void setDonCode(String donCode) {
		this.donCode = donCode;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", donCode=" + donCode + "]";
	}
	
}


