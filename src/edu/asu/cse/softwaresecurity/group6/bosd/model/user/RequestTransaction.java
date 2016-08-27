package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

public class RequestTransaction {
	private Request request;
	private Transaction transaction;
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
}
