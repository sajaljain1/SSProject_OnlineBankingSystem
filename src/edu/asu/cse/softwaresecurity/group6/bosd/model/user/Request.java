package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean class for Request table
 * 
 * @author apchanda
 *
 */

@Entity
@Table(name = "Request")
public class Request {
	@Id
	@Column(name = "requestId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requetId;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Long transactionId;
	private String type;
	private String assignedTo;
	private String username;
	private String updatedPhone;
	private String updatedAddress;
	private String updatedFirstName;
	private String updatedLastName;
	private String updatedSecurityQuestion;
	private String updatedSecurityAnswer;
	private String pending;
	private String approved;
	private String updatedEmail;
	private String remarks;
	private String permissionto;

	public String getUpdatedFirstName() {
		return updatedFirstName;
	}

	public void setUpdatedFirstName(String updatedFirstName) {
		this.updatedFirstName = updatedFirstName;
	}

	public String getUpdatedLastName() {
		return updatedLastName;
	}

	public void setUpdatedLastName(String updatedLastName) {
		this.updatedLastName = updatedLastName;
	}

	public String getUpdatedSecurityQuestion() {
		return updatedSecurityQuestion;
	}

	public void setUpdatedSecurityQuestion(String updatedSecurityQuestion) {
		this.updatedSecurityQuestion = updatedSecurityQuestion;
	}

	public String getUpdatedSecurityAnswer() {
		return updatedSecurityAnswer;
	}

	public void setUpdatedSecurityAnswer(String updatedSecurityAnswer) {
		this.updatedSecurityAnswer = updatedSecurityAnswer;
	}

	public Long getRequetId() {
		return requetId;
	}

	public void setRequetId(Long requetId) {
		this.requetId = requetId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getUpdatedPhone() {
		return updatedPhone;
	}

	public void setUpdatedPhone(String updatedPhone) {
		this.updatedPhone = updatedPhone;
	}

	public String getUpdatedAddress() {
		return updatedAddress;
	}

	public void setUpdatedAddress(String updatedAddress) {
		this.updatedAddress = updatedAddress;
	}

	public String getUpdatedEmail() {
		return updatedEmail;
	}

	public void setUpdatedEmail(String updatedEmail) {
		this.updatedEmail = updatedEmail;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getPermissionto() {
		return permissionto;
	}

	public void setPermissionto(String permissionto) {
		this.permissionto = permissionto;
	}
	
	
}
