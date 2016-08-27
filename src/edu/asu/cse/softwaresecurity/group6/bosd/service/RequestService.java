package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.List;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;

/**
 * Request service interface
 * 
 * @author apchanda
 *
 */
public interface RequestService {
	Request getRequest(Long requestId);
	long createDebitCreditRequest(Request request);
	List<Request> getRequestForUser(String userName);
	boolean updateRequestStatus(Long requestId,int approved,int pending);
	boolean updateAssignedToRequest(Long requestId, String assignedTo);
	long createprofileUpdateRequest(Request request);
	boolean deleteRequest(long transId, String username);
	List<Request> getMerchantRequestForUser(String currentUserName);
	List<Request> getAccountRequestForUser(String currentUserName);
	public boolean createRequest(Request req);
	List<Request> getRequestOfTypeForUser(String string, String currentUserName);
	boolean permittedOn(String username, String currentUserName, String typeOfPermission);
	List<Request> getAllRequestForUser(String currentUserName);
	long createDeleteAccountRequest(Request request) ;
}
