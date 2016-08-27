package edu.asu.cse.softwaresecurity.group6.bosd.utils;

public enum AccountType {
	Savings("SAVINGS"), CHECKING("CHECKING");
	
	private String accountType;
	
	private AccountType(String s){
		accountType = s;
	}
	
	public String getAccountType(){
		return accountType;
	}
}
