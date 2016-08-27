package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;

/**
 * Account service interface to declare all the methods to be used
 * 
 * @author apchanda
 *
 */
public interface AccountService {
	ArrayList<Account> fetchUserAccounts(String username);
	
	ArrayList<Account> fetchUserAccounts(String username, String accType);
	
	ArrayList<Account> fetchAccounts(String username);
	
	ArrayList<Account> fetchOtherAccounts(String username);
	
	ArrayList<Account> fetchAccounts(String username, String accNo);

	// boolean debitAmount(String accID, double amount);
	//
	// boolean creditAmount(String accID, double amount);

	Account fetchAccountDetail(String accID) throws Exception;

	double getBalance(String accID);

	boolean updateBalance(String accID, double bal);
	
	String getUsername(String accID);
	
	boolean createAccount(Account account);
	
	boolean deleteAccount(String username);
	
	String getLatestAccount(); 

	boolean transfer(String senderAccID, String receiverAccID,
			double senderAmt, double receiverAmt);
	
	public ArrayList<Account> fetchActiveAccounts(String username);
}
