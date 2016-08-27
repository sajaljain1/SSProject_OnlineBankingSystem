package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean class for account table
 * 
 * @author apchanda
 *
 */

@Entity
@Table(name = "Account")
public class Account {
	private String accID;
	private String username;
	private double balance;
	private String acctype;
	private String accstatus;
	private Date created_at;
	private Date updated_at;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getAccstatus() {
		return accstatus;
	}

	public void setAccstatus(String accstatus) {
		this.accstatus = accstatus;
	}

	@Id
	@Column(name = "accId", unique = true, nullable = false)
	public String getAccID() {
		return accID;
	}

	public void setAccID(String accID) {
		this.accID = accID;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return balance + " " + acctype + " " + accstatus;
	}

}
