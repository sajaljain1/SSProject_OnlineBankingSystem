package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

public class DebitCreditModel {

	private String amount;
	private Account account;
	private String balance;
	private String acctype;
	private String accID;
	private String username;

	public Account getAccount() {
		return account;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return amount + " " + accID + " " + acctype + " " + balance;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getAccID() {
		return accID;
	}

	public void setAccID(String accID) {
		this.accID = accID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
