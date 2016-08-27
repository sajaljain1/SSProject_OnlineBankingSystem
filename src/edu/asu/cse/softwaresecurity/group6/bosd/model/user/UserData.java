package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserData")
public class UserData {

	private String username;
	private String firstname;
	private String lastname;
	private String contact;
	private String email;
	private String address;
	private Date birthdate;
	private String pii;
	private String pii_status;
	private String securityquestion;
	private String securityanswer;
	private String merchantid;
	private String created_at;
	private String updated_at;
	
	@Column(name = "updated_at")
	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	@Id
	@Column(name = "username", unique = true, nullable = false, length = 15)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "firstname", nullable = false, length = 30)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 30)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "contact", nullable = false, length = 10)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "email", nullable = false, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", nullable = false, length = 250)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "birthdate", nullable = false)
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "pii", nullable = false, length = 30)
	public String getPii() {
		return pii;
	}

	public void setPii(String pii) {
		this.pii = pii;
	}

	@Column(name = "securityquestion", nullable = false, length = 255)
	public String getSecurityquestion() {
		return securityquestion;
	}

	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}

	@Column(name = "securityanswer", nullable = false, length = 255)
	public String getSecurityanswer() {
		return securityanswer;
	}

	public void setSecurityanswer(String securityanswer) {
		this.securityanswer = securityanswer;
	}

	@Column(name = "merchantID", length = 15)
	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname;
	}

	public String getPii_status() {
		return pii_status;
	}

	public void setPii_status(String pii_status) {
		this.pii_status = pii_status;
	}
	
	@Column(name = "created_at")
	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
