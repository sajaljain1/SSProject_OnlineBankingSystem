package edu.asu.cse.softwaresecurity.group6.bosd.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDao;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider{
	
	private static final Logger logger = Logger.getLogger(LimitLoginAuthenticationProvider.class);
	
	@Autowired
	UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
		super.setPasswordEncoder(new BCryptPasswordEncoder());
	}
	
	@Transactional
	@Override
	public Authentication authenticate(Authentication authentication) {
		try {
			Authentication auth = super.authenticate(authentication);
			userDao.resetFailAttempts(authentication.getName());
			return auth;
		}
		catch (BadCredentialsException e) {	
			
			//invalid login, update to user_attempts
			userDao.updateFailAttempts(authentication.getName());
			throw e;
		}
		catch (LockedException e){
			
			//this user is locked!
			String error = "";
			edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserAttempts userAttempts = 
	                    userDao.getUserAttempts(authentication.getName());
			
	               if(userAttempts!=null){
				String lastAttempts = userAttempts.getLastModified(); 
				error = "User account is locked! <br /><br />Username : " 
	                           + authentication.getName() + "<br />Last Attempts : " + lastAttempts;
			}else{
				error = e.getMessage();
			}
				
		  throw new LockedException(error);
		}
	}
}