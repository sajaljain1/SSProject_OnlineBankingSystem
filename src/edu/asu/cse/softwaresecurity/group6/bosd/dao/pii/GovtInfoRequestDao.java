package edu.asu.cse.softwaresecurity.group6.bosd.dao.pii;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pii.GovtInfoRequest;

@Repository
public class GovtInfoRequestDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<GovtInfoRequest> getCompletedRequests(){
		Query query = sessionFactory.getCurrentSession().createQuery("from GovtInfoRequest where completed=?");
		query.setParameter(0, true);
		return query.list();
	}
	
	public List<GovtInfoRequest> getNewRequests(){
		Query query = sessionFactory.getCurrentSession().createQuery("from GovtInfoRequest where completed=?");
		query.setParameter(0, false);
		return query.list();
	}
	
	public void addNewRequest(GovtInfoRequest req){
		if(this.getByPII(req.getPii()) == null){
			sessionFactory.getCurrentSession().saveOrUpdate(req);
		}
	}
	
	public GovtInfoRequest getByPII(String pii){
		Query query = sessionFactory.getCurrentSession().createQuery("from GovtInfoRequest where pii=?");
		query.setParameter(0, pii);
		return (GovtInfoRequest) query.uniqueResult();
	}

	public void completeRequest(String pii) {
		GovtInfoRequest req = getByPII(pii);
		req.setCompleted(true);
		sessionFactory.getCurrentSession().saveOrUpdate(req);
	}
}
