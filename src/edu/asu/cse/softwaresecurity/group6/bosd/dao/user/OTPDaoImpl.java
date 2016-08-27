package edu.asu.cse.softwaresecurity.group6.bosd.dao.user;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.cse.softwaresecurity.group6.bosd.model.user.OTP;
import edu.asu.cse.softwaresecurity.group6.bosd.utils.OTPStatus;

@Repository
public class OTPDaoImpl implements OTPDao {

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
	public OTP userExists(String username, OTPStatus otpStatus) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from OTP where username = :username AND type = :type");
		query.setParameter("username", username);
		query.setParameter("type", otpStatus.getStatusCode());
		List<OTP> otps = query.list();
		if (otps.size() > 0) {
			return otps.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int createOTP(OTP otp) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(otp);
		return otp.getOtpID();
	}

	@Override
	public int updateOTP(OTP otp) {

		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update OTP set otp = :otp, timestamp = CURRENT_TIMESTAMP, otpused = :otpused"
				+ " where otpid = :otpid");
		query.setParameter("otp", otp.getOtp());
		query.setParameter("otpid", otp.getOtpID());
		query.setParameter("otpused", otp.getOtpused());
		int result = query.executeUpdate();

		return result;
	}
	
	@Override
	public boolean updateOTPToUsed(OTP otp){

		Session session = this.sessionFactory.getCurrentSession();

		Query query = session.createQuery("update OTP set otpused = :otpused"
				+ " where otpid = :otpid");

		query.setParameter("otpid", otp.getOtpID());

		query.setParameter("otpused", 1);
		
		int result = query.executeUpdate();
		
		return true;
	}

}
