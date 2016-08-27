package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class NameValidatorImpl implements ConstraintValidator<NameValidator, String>{
	
	private int min;
	private int max;
	
	@Override
	public void initialize(NameValidator constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		
		boolean flag = true;
		
		//Match length of name
		if(name.length() < min || name.length() > max) {
			flag = false;
		}
		
		if(!StringUtils.isAlphaSpace(name)) {
			flag =false;
		}
		
		if(StringUtils.startsWith(name, " ")) {
			flag = false;
		}
		
		return flag;
	}

}

