package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import edu.asu.cse.softwaresecurity.group6.bosd.validations.PasswordValidator;

public class ChangePassword {
	
	private String username;
	@PasswordValidator
	private String newPassword;
	@PasswordValidator
	private String confirmPassword;
	@Size(min=6, max=6, message="OTP entered is not valid")
	private String OTP;
	private String chosenSecurityQuestion; 
	@NotEmpty(message="Security Question must be answered")
	private String securityAnswer;
	private String successMessage;
	private String failureMessage;
	
	public ChangePassword() {
		
	}
	
	public ChangePassword(String username, String newPassword, String confirmPassword, String oTP) {
		super();
		this.username = username;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
		OTP = oTP;
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
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
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

	@Override
	public String toString() {
		return "ChangePassword [username=" + username + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + ", OTP=" + OTP + ", chosenSecurityQuestion=" + chosenSecurityQuestion
				+ ", securityAnswer=" + securityAnswer + ", successMessage=" + successMessage + ", failureMessage="
				+ failureMessage + "]";
	}
	
}
