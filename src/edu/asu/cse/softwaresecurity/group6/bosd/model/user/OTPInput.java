package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import javax.validation.constraints.Size;

public class OTPInput {
	@Size(min=6,max=6,message="Invalid OTP")
	private String OTP;
	private String failureMessage;
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	@Override
	public String toString() {
		return "OTPInput [OTP=" + OTP + ", failureMessage=" + failureMessage
				+ "]";
	}
	
	
}
