package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import edu.asu.cse.softwaresecurity.group6.bosd.validations.PasswordValidator;
import edu.asu.cse.softwaresecurity.group6.bosd.validations.SecurityQuestionValidator;

public class UserSignUp {
	
	private String username;
	@PasswordValidator
	private String password;
	@PasswordValidator
	private String repassword;
	@NotEmpty(message="PII Information must be submitted")
	private String PII;
	@Size(min=6, max=6, message="OTP entered is not valid")
	private String OTP;
	@SecurityQuestionValidator
	private String chosenSecurityQuestion;
	@NotEmpty(message="Security Question must be answered")
	private String securityAnswer;
	private String successMessage;
	private String failureMessage;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getPII() {
		return PII;
	}
	public void setPII(String pII) {
		PII = pII;
	}
	public String getChosenSecurityQuestion() {
		return chosenSecurityQuestion;
	}
	public void setChosenSecurityQuestion(String chosenSecurityQuestion) {
		this.chosenSecurityQuestion = chosenSecurityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	@Override
	public String toString() {
		return "UserSignUp [username=" + username + ", password=" + password + ", repassword=" + repassword + ", PII="
				+ PII + ", chosenSecurityQuestion=" + chosenSecurityQuestion + ", securityAnswer=" + securityAnswer
				+ ", OTP=" + OTP + "]";
	}
	
}
