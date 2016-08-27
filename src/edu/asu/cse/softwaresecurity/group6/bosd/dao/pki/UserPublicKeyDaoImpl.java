package edu.asu.cse.softwaresecurity.group6.bosd.dao.pki;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pki.UserPublicKey;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;

@Repository
public class UserPublicKeyDaoImpl implements UserPublicKeyDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public UserPublicKey getPublicKeyForUser(User user) {
		UserPublicKey key = (UserPublicKey) sessionFactory.openSession().createQuery("from UserPublicKey where user=?")
				.setParameter(0, user).uniqueResult();
				return key;
	}

	@Override
	public void addUserPublicKey(UserPublicKey key) {
		sessionFactory.getCurrentSession().saveOrUpdate(key);
	}

	@Override
	public UserPublicKey getPublicKeyForUser(String username) {
		UserPublicKey key = (UserPublicKey) sessionFactory.openSession().createQuery("from UserPublicKey where username=?")
				.setParameter(0, username).uniqueResult();
				return key;
	}

}
