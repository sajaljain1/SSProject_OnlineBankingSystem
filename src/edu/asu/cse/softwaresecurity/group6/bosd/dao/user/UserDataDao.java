package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;
import java.util.List;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;

public interface UserDataDao {

	public UserData getUserDetails(String username);

	public ArrayList<UserData> getUserProfileDetails(String username);

	public ArrayList<String> getRegularEmpList();

	public int updateProfile(UserData userData);
	
	public String getUserSecurityQuestion(String username);
	
	public String getUserPII(String username);
	
	public boolean processUserSignUp(UserSignUp userSignUp);

	boolean updatePermissionTo(String username, String permissionto);

	List<String> getSystemManagerList();

	List<String> getSystemAdminsList();
	
	public List<UserData> getNewUsersRequiringPIIVerification();

	public void sendUserForPIIVerification(String userName);

	List<UserData> getUsersAwaitingGovtPIIVerification();

	void setUserPIIVerified(String userName);

	void setUserPIIRejected(String username);

	List<UserData> getNewUsersWithPendingInfoRequest();

	public List<UserData> getUsersWithCompletedInfoRequest();
	
	public boolean createAccount(UserData userData);

	public List<UserData> getUsersFromName(String firstName, String lastName);
	
	public boolean createNewUser(String username, String password);
	
	public boolean userExists(String username);

	public List<String> getUsernameByFirstAndLast(String firstname,
			String lastname);
	
	public String getUserByEmail(String email);
}
