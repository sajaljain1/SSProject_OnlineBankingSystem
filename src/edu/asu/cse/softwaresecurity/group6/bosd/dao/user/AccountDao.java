package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;

/**
 * Interface to declare operations to be performed on account
 * 
 * @author apchanda
 *
 */

public interface AccountDao {

	ArrayList<Account> fetchUserAccounts(String username);
	
	ArrayList<Account> fetchAccounts(String username);

	ArrayList<Account> fetchOtherAccounts(String username);
	
	ArrayList<Account> fetchUserAccounts(String username, String accType);
	
	ArrayList<Account> fetchAccounts(String username, String accNo);

	// boolean debitAmount(String accID, double amount);
	//
	// boolean creditAmount(String accID, double amount);

	Account fetchAccountDetail(String accID) throws Exception;

	double getBalance(String accID);

	boolean updateBalance(String accID, double bal);

	String getUsername(String accID);

	boolean transfer(String senderAccID, String receiverAccID,
			double senderAmt, double receiverAmt);
	
	boolean createAccount(Account account);
	
	boolean deleteAccount(String username);
	
	String getLatestAccount(); 
	
	public ArrayList<Account> fetchActiveAccounts(String username);

}
