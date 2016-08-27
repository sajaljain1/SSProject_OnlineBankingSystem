package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserAttempts;

public interface UserDao {

	User findByUserName(String username);
	void updateFailAttempts(String username);
	void resetFailAttempts(String username);
	UserAttempts getUserAttempts(String username);
	boolean resetPassword(String username, String password);
	public boolean updateUserSignUpInfo(UserSignUp userSignUp);
	void enableUserLogin(String userName);
	public boolean deleteUser(String username);
	public boolean isUserAccountEnabled(String username);
	public boolean makeAccountInactive(String username);
	public boolean makeAccountActive(String username);
}
