package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Transaction;

/**
 * Transaction service interface to declare all the methods to be used
 * 
 * @author apchanda
 *
 */
public interface TransactionService {

	ArrayList<Transaction> getPendingTransactionList(String accNo);

	ArrayList<Transaction> getTransactionList(String username, String accNo, String startDate, String endDate);

	long createTransaction(Transaction trans);

	long updateTransaction(Transaction trans);

	Transaction getTransaction(long transId);

	ArrayList<Transaction> getTransactionList(String accNo);

	boolean deleteTransaction(long transId);

	void updateTransactionStatus(long parseLong,boolean approved, boolean pending);

}
