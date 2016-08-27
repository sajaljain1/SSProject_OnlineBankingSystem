package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Request;

/**
 * Implementation class of RequestDao interface
 * 
 * @author apchanda
 *
 */
public class RequestDaoImpl implements RequestDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public long createDebitCreditRequest(Request request) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(request);
		return request.getTransactionId();
	}
	@Override
	public long createDeleteAccountRequest(Request request) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(request);
		return request.getTransactionId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Request> findRequestsForUser(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where assignedTo is ? and pending is ?";
		Query q = session.createQuery(hquery);
		q.setString(0, username);
		q.setInteger(1, 1);
		
		List<Request> requests = q.list();
		if(requests!=null)	System.out.println(requests.size());
		return requests;
	}

	@Override
	public boolean deleteRequest(long transId, String username) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Query q = session
					.createQuery("delete from Request where transactionId = :transId AND username = :username");
			q.setParameter("transId", transId);
			q.setParameter("username", username);
			System.out.println("******************************** " + q.toString());
			q.executeUpdate();
			return true;
		} catch (HibernateException e) {
			return false;
		}
	}
	@Override
	public long createprofileUpdateRequest(Request request) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(request);
		return request.getRequetId();
	}

	@Override
	public boolean updateRequestStatus(Long requestId, int approved, int pending) {
		if(getRequestStatus(requestId)){
			
			System.out.println("requestId to be updated is "+requestId);
			Session session = this.sessionFactory.getCurrentSession();
			String que = "update Request set approved = ?, pending = ? where requestId = ?";
			Query query = session.createQuery(que);
			query.setInteger(0, approved);
			query.setInteger(1, pending);
			query.setLong(2, requestId);
			int result = query.executeUpdate();
			if(result == 0) return false;
		}else{
			return false;
		}
		return true;
	}
	
	@Override
	public boolean getRequestStatus(Long requestId) {
		System.out.println("requestId to be updated is "+requestId);
		Session session = this.sessionFactory.getCurrentSession();
		String que = "select pending from Request where requestId = ?";
		Query query = session.createQuery(que);
		query.setLong(0, requestId);
		List<String> result = query.list();
		if(result.size() == 0) return false;
		if(StringUtils.equalsIgnoreCase("1", result.get(0))) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Request> getMerchantRequestForUser(String currentUserName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where assignedTo is ? and pending is ? and type is ?";
		Query q = session.createQuery(hquery);
		q.setString(0, currentUserName);
		q.setInteger(1, 1);
		q.setString(2, "merchant");
		List<Request> requests = q.list();
		if(requests!=null)	System.out.println(requests.size());
		return requests;
	}
	
	@Override
	public boolean updateAssignedToRequest(Long requestId, String assignedTo) {
		System.out.println("assigninig to " + assignedTo);
		Session session = this.sessionFactory.getCurrentSession();
		String que = "update Request set assignedTo = ? where requestId = ?";
		Query q = session.createQuery(que);
		q.setString(0, assignedTo);
		q.setLong(1,requestId);
		int result = q.executeUpdate();
		if(result == 0) return false;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Request> getAccountRequestForUser(String currentUserName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where assignedTo is ? and pending is ? and type is ?";
		Query q = session.createQuery(hquery);
		q.setString(0, currentUserName);
		q.setInteger(1, 1);
		q.setString(2, "Profile Update");
		List<Request> requests = q.list();
		if(requests!=null)	System.out.println(requests.size());
		return requests;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Request getRequest(Long requestId) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where requestId is :requestId";
		Query q = session.createQuery(hquery);
		q.setParameter("requestId", requestId);
		List<Request> reqs = q.list();
		if(reqs.size()==0) return null;
		return reqs.get(0);
	}

	@Override
	public boolean createRequest(Request req) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(req);
		return true;
	}

	@Override
	public List<Request> getRequestOfTypeForUser(String type,
			String currentUserName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where assignedTo is ? and type is ? and pending is ?";
		Query q = session.createQuery(hquery);
		q.setString(0, currentUserName);
		q.setString(1, type);
		q.setInteger(2, 1);
		
		List<Request> requests = q.list();
		if(requests!=null)	System.out.println(requests.size());
		if(requests.size() == 0) return null;
		return requests;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Timestamp permittedOn(String username, String currentUserName, String typeOfPermission) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "select updated_at FROM Request where username = :username and type = :type"
				+ " and approved = :approved and permissionto = :permission ORDER BY updated_at DESC";
		Query q = session.createQuery(hquery);
		q.setParameter("username", username);
		q.setParameter("type", typeOfPermission);
		q.setParameter("approved", "1");
		q.setParameter("permission", currentUserName);
		//System.out.println("********query" + q.toString());
		List<Timestamp> requests = q.list();
		//if(requests!=null)	System.out.println(requests.size());
		if(requests.size() == 0) return null;
		return requests.get(0);
	}
	
	@Override
	public List<Request> getAllRequestForUser(String currentUserName) {
		Session session = this.sessionFactory.getCurrentSession();
		String hquery = "FROM Request where username is ?";
		Query q = session.createQuery(hquery);
		q.setString(0, currentUserName);
		List<Request> requests = q.list();
		if(requests!=null){
			System.out.println(requests.size());
			return requests;
		}else{
			return null;
		}
	}
}
