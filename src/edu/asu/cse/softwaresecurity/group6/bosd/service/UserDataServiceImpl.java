package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDataDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserRoleDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

@Service
public class UserDataServiceImpl implements UserDataService {
	@Autowired
	private UserDataDao userDataDao;
	
	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	private UserRoleDao userRoleDao;

	
	public UserDataDao getUserDataDao() {
		return userDataDao;
	}

	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}

	@Override
	@Transactional
	public UserData getUserDetails(String username) {
		return userDataDao.getUserDetails(username);
	}

	@Override
	@Transactional
	public ArrayList<String> getRegularEmpList() {
		return userDataDao.getRegularEmpList();
	}

	@Override
	@Transactional
	public ArrayList<UserData> getUserProfileDetails(String username) {
		return userDataDao.getUserProfileDetails(username);
	}

	@Override
	@Transactional
	public int updateUserProfile(UserData userData) {
		return userDataDao.updateProfile(userData);

	}

	@Override
	@Transactional
	public List<String> getSystemAdminsList() {
		return userDataDao.getSystemAdminsList();
		
	}
	
	public boolean createAccount(UserData userData) {
		 return userDataDao.createAccount(userData);
	}
	
	public boolean addRole(UserRole userRole) {
		 return userRoleDao.addRole(userRole);
	}

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	@Transactional
	public List<String> getSystemManagerList() {
		return userDataDao.getSystemManagerList();
		
	}

//	@Override
//	public boolean updatePermissionTo(String username, String permissionto) {
//		return userDataDao.updatePermissionTo(username, permissionto);
//	}

	@Override
	@Transactional
	public boolean isPermittedForAccount(UserData user, String currentUserName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@Transactional
	public List<UserData> getNewUsersRequiringPIIVerification() {
		return userDataDao.getNewUsersRequiringPIIVerification();
	}

	@Override
	@Transactional
	public void sendUserForPIIVerification(String userName) {
		userDataDao.sendUserForPIIVerification(userName);
	}

	@Override
	@Transactional
	public List<UserData> getUsersAwaitingGovtPIIVerification() {
		return userDataDao.getUsersAwaitingGovtPIIVerification();
	}

	@Override
	@Transactional
	public void setUserPIIVerified(String userName) {
		userDataDao.setUserPIIVerified(userName);
		userDao.enableUserLogin(userName);
	}

	@Override
	@Transactional
	public void setUserPIIRejected(String userName) {
		userDataDao.setUserPIIRejected(userName);
//		userDao.deleteUser(userName);
	}

	@Override
	public List<UserData> getNewUsersWithPendingInfoRequest() {
		return userDataDao.getNewUsersWithPendingInfoRequest();
	}

	@Override
	public List<UserData> getUsersWithCompletedInfoRequest() {
		return userDataDao.getUsersWithCompletedInfoRequest();
	}
	
	@Override
	public boolean userExists(String username) {
		return userDataDao.userExists(username);
	}

	@Override
	public List<UserData> getUsersFromName(String firstName, String lastName) {
		return userDataDao.getUsersFromName(firstName, lastName);
	}
	
	@Override
	public List<String> getUsernameByFirstAndLast(String firstname, String lastname) {
		// TODO Auto-generated method stub
		return userDataDao.getUsernameByFirstAndLast(firstname,lastname);
	}
	
	@Override
	public boolean doesEmailExist(String email) {
		
		String email_new = userDataDao.getUserByEmail(email);
		
		if(StringUtils.equalsIgnoreCase(UtilMessages.EMPTY,email_new)) {
			return false;
		}
		return true;
	}

}
