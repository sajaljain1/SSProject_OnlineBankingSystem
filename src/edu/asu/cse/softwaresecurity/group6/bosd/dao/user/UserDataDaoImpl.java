package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

public class UserDataDaoImpl implements UserDataDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserData getUserDetails(String username) {
		List<UserData> trans = new ArrayList<UserData>();
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserData user where user.username = :username");
		query.setParameter("username", username);
		trans = query.list();
		
		if (trans.size() == 0) {
			return null;
		} 
		return trans.get(0);
	}

	@Override
	public ArrayList<UserData> getUserProfileDetails(String username) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserData where username = ?";
		@SuppressWarnings("unchecked")
		List<UserData> userList = session.createQuery(query).setParameter(0, username).list();
		return (ArrayList<UserData>) userList;
	}
	
	@Override
	public boolean userExists(String username) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select username from UserData where username = ?";
		@SuppressWarnings("unchecked")
		List<String> userList = session.createQuery(query).setParameter(0, username).list();
		
		if(userList.size() == 0) return false;
		return true;
	}

	@Override
	public ArrayList<String> getRegularEmpList() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select ud.username from UserData ud inner join "
						+ "user_roles ur on (ud.username = ur.username) where ur.role = :role ;");
		query.setParameter("role", "ROLE_REGULAR_EMP");
		@SuppressWarnings("unchecked")
		List<String> rEmpList = query.list();
		return (ArrayList<String>) rEmpList;

	}

	@Override
	public int updateProfile(UserData userData) {
		Session session = this.sessionFactory.getCurrentSession();
		 Query query = session.createQuery("update UserData set firstname = :firstName ," + " lastname = :lastName ,"
		 		+ " address = :address ," + " email = :email ," + " contact = :contact" + " where username = :userName");
		 query.setParameter("firstName", userData.getFirstname());
		 query.setParameter("lastName", userData.getLastname());
		 query.setParameter("address", userData.getAddress());
		 query.setParameter("email", userData.getEmail());
		 query.setParameter("contact", userData.getContact());
		
		 query.setParameter("userName", userData.getUsername());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public List<String> getSystemAdminsList() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select ud.username from UserData ud inner join "
						+ "user_roles ur on (ud.username = ur.username) where ur.role = :role ;");
		query.setParameter("role", "ROLE_ADMIN");
		@SuppressWarnings("unchecked")
		List<String> adminList = query.list();
		return (ArrayList<String>) adminList;
	}

	@Override
	public List<String> getSystemManagerList() {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("select ud.username from UserData ud inner join "
						+ "user_roles ur on (ud.username = ur.username) where ur.role = :role ;");
		query.setParameter("role", "ROLE_SYS_MANAGER");
		@SuppressWarnings("unchecked")
		List<String> manList = query.list();
		return (ArrayList<String>) manList;
		
	}

	@Override
	public boolean updatePermissionTo(String username, String permissionto) {
		Session session = this.sessionFactory.getCurrentSession();
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		@SuppressWarnings("unused")
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		String hql = "update UserData set permitted = :permissionto where username = :username"; 
		Query q = session.createSQLQuery(hql);
		q.setParameter("permissionto", permissionto);
		q.setParameter("username", username);
		int result = q.executeUpdate();
		if(result == 0) return false;
		return true;
	}
	
	@Override
	public String getUserSecurityQuestion(String username) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select user.securityquestion from UserData user where user.username = :username");
		query.setParameter("username", username);
		List<String> securityQues = query.list();
		
		if (securityQues.size() == 0) {
			return UtilMessages.NONE;
		} 
		return securityQues.get(0);
	}
	
	@Override
	public String getUserPII(String username) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select user.pii from UserData user where user.username = :username");
		query.setParameter("username", username);
		List<String> piiInfo = query.list();
		
		if (piiInfo.size() == 0) {
			return UtilMessages.EMPTY;
		} 
		return piiInfo.get(0);
	}

	@Override
	public boolean processUserSignUp(UserSignUp userSignUp) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserData set pii = :pii, pii_status = :pii_status, securityquestion = :secQues, securityanswer = :secAns" + " where username = :username");
		query.setParameter("pii", userSignUp.getPII());
		query.setParameter("pii_status", UtilMessages.PII_APPROVAL_REQUIRED);
		query.setParameter("secQues", userSignUp.getChosenSecurityQuestion());
		query.setParameter("secAns", userSignUp.getSecurityAnswer());
		query.setParameter("username", userSignUp.getUsername());
		int result = query.executeUpdate();
		
		if(result == 0) {
			return false;
		}
		return true;
	}
	
	public boolean createAccount(UserData userData) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(userData);
		return true;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserData> getNewUsersRequiringPIIVerification() {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserData where pii_status=?");
		query.setParameter(0, UtilMessages.PII_APPROVAL_REQUIRED);
		List<UserData> newAccounts = query.list(); 
		return newAccounts;
	}

	@Override
	public void sendUserForPIIVerification(String userName) {
		setPIIStatusForUser(userName, UtilMessages.PII_APPROVAL_PENDING);
	}

	@Override
	public List<UserData> getUsersAwaitingGovtPIIVerification() {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserData where pii_status=?");
		query.setParameter(0, UtilMessages.PII_APPROVAL_PENDING);
		List<UserData> newAccounts = query.list(); 
		return newAccounts;
	}
	
	@Override
	public void setUserPIIVerified(String userName) {
		setPIIStatusForUser(userName, UtilMessages.PII_APPROVED);
	}
	
	@Override
	public void setUserPIIRejected(String userName) {
		setPIIStatusForUser(userName, UtilMessages.PII_REJECTED);
	}
	
	private void setPIIStatusForUser(String userName, String status){
		UserData user = getUserDetails(userName);
		user.setPii_status(status);
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public List<UserData> getNewUsersWithPendingInfoRequest() {
		return getUsersWithGetInfoStatus(false);
	}

	@Override
	public List<UserData> getUsersWithCompletedInfoRequest() {
		return getUsersWithGetInfoStatus(true);
	}
	
	private List<UserData> getUsersWithGetInfoStatus(boolean completed){
		Query query = sessionFactory.getCurrentSession().createQuery("from UserData as U , GovtInfoRequest as G where U.pii=G.pii and G.completed =?");
		query.setParameter(0, completed);
		List list = query.list();
		List<UserData> userDataList = new ArrayList<UserData>();
		for(Object o : list){
			Object[] arr= (Object[]) o;
			userDataList.add((UserData)arr[0]);
		}
		return userDataList;
	}
	
	public boolean createNewUser(String username, String password) {

		Session session = this.sessionFactory.getCurrentSession();
		User user = new User();
		user.setAccountNonLocked(1);
		user.setEnabled(false);
		user.setPassword(password);
		user.setUsername(username);
		session.save(user);
		
		return true;
	}

	@Override
	public List<UserData> getUsersFromName(String firstName, String lastName) {
		List<UserData> trans = new ArrayList<UserData>();
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserData user where user.firstname = :firstname and user.lastname = :lastname and user.username <> user.email");
		query.setParameter("firstname", firstName);
		query.setParameter("lastname", lastName);
		return query.list();
	}
	
	@Override
	public List<String> getUsernameByFirstAndLast(String firstname,
			String lastname) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select username from UserData user where firstname = :firstname and lastname = :lastname and username <> email");
		query.setParameter("firstname", firstname);
		query.setParameter("lastname", lastname);
		List<String> usernames = query.list();
		
		if (usernames.size() == 0) {
			return usernames;
		} 
		return usernames;
	}
	
	@Override
	public String getUserByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select email from UserData user where email = :email");
		query.setParameter("email", email);
		List<String> emails = query.list();
		
		if (emails.size() == 0) {
			return UtilMessages.EMPTY;
		} 
		return emails.get(0);
	}

}
