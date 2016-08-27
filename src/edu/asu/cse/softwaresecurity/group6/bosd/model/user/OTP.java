package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OTP")
public class OTP implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OTPID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int otpID;
	
	@Column(name="otp")
	private String otp ;
	
	@Column(name="timestamp")
	private String timeStamp;
	
	@Column(name="username")
	private String username;
	
	@Column(name="type")
	private String type ;
	
	@Column(name="otpused")
	private int otpused ;
	
	public int getOtpID() {
		return otpID;
	}

	public void setOtpID(int otpID) {
		this.otpID = otpID;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOtpused() {
		return otpused;
	}

	public void setOtpused(int otpused) {
		this.otpused = otpused;
	}

	@Override
	public String toString() {
		return "OTP [otpID=" + otpID + ", otp=" + otp + ", timeStamp="
				+ timeStamp + ", username=" + username + ", type=" + type
				+ ", otpused=" + otpused + "]";
	}
	
}


