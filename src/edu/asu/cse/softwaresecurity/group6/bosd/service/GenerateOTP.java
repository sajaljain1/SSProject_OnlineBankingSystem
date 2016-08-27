package edu.asu.cse.softwaresecurity.group6.bosd.service;

import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.OTPDao;
import edu.asu.cse.softwaresecurity.group6.bosd.dao.user.UserDataDao;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTP;
import edu.asu.cse.softwaresecurity.group6.bosd.model.user.UserData;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.DateAndTime;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.GenerateRandomKey;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.SendMail;

//import org.apache.tomcat.util.codec.binary.Base64;
public class GenerateOTP {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	
	@Autowired
	private OTPDao otpDao;
	
	@Autowired
	private UserDataDao userDataDao;
	
	public UserDataDao getUserDataDao() {
		return userDataDao;
	}

	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}

	public OTPDao getOtpDao() {
		return otpDao;
	}

	public void setOtpDao(OTPDao otpDao) {
		this.otpDao = otpDao;
	}
	
	@Transactional
	private boolean proceedWithOTPGeneration(String username, OTPStatus otpStatus, UserData userData, OTP otp)
			throws SignatureException, UsernameNotFoundException {

		// CalculateOTP
		String result = calculateOTP(username, GenerateRandomKey.getRandomKey());

		// Update to database
		if (otp != null) {
			otp.setOtp(result);
			otp.setOtpused(0);
			int update = otpDao.updateOTP(otp);
		} else {
			OTP oneTime = new OTP();
			oneTime.setOtp(result);
			oneTime.setType(otpStatus.getStatusCode());
			oneTime.setUsername(username);
			oneTime.setTimeStamp(DateAndTime.getCurrentdateTime());
			oneTime.setOtpused(0);
			int OTPID = otpDao.createOTP(oneTime);
		}

		// Send OTP via email
		Preconditions.checkNotNull(userData);
		SendMail mail = new SendMail();
		mail.sendMailTo(userData.getEmail(), result, "Sending OTP");

		return true;
	}
	
	@Transactional
	private String calculateOTP(String data, String key) throws java.security.SignatureException {
		String result;
		StringBuffer sb = new StringBuffer();
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			for (int i = 0; i < rawHmac.length; i++) {
				sb.append(Integer.toString(((rawHmac[i] & 0xff) + 0x100)));
			}
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		result = sb.toString();
		return result.substring(0, 6);
	}
	
	@Transactional
	public boolean generateOTPForUser(String username, OTPStatus otpStatus) {
		
		// 1. Validate Username
		Assert.notNull(userDataDao);
		System.out.println(username);
		UserData userData = userDataDao.getUserDetails(username);
		if (userData == null) {
			System.out.println("Username does not exist in databse");
			return false;
		} else {
			OTP otp = otpDao.userExists(username, otpStatus);
			if (canGenerateOTP(otp)) {
				try {
					proceedWithOTPGeneration(username, otpStatus, userData, otp);
				} catch (UsernameNotFoundException | SignatureException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	@Transactional
	private boolean canGenerateOTP(OTP otp) {

		if (otp != null) {
			return DateAndTime.isDateTimeOutOfRange(DateAndTime.getDateTimefromString(otp.getTimeStamp()));
		}
		return true;
	}
}
