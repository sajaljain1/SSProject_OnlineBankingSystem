package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;

public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public boolean addRole(UserRole userRole) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(userRole);
		return true;
	}

}
