package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import edu.asu.cse.softwaresecurity.group6.bosd.validations.UsernameValidator;

public class OTPRequestInfo {
	
	@UsernameValidator
	private String username;
	private String failureMessage;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	@Override
	public String toString() {
		return "GenerateOTP [username=" + username + ", failureMessage=" + failureMessage + "]";
	}
	
}
