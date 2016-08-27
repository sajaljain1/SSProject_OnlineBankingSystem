package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.sql.Timestamp;
import java.util.List;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;

/**
 * Interface to declare operations to be performed on Request table
 * 
 * @author apchanda
 *
 */
public interface RequestDao {

	long createDebitCreditRequest(Request request);
	List<Request> findRequestsForUser(String username);
	long createprofileUpdateRequest(Request request);
	boolean deleteRequest(long transId, String username);
	boolean updateRequestStatus(Long requestId, int approved, int pending);
	List<Request> getMerchantRequestForUser(String currentUserName);
	boolean updateAssignedToRequest(Long requestId, String assignedTo);
	List<Request> getAccountRequestForUser(String currentUserName);
	Request getRequest(Long requestId);
	boolean createRequest(Request req);
	List<Request> getRequestOfTypeForUser(String type, String currentUserName);
	Timestamp permittedOn(String username, String currentUserName,String typeOfPermission);
	List<Request> getAllRequestForUser(String currentUserName);
	boolean getRequestStatus(Long requestId);
	long createDeleteAccountRequest(Request request) ;
}
