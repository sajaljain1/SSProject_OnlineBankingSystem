package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

/**
 * Implementation class of Account interface
 * 
 * @author apchanda
 *
 */

public class AccountDaoImpl implements AccountDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public ArrayList<Account> fetchUserAccounts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username = :username");
		query.setParameter("username", username);
		List<Account> accountList = query.list();
		return (ArrayList<Account>) accountList;
	}

	// @Override
	// public boolean debitAmount(String accID, double balance) {
	// Session session = this.sessionFactory.getCurrentSession();
	// Query query = session.createQuery("update Account set balance = :balance" + " where accID = :accID");
	// query.setParameter("balance", balance);
	// query.setParameter("accID", accID);
	// int result = query.executeUpdate();
	// return true;
	// }
	//
	// @Override
	// public boolean creditAmount(String accID, double balance) {
	// Session session = this.sessionFactory.getCurrentSession();
	// Query query = session.createQuery("update Account set balance = :balance" + " where accID = :accID");
	// query.setParameter("balance", balance);
	// query.setParameter("accID", accID);
	// int result = query.executeUpdate();
	// return true;
	// }
	@Override
	public ArrayList<Account> fetchUserAccounts(String username, String accType) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username  = :username and accType = :accType");
		query.setParameter("username", username);
		query.setParameter("accType", accType);
		List<Account> accountList = query.list();
		return (ArrayList<Account>) accountList;
	}
	
	@Override
	public ArrayList<Account> fetchAccounts(String username, String accNo) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username  = :username and accId = :accNo and accStatus =:accStatus");
		query.setParameter("username", username);
		query.setParameter("accNo", accNo);
		query.setParameter("accStatus", "open");
		List<Account> accountList = query.list();
		return (ArrayList<Account>) accountList;
	}

	@Override
	public ArrayList<Account> fetchAccounts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username = :username");
		query.setParameter("username", username);
		List<Account> accountList = query.list();
		if(accountList!=null) System.out.println("accounts lniked to " + username + " are " + accountList.size());
		return (ArrayList<Account>) accountList;
	}

	@Override
	public ArrayList<Account> fetchOtherAccounts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username <> :username");
		query.setParameter("username", username);
		List<Account> accountList = query.list();
		if(accountList!=null) System.out.println("accounts lniked to " + username + " are " + accountList.size());
		return (ArrayList<Account>) accountList;
	}
	
	@Override
	public Account fetchAccountDetail(String accID) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		// Account acc = (Account) session.load(Account.class, accID);
		Query query = session.createQuery("from Account acc where acc.accID  = :accID and accStatus = :accStatus");
		query.setParameter("accID", accID);
		query.setParameter("accStatus", "open");
		List<Account> accountList = query.list();
		return accountList.get(0);
		// return acc;
	}

	@Override
	public double getBalance(String accID) {
//		Session session = this.sessionFactory.getCurrentSession();
		Account acc;
		try {
			acc = fetchAccountDetail(accID);
			return acc.getBalance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean updateBalance(String accID, double bal) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Account set balance = :balance" + " where accID = :accID");
		query.setParameter("balance", bal);
		query.setParameter("accID", accID);
		int result = query.executeUpdate();
		return true;
	}
	
	@Override
	public String getUsername(String accID) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select username from Account where accID = :accID");
		query.setParameter("accID", accID);
		List<String> username = query.list();
		return username.get(0);

	}

	@Override
	public boolean transfer(String senderAccID, String receiverAccID,
			double senderAmt, double receiverAmt) {
		Session session = this.sessionFactory.getCurrentSession();
		
		try{
			Query query = session.createQuery("update Account set balance = :balance where accID = :accID");
			query.setParameter("balance", senderAmt);
			query.setParameter("accID", senderAccID);
			query.executeUpdate();
			Query query1 = session.createQuery("update Account set balance = :balance where accID = :accID");
			query1.setParameter("balance", receiverAmt);
			query1.setParameter("accID", receiverAccID);
			query1.executeUpdate();
		}
		catch(HibernateException e){
			session.getTransaction().rollback();
			return false;
		}
		return true;
	}
	
	public boolean createAccount(Account account){
		Session session = this.sessionFactory.getCurrentSession();
		session.save(account);
		return true;
	}
	
	public boolean deleteAccount(String username){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Account set accstatus = :accStatus where username = :username");
		query.setParameter("username", username);
		query.setParameter("accStatus", "close");
		int result = query.executeUpdate();
		
		query = session.createQuery("update UserData set pii = :pii,pii_status = :pii_status, email = :email where username = :username");
		query.setParameter("username", username);
		query.setParameter("pii", "");
		query.setParameter("pii_status", "");
		query.setParameter("email", username);
		result = query.executeUpdate();
		
		query = session.createQuery("delete User where username = :username");
		query.setParameter("username", username);
		result = query.executeUpdate();
		return true;
	}
	
	public String getLatestAccount(){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("Select accId from Account ORDER BY accId DESC");
		List<String> accnum = query.list();
		if (accnum.size() == 0) {
			return UtilMessages.ACCOUNT_NUMBER_START;
		}
		return accnum.get(0);
	}
	
	@Override
	public ArrayList<Account> fetchActiveAccounts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Account acc where acc.username = :username and accStatus = :accStatus");
		query.setParameter("username", username);
		query.setParameter("accStatus", "open");
		List<Account> accountList = query.list();
		if(accountList!=null) System.out.println("accounts lniked to " + username + " are " + accountList.size());
		return (ArrayList<Account>) accountList;
	}

}
