package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTP;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;

public interface OTPDao {

		OTP userExists(String username, OTPStatus otpStatus);
		int createOTP(OTP otp);
		int updateOTP(OTP otp);
		public boolean updateOTPToUsed(OTP otp);
}
