package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.RequestDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.DateAndTime;

/**
 * Request service implementation class
 * 
 * @author apchanda
 *
 */
public class RequestServiceImpl implements RequestService {

	public RequestDao getRequestDao() {
		return requestDao;
	}

	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}

	@Autowired
	RequestDao requestDao;

	@Override
	@Transactional
	public long createDebitCreditRequest(Request request) {
		return requestDao.createDebitCreditRequest(request);
	}

	@Override
	@Transactional
	public List<Request> getRequestForUser(String userName) {
		List<Request> requests = requestDao.findRequestsForUser(userName);
		return requests;
	}
	

@Override
	@Transactional
	public long createprofileUpdateRequest(Request request) {
		return requestDao.createprofileUpdateRequest(request);
	}@Override
	public boolean deleteRequest(long transId, String username) {

		return requestDao.deleteRequest(transId, username);
	}

	@Override
	public boolean updateRequestStatus(Long requestId, int approved, int pending) {
		//Check if the request is already acted on
		if(requestDao.getRequestStatus(requestId)) {
			if(requestDao.updateRequestStatus(requestId,approved,pending)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public List<Request> getMerchantRequestForUser(String currentUserName) {
		List<Request> mercReqs = requestDao.getMerchantRequestForUser(currentUserName);
		return mercReqs;
	}

	@Override
	public boolean updateAssignedToRequest(Long requestId, String assignedTo) {
		if(requestDao.updateAssignedToRequest(requestId, assignedTo))
			return true;
		else{
			return false;
		}
	}

	@Override
	public List<Request> getAccountRequestForUser(String currentUserName) {
		List<Request> accReqs = requestDao.getAccountRequestForUser(currentUserName);
		return accReqs;
	}

	@Override
	public Request getRequest(Long requestId) {
		Request req = requestDao.getRequest(requestId);
		return req;
	}

	@Override
	public boolean createRequest(Request req) {
		return requestDao.createRequest(req);
	}

	@Override
	public List<Request> getRequestOfTypeForUser(String type,
			String currentUserName) {
		return requestDao.getRequestOfTypeForUser(type,
				currentUserName);
	}

	@Override
	public boolean permittedOn(String username, String currentUserName,
			String typeOfPermission) {
		
		Timestamp timeStamp =  requestDao.permittedOn(username,currentUserName,typeOfPermission);
		if(timeStamp == null) {
			return false;
		}
		
		DateTime dateTime = new DateTime(timeStamp);
		System.out.println(dateTime);
		return DateAndTime.isDateTimeWithInPermissionPeriod(dateTime);
	}

	@Override
	public List<Request> getAllRequestForUser(String currentUserName) {
		return requestDao.getAllRequestForUser(currentUserName);
		
	}

	@Override
	public long createDeleteAccountRequest(Request request) {
		return requestDao.createDeleteAccountRequest(request);
	}
}
