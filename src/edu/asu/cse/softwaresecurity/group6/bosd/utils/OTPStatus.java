package edu.asu.cse.softwaresecurity.group6.bosd.utils;

public enum OTPStatus {
	
	SIGN_UP("L"), FORGOT_PWD("F"), STATEMENTS("S"), CRITICAL_TRANSACTIONS("C");
	
	private String statusCode;
	
	private OTPStatus(String s) {
		statusCode = s;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
