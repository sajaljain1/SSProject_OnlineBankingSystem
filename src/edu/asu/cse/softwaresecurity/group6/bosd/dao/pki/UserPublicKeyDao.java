package edu.asu.cse.softwaresecurity.group6.bosd.dao.pki;

import edu.asu.cse.softwaresecurity.group6.bosd.model.pki.UserPublicKey;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;

public interface UserPublicKeyDao {
	UserPublicKey getPublicKeyForUser(User user);
	UserPublicKey getPublicKeyForUser(String username);
	void addUserPublicKey(UserPublicKey key);
}
