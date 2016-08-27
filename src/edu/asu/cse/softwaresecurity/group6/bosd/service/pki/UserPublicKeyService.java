package edu.asu.cse.softwaresecurity.group6.bosd.service.pki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.pki.UserPublicKeyDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.pki.UserPublicKey;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;

@Service
public class UserPublicKeyService {
	@Autowired
	private UserPublicKeyDao userPublicKeyDao;

	public UserPublicKeyDao getUserPublicKeyDao() {
		return userPublicKeyDao;
	}

	public void setUserPublicKeyDao(UserPublicKeyDao userPublicKeyDao) {
		this.userPublicKeyDao = userPublicKeyDao;
	} 
	
	@Transactional
	public UserPublicKey getPublicKeyForUser(User user){
		return userPublicKeyDao.getPublicKeyForUser(user);
	}
	
	@Transactional
	public UserPublicKey getPublicKeyForUser(String username){
		return userPublicKeyDao.getPublicKeyForUser(username);
	}
	
	@Transactional
	public void addUserPublicKey(UserPublicKey key) {
		userPublicKeyDao.addUserPublicKey(key);
	}
	
}
