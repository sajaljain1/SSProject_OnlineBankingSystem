package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.SysManagerDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.Account;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRegistration;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;

public class SystemManagerService {
	@Autowired
	private SysManagerDao sysManagerDao;

	public SysManagerDao getSysManagerDao() {
		return sysManagerDao;
	}
	
	public void setSysManagerDao(SysManagerDao sysManagerDao) {
		this.sysManagerDao = sysManagerDao;
	}


	public void createUserAccount(UserRegistration sysManager) {
		 sysManagerDao.CreateAccount(sysManager);
	}
		
	
	public void deleteUserAccount(String username){
		 sysManagerDao.deleteUserAccount(username);
	}
	
	
}
