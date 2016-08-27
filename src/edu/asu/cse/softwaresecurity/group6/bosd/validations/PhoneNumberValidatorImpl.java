package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.math.NumberUtils;

public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String>{
	
	private int min;
	private int max;
	
	@Override
	public void initialize(PhoneNumberValidator constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {	
		boolean flag = true;
		
		//Match length of password
		if(phoneNumber.length() < min || phoneNumber.length() > max) {
			flag = false;
		}
		
		//Match conditions of password
		if(!NumberUtils.isDigits(phoneNumber)) {
			flag = false;
		}
		
		return flag;
	}

}
