package edu.asu.cse.softwaresecurity.group6.bosd.model.user;

import java.util.HashMap;
import java.util.Map;

public class SecurityQuestions {

	public static Map<String,String> getSecurityQuestions() {
		
		Map<String,String> securityQuestions = new HashMap<>();
		
		securityQuestions.put("Q1", "What is your favourite teacher's last name?");
		securityQuestions.put("Q2", "What is your best friends DOB(dd-mm-yyyy)?");
		securityQuestions.put("Q3", "What is the first name of the person you first kissed?");
		securityQuestions.put("Q4", "What is your dream job?");
		securityQuestions.put("Q5", "What is the first name of your favourite aunt?");
		securityQuestions.put("Q6", "What is the frist dish you learned to cook?");
		
		return securityQuestions;
	}
	
	public static String getQuestionByCode(String code) {
		
		return getSecurityQuestions().get(code);
	}
}
