package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRegistration;

public class SysManagerDao {
	
	//boolean createAccount(String username, String firstName, String lastName, String email, int phoneNumber, Date DOB, String address);
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void CreateAccount(UserRegistration sysManager) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(sysManager);
		Query query = session.createQuery("insert into users Request where transactionId = :transId AND username = :username");
	}
	
	//ArrayList<SysManager> fetchUserDetails(String username);
	
	//boolean updateUserAccount(String username);
	
	public void deleteUserAccount(String username){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserData user where user.username = :username");
		session.delete(query);
	}

}
