package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;

/**
 * Interface to declare operations to be performed on Transaction table
 * 
 * @author apchanda
 *
 */
public interface TransactionDao {

	ArrayList<Transaction> getPendingTransactionList(String accNo);

	ArrayList<Transaction> getTransactionList(String username, String accNo, String startDate, String endDate);

	ArrayList<Transaction> getTransactionList(String accNo);

	long createTransaction(Transaction trans);

	long updateTransaction(Transaction trans);

	Transaction getTransaction(long transId);

	boolean deleteTransaction(long transId);

	void updateTransactionStatus(long transactionId, boolean approved, boolean pending);

}
