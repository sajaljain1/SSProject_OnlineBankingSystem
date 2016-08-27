package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidatorImpl implements ConstraintValidator<UsernameValidator, String>{
	
	private int min;
	private int max;
	
	@Override
	public void initialize(UsernameValidator constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		
		boolean flag = true;
		
		//Match length of username
		if(username.length() < min || username.length() > max) {
			flag = false;
		}
		
		return flag;
	}

}