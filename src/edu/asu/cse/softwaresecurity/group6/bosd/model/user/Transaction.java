package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean class for transaction table
 * 
 * @author apchanda
 *
 */

@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@Column(name = "transactionId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	private Timestamp created_at;
	private Timestamp updated_at;
	private String account_from;
	private double amount;
	private String type;
	private boolean pending;
	private boolean approved;
	private String account_to;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccount_to() {
		return account_to;
	}

	public void setAccount_to(String account_to) {
		this.account_to = account_to;
	}

	public String getAccount_from() {
		return account_from;
	}

	public void setAccount_from(String account_from) {
		this.account_from = account_from;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
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

	@Override
	public String toString() {
		return transactionId + " " + amount + " " + type;
	}

}
