package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.AccountDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;

/**
 * Account service implementation class
 * 
 * @author apchanda
 *
 */
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDao accountDao;

	@Override
	@Transactional
	public ArrayList<Account> fetchUserAccounts(String username) {
		return accountDao.fetchUserAccounts(username);
	}
	
	@Override
	@Transactional
	public ArrayList<Account> fetchUserAccounts(String username, String accType) {
		return accountDao.fetchUserAccounts(username, accType);
	}

	@Override
	@Transactional
	public ArrayList<Account> fetchAccounts(String username) {
		return accountDao.fetchActiveAccounts(username);
	}
	
	@Override
	@Transactional
	public ArrayList<Account> fetchOtherAccounts(String username) {
		return accountDao.fetchOtherAccounts(username);
	}
	
	@Override
	@Transactional
	public ArrayList<Account> fetchAccounts(String username, String accNo) {
		return accountDao.fetchAccounts(username, accNo);
	}
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	
	public String getLatestAccount(){
		return accountDao.getLatestAccount();
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	// @Override
	// @Transactional
	// public boolean debitAmount(String accID, double amount) {
	// return accountDao.debitAmount(accID, amount);
	// }
	//
	// @Override
	// @Transactional
	// public boolean creditAmount(String accID, double amount) {
	// return accountDao.creditAmount(accID, amount);
	// }

	@Override
	@Transactional
	public Account fetchAccountDetail(String accID) throws Exception {
		return accountDao.fetchAccountDetail(accID);
	}

	@Override
	@Transactional
	public double getBalance(String accID) {

		return accountDao.getBalance(accID);
	}

	@Override
	@Transactional
	public boolean updateBalance(String accID, double bal) {
		return accountDao.updateBalance(accID, bal);
	}

	@Override
	@Transactional
	public String getUsername(String accID) {
		return accountDao.getUsername(accID);
	}

	public boolean createAccount(Account account){
		return accountDao.createAccount(account);
	}
	
	@Transactional
	public boolean deleteAccount(String username) {

		if (((StringUtils.equalsIgnoreCase(username, "admin")) || (StringUtils.equalsIgnoreCase(username, "sysmgr"))
				|| (StringUtils.equalsIgnoreCase(username, "regemp")))) {
			return true;
		}
		else {
			return accountDao.deleteAccount(username);
		}
		
	}

	@Override
	@Transactional
	public boolean transfer(String senderAccID, String receiverAccID, double senderAmt, double receiverAmt) {
		return accountDao.transfer(senderAccID, receiverAccID, senderAmt, receiverAmt);
	}

	@Override
	@Transactional
	public ArrayList<Account> fetchActiveAccounts(String username) {
		return accountDao.fetchAccounts(username);
	}

}
