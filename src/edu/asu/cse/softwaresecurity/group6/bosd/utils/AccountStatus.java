package edu.asu.cse.softwaresecurity.group6.bosd.utils;

public enum AccountStatus {
	OPEN("open"), CLOSE("close");
	
	private String accountStatus;
	
	private AccountStatus(String s){
		accountStatus = s;
	}
	
	public String getAccountStatus(){
		return accountStatus;
	}
}
