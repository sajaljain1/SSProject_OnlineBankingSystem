package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.sql.Timestamp;

public class TransactionUpdateModel {
	private Long transactionId;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String account_from;
	private String amount;
	private String type;
	private boolean pending;
	private boolean approved;
	private String account_to;
	private double prevAmount;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public String getAccount_from() {
		return account_from;
	}

	public void setAccount_from(String account_from) {
		this.account_from = account_from;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getAccount_to() {
		return account_to;
	}

	public void setAccount_to(String account_to) {
		this.account_to = account_to;
	}

	public double getPrevAmount() {
		return prevAmount;
	}

	public void setPrevAmount(double prevAmount) {
		this.prevAmount = prevAmount;
	}

	@Override
	public String toString() {
		return prevAmount + " " + amount + " " + transactionId + " " + created_at.toString();
	}
}
