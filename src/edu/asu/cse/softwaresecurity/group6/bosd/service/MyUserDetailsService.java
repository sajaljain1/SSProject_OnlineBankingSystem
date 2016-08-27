package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDataDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserRole;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService  {

	//get user from the database, via Hibernate
	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	private UserDataDao userDataDao ;
	
	public UserDataDao getUserDataDao() {
		return userDataDao;
	}

	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) 
		throws UsernameNotFoundException {
	
		edu.asu.cse.softwaresecurity.group6.bosd.model.user.User user = userDao.findByUserName(username.toLowerCase());		
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(edu.asu.cse.softwaresecurity.group6.bosd.model.user.User user, 
		List<GrantedAuthority> authorities) {
		boolean locked = user.isAccountNonLocked()!=0?true:false;
		User builtUser = new User(user.getUsername(), user.getPassword(), 
				user.isEnabled(), true, true, locked , authorities);
		return builtUser;
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}