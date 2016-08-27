package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.security.authentication.LockedException;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserAttempts;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", username);
		List<User> users = query.list();	
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean resetPassword(String username, String password) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update User set password = :password, accountNonLocked = :accountNonLocked"
						+ " where username = :username");
		query.setParameter("password", password);
		query.setParameter("accountNonLocked", 1);
		query.setParameter("username", username);
		int result = query.executeUpdate();
		
		if(result == 0) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean updateUserSignUpInfo(UserSignUp userSignUp) {
		
		//1. Update the signup info
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update UserData set pii = :pii, securityquestion = :secQue, securityanswer = :secAns"
						+ " where username = :username");
		query.setParameter("pii", userSignUp.getPII());
		query.setParameter("secQue", userSignUp.getChosenSecurityQuestion());
		query.setParameter("secAns", userSignUp.getSecurityAnswer());
		int result = query.executeUpdate();
		
		if(result == 0) {
			return false;
		}
		
		//2. update user password
		return resetPassword(userSignUp.getUsername(), userSignUp.getPassword());
	}
	
	@Override
	public void updateFailAttempts(String username) {
		UserAttempts userAttempt = getUserAttempts(username);
		if (userAttempt == null) {
			if (isUserExists(username)) {
				Session session = sessionFactory.openSession();
				Query query = session.createSQLQuery("insert into user_attempts(username, attempts) values(?,?)");
				query.setParameter(0, username);
				query.setParameter(1, "1");
				query.executeUpdate();
				session.flush();
				session.close();
				
			}
		} else {
			if (isUserExists(username)) {
				int noOfAttempts = userAttempt.getAttempts();
				noOfAttempts++;
				Session session = sessionFactory.openSession();
				Query query = session.createSQLQuery("update user_attempts set attempts=? where username=?");
				query.setParameter(0, noOfAttempts);
				query.setParameter(1, username);
				if(username.equalsIgnoreCase("admin")){
					noOfAttempts = 0;
					query.setParameter(0, noOfAttempts);
				}
				query.executeUpdate();
				session.flush();
				session.close();
			}
			if (userAttempt.getAttempts() >= 2) {
				Session session = sessionFactory.openSession();
				Query query = session.createSQLQuery("update users set accountNonlocked=? where username=?");
				query.setParameter(0,"0");
				query.setParameter(1, username);
				query.executeUpdate();
				session.flush();
				session.close();
				throw new LockedException("User Account is locked!");
			}
		}
	}

	@Override
	public void resetFailAttempts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update UserAttempts set attempts = 0"
						+ " where username = :username");
		query.setParameter("username", username);
		query.executeUpdate();
		
	}

	public UserAttempts getUserAttempts(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from UserAttempts where username = :username");
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<UserAttempts> userAttempts = query.list();
		if (!userAttempts.isEmpty() && userAttempts.get(0).getAttempts() >= 0) {
			return userAttempts.get(0);
		} else {
			System.out.println("0");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean isUserExists(String username) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where username=?")
				.setParameter(0, username).list();
		System.out.println(users);
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void enableUserLogin(String userName) {
		User user = findByUserName(userName);
		user.setEnabled(true);
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}
	
	public boolean deleteUser(String username) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from User where username = :username");
		query.setParameter("username", username);
		int result = query.executeUpdate();
		
		if(result == 0) {
			return false;
		}
		
		return true;
	}
	public boolean makeAccountInactive(String username){
		Session session = this.sessionFactory.getCurrentSession();
		
		if(!isAccountDeleted(username)){
		Query query = session
				.createQuery("update User set enabled = :enabled"
						+ " where username = :username");
		query.setParameter("enabled", false);
		query.setParameter("username", username);
		int result = query.executeUpdate();
		if(result == 0) {
			return false;
		}
		return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean isUserAccountEnabled(String username){
		
		List<Boolean> users = sessionFactory.getCurrentSession()
				.createQuery("select enabled from User where username=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0).booleanValue();
		} else {
			return false;
		}
	}
		private boolean isAccountDeleted(String username){
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User where username = :username AND enabled = :enabled");
		query.setParameter("username", username);
		query.setParameter("enabled", false);
		List<User> users = query.list();
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	public boolean makeAccountActive(String username){
		Session session = this.sessionFactory.getCurrentSession();
		
		
		Query query = session
				.createQuery("update User set enabled = :enabled"
						+ " where username = :username");
		query.setParameter("enabled", true);
		query.setParameter("username", username);
		int result = query.executeUpdate();
		if(result == 0) {
			return false;
		}
		return true;
		
	}
		
}
