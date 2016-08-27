package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import org.hibernate.validator.constraints.NotEmpty;

import edu.asu.cse.softwaresecurity.group6.bosd.validations.DateValidator;
import edu.asu.cse.softwaresecurity.group6.bosd.validations.NameValidator;
import edu.asu.cse.softwaresecurity.group6.bosd.validations.PhoneNumberValidator;
import edu.asu.cse.softwaresecurity.group6.bosd.validations.ValidEmail;

public class UserRegistration {
	private String username;
	@NameValidator
	private String firstname;
	@NameValidator
	private String lastname;
	@ValidEmail
	private String email;
	@NotEmpty(message="User Type must be chosen")
	private String userType;
	@PhoneNumberValidator
	private String contact;
	@DateValidator
	private String dob;
	@NotEmpty(message="Message can not be empty")
	private String address;
	private String successMessage;
	private String failureMessage;
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	private int isActive;
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getisActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "UserRegistration [username=" + username + ", firstname="
				+ firstname + ", lastname=" + lastname + ", email=" + email
				+ ", userType=" + userType + ", contact=" + contact + ", dob="
				+ dob + ", address=" + address + ", isActive=" + isActive + "]";
	}
	
}





