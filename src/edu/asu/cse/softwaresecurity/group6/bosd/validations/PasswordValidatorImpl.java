package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidatorImpl implements ConstraintValidator<PasswordValidator, String>{
	
	private int min;
	private int max;
	
	@Override
	public void initialize(PasswordValidator constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		Pattern passwordRegexPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})");
		boolean flag = true;
		
		//Match length of password
		if(password.length() < min || password.length() > max) {
			flag = false;
		}
		
		//Match conditions of password
		Matcher matcher = passwordRegexPattern.matcher(password);
		if(!matcher.matches()) {
			flag = false;
		}
		
		return flag;
	}

}
