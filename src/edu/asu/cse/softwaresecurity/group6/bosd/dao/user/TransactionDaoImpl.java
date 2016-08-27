package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;

/**
 * Implementation class of TransactionDao interface
 * 
 * @author apchanda
 *
 */
public class TransactionDaoImpl implements TransactionDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public ArrayList<Transaction> getPendingTransactionList(String accNo) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Transaction trans where trans.account_from = :accID AND trans.pending = :status ");
		query.setParameter("accID", accNo);
		query.setParameter("status", true);
		List<Transaction> pendingTransactionList = query.list();
		return (ArrayList<Transaction>) pendingTransactionList;
	}

	@Override
	public ArrayList<Transaction> getTransactionList(String username, String accNo, String startDate, String endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select trans.amount, trans.type, trans.pending, "
						+ "trans.approved, DATE(trans.updated_at), trans.account_from ,"
						+ "trans.account_to from Account acc inner join Transaction trans on (acc.accId = trans.account_to or acc.accId = trans.account_from ) where acc.username = :username and acc.accID = :accID and (trans.created_at between :startdate and :enddate or trans.updated_at  between :startdate and :enddate) ;");
		// Query query =
		// session.createSQLQuery("select trans.* from Account acc inner join Transaction trans on (acc.accId = trans.account_to or acc.accId = trans.account_from ) ;");
		query.setParameter("username", username);
		query.setParameter("accID", accNo);
		query.setParameter("startdate", startDate);
		query.setParameter("enddate", endDate + " 23:59:59");
		List<Transaction> TransactionList = query.list();
		return (ArrayList<Transaction>) TransactionList;
	}

	@Override
	public long createTransaction(Transaction trans) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(trans);
		return trans.getTransactionId();
	}

	@Override
	public long updateTransaction(Transaction trans) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Transaction set updated_at = :updated_at, amount = :amount"
				+ " where transactionId = :transID");
		query.setParameter("updated_at", trans.getUpdated_at());
		query.setParameter("amount", trans.getAmount());
		query.setParameter("transID", trans.getTransactionId());
		int result = query.executeUpdate();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Transaction getTransaction(long transId) {
		List<Transaction> trans = new ArrayList<Transaction>();
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Transaction trans where trans.transactionId = :transId");
		query.setParameter("transId", transId);
		trans = query.list();
		if(trans!= null && trans.size() > 0)
			return trans.get(0);
		else
			return null;
	}

	@Override
	public ArrayList<Transaction> getTransactionList(String accNo) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Transaction trans where trans.account_from = :accID OR trans.account_to = :accID ");
		query.setParameter("accID", accNo);
		List<Transaction> transactionList = query.list();
		return (ArrayList<Transaction>) transactionList;
	}

	@Override
	public boolean deleteTransaction(long transId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query q = session.createQuery("delete from Transaction where transactionId = :transId");
			q.setParameter("transId", transId);
			q.executeUpdate();
			return true;
		} catch (HibernateException e) {
			return false;
		}
	}

	@Override
	public void updateTransactionStatus(long transactionId, boolean approved, boolean pending) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Transaction set pending = :pending, approved = :approved"
				+ " where transactionId = :transID");
		query.setParameter("pending", pending);
		query.setParameter("approved", approved);
		query.setParameter("transID",transactionId);
		int result = query.executeUpdate();
		//return result;
	}

}
