package edu.asu.cse.softwaresecurity.group6.bosd.service;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.ChangePassword;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserSignUp;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;

public interface UserService {
	public boolean updateUserPassword(ChangePassword changePassword,  OTPStatus otpStatus);
	public boolean updateUserSignUpInfo(UserSignUp userSignUp);
	public boolean isThisValidUsername(String username);
	public String getSecurityQuestionOfUser(String username);
	public String getPIIInformationOfUser(String username);
	public boolean processUserSignUp(UserSignUp userSignUp);
	public boolean deleteUser(String username);
	public boolean makeAccountInactive(String username);
	public boolean checkOTP(String username,String userOTP,OTPStatus otpStatus);
	public boolean getIsUserAccountEnabled(String username);
		public boolean makeAccountActive(String username);
}
