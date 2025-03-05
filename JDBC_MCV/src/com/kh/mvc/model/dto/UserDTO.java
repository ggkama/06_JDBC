package com.kh.mvc.model.dto;

import java.sql.Date;

public class UserDTO { // 필드 5개 필요 (컬럼 5개를 가져오기때문에)
	
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private Date enrollDate;
	
	
	
	
	

	public UserDTO() {
		super();

	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	
	
}
