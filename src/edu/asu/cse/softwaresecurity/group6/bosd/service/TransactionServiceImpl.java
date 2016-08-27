package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.TransactionDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;

/**
 * Transaction service implementation class
 * 
 * @author apchanda
 *
 */
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDao transactionDao;

	@Override
	@Transactional
	public ArrayList<Transaction> getPendingTransactionList(String accNo) {
		return transactionDao.getPendingTransactionList(accNo);
	}

	@Override
	@Transactional
	public ArrayList<Transaction> getTransactionList(String username, String accNo, String startDate, String endDate) {
		return transactionDao.getTransactionList(username, accNo, startDate, endDate);
	}

	@Override
	@Transactional
	public long createTransaction(Transaction trans) {
		return transactionDao.createTransaction(trans);
	}

	@Override
	@Transactional
	public long updateTransaction(Transaction trans) {
		return transactionDao.updateTransaction(trans);
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	@Override
	public Transaction getTransaction(long transId) {
		return transactionDao.getTransaction(transId);
	}

	@Override
	public ArrayList<Transaction> getTransactionList(String accNo) {
		return transactionDao.getTransactionList(accNo);
	}

	@Override
	public boolean deleteTransaction(long transId) {
		return transactionDao.deleteTransaction(transId);
	}

	@Override
	public void updateTransactionStatus(long transactionId, boolean approved, boolean pending) {
		transactionDao.updateTransactionStatus(transactionId, approved, pending);
		
	}

}
