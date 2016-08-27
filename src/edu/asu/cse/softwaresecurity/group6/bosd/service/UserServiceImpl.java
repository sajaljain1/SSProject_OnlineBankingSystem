package edu.asu.cse.softwaresecurity.group6.bosd.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.OTPDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDataDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.ChangePassword;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTP;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.SecurityQuestions;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.User;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.DateAndTime;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.PasswordHasher;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

/**
 * @author rahulkrsna
 *
 */

@Configuration
@Import(PasswordHasher.class)
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserDataDao userDataDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private OTPDao otpDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserDataDao getUserDataDao() {
		return userDataDao;
	}

	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public OTPDao getOtpDao() {
		return otpDao;
	}

	public void setOtpDao(OTPDao otpDao) {
		this.otpDao = otpDao;
	}

	@Override
	public boolean updateUserPassword(ChangePassword changePassword, OTPStatus otpStatus) {

		boolean status = false;

		OTP otp = otpDao.userExists(changePassword.getUsername(),otpStatus);
		UserData userData = userDataDao.getUserDetails(changePassword.getUsername());
		if (otp != null && userData != null) {
			// 1. Check if the reset_password_sent_at is not expired && it
			// matches the OTP entered by the user.
			DateTime tokenSentAt = DateAndTime.getDateTimefromString(otp.getTimeStamp());
			if (isUserInfoValid(otp, userData, changePassword)
					&& DateAndTime.isDateTimeWithInValidRange(tokenSentAt)) {
				
				// 2. Check the validity of the Sent DateTime of the token.
				userDao.resetPassword(changePassword.getUsername(),
						passwordEncoder.encode(changePassword.getNewPassword()));
				status = true;
			}
			// Update OTPUsed to 1
			otpDao.updateOTPToUsed(otp);
		} else {
			System.out.println("user not found " + changePassword);
		}

		return status;
	}

	@Override
	public boolean updateUserSignUpInfo(UserSignUp userSignUp) {

		userSignUp.setPassword(passwordEncoder.encode(userSignUp.getPassword()));
		boolean status = userDao.updateUserSignUpInfo(userSignUp);
		System.out.println("Status :  " + status);
		return status;
	}

	@Override
	public boolean isThisValidUsername(String username) {

		boolean status = true;
		UserData userData = userDataDao.getUserDetails(username);
		if (userData == null) {
			status = false;
		}
		return status;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.cse.softwaresecurity.group6.bosd.service.UserService#getSecurityQuestionOfUser(java.lang.String)
	 * @Return SecurityQuestion chosen by an user is returned, in case no question is present, "NONE" is returned
	 */
	@Override
	public String getSecurityQuestionOfUser(String username) {
		
		String securityQuestion = userDataDao.getUserSecurityQuestion(username);
		return securityQuestion;
	}
	
	@Override
	public boolean getIsUserAccountEnabled(String username){
		return userDao.isUserAccountEnabled(username);
	}
	
	@Override
	public String getPIIInformationOfUser(String username) {
		
		String piiInformation = userDataDao.getUserPII(username);
		System.out.println("PII " + piiInformation);
		return piiInformation;
	}
	
	@Override
	public boolean processUserSignUp(UserSignUp userSignUp) {
		
		//Change the Security Question from code to actual question
		userSignUp.setChosenSecurityQuestion( SecurityQuestions.getQuestionByCode(userSignUp.getChosenSecurityQuestion()));
		System.out.println("UserSignUp Info: " + userSignUp.getUsername() +" "+ userSignUp.getPII() +userSignUp.getChosenSecurityQuestion() + userSignUp.getSecurityAnswer() + userSignUp.getOTP());
		boolean status = true;
		//Set pii, pii_status, securityQ, securityA
		status =  userDataDao.processUserSignUp(userSignUp);
		//Set pwd
		status = status && userDataDao.createNewUser(userSignUp.getUsername(),passwordEncoder.encode(userSignUp.getPassword()));
		return status;
	}
	
	private boolean isUserInfoValid(OTP otp, UserData userData, ChangePassword changePassword) {

		// OTP, Security Question, Security Answer are matched
		return (StringUtils.equalsIgnoreCase(otp.getOtp(), changePassword.getOTP())
				&& StringUtils.equalsIgnoreCase(userData.getSecurityquestion(),
						changePassword.getChosenSecurityQuestion())
				&& StringUtils.equalsIgnoreCase(userData.getSecurityanswer(),
						changePassword.getSecurityAnswer()) && otp.getOtpused() == 0);
	}
	
	public boolean deleteUser(String username){
		return userDao.deleteUser(username);
	}
	
	@Override
	public boolean checkOTP(String username,String userOTP,OTPStatus otpStatus){
		boolean status = false;

		OTP otp = otpDao.userExists(username,otpStatus);
		UserData userData = userDataDao.getUserDetails(username);
		if (otp != null && userData != null) {
			// 1. Check if the reset_password_sent_at is not expired && it
			// matches the OTP entered by the user.
			DateTime tokenSentAt = DateAndTime.getDateTimefromString(otp.getTimeStamp());
			if (isOtpValid(otp, userOTP)
					&& DateAndTime.isDateTimeWithInValidRange(tokenSentAt)) {
				
				status = true;
			}
			// Update OTPUsed to 1
			otpDao.updateOTPToUsed(otp);
		} else {
			System.out.println("user not found " );
		}
		
		return status;
	}
	
	private boolean isOtpValid(OTP otp, String userOTP){
		return (StringUtils.equalsIgnoreCase(otp.getOtp(), userOTP)&& otp.getOtpused()==0);
		
	}

	@Override
	public boolean makeAccountInactive(String username) {
		return userDao.makeAccountInactive(username);
	}

	@Override
	public boolean makeAccountActive(String username) {
		return userDao.makeAccountActive(username);
	}
}
