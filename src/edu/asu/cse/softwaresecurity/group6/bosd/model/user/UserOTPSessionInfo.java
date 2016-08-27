package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

public class UserOTPSessionInfo {
	private String username;
	private String securityQuestion;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	@Override
	public String toString() {
		return "UserOTPSessionInfo [username=" + username + ", securityQuestion=" + securityQuestion + "]";
	}
	
}
