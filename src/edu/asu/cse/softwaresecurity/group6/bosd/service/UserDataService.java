package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;
import java.util.List;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;

public interface UserDataService {

	public UserData getUserDetails(String username);

	public ArrayList<UserData> getUserProfileDetails(String username);

	public ArrayList<String> getRegularEmpList();

	public int updateUserProfile(UserData userData);

	public List<String> getSystemAdminsList();

	public List<String> getSystemManagerList();

	//public boolean updatePermissionTo(String username, String permissionto);
	
	public boolean createAccount(UserData userData);
	
	public boolean addRole(UserRole userRole);

	public boolean isPermittedForAccount(UserData user, String currentUserName);
	
	public List<UserData> getNewUsersRequiringPIIVerification();
	
	public void sendUserForPIIVerification(String userName);
	
	public List<UserData> getUsersAwaitingGovtPIIVerification();
	
	public void setUserPIIVerified(String userName);

	public void setUserPIIRejected(String username);

	public List<UserData> getNewUsersWithPendingInfoRequest();
	
	public List<UserData> getUsersWithCompletedInfoRequest();

	public List<UserData> getUsersFromName(String firstName, String lastName);
	
	public boolean userExists(String username);

	public List<String> getUsernameByFirstAndLast(String firstname,
			String lastname);
	
	public boolean doesEmailExist(String email);
}
