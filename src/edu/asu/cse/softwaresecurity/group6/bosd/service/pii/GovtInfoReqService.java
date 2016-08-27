package edu.asu.cse.softwaresecurity.group6.bosd.service.pii;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.pii.GovtInfoRequestDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.pii.GovtInfoRequest;

@Service
public class GovtInfoReqService {
	@Autowired
	private GovtInfoRequestDao govtInfoRequestDao;

	public GovtInfoRequestDao getGovtInfoRequestDao() {
		return govtInfoRequestDao;
	}

	public void setGovtInfoRequestDao(GovtInfoRequestDao govtInfoRequestDao) {
		this.govtInfoRequestDao = govtInfoRequestDao;
	}
	
	@Transactional
	public List<GovtInfoRequest> getCompletedRequests(){
		return govtInfoRequestDao.getCompletedRequests();
	}
	
	@Transactional
	public List<GovtInfoRequest> getNewRequests(){
		return govtInfoRequestDao.getNewRequests();
	}
	
	@Transactional
	public void addNewRequest(GovtInfoRequest req){
		govtInfoRequestDao.addNewRequest(req);
	}
	
	@Transactional
	public GovtInfoRequest getByPII(String pii){
		return govtInfoRequestDao.getByPII(pii);
	}

	@Transactional
	public void completeRequest(String pii) {
		govtInfoRequestDao.completeRequest(pii);
	}
}
