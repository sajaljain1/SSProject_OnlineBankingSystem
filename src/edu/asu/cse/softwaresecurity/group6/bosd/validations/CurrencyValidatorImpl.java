package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.math.NumberUtils;

public class CurrencyValidatorImpl implements ConstraintValidator<CurrencyValidator, String>{
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		//Match length of username
		return NumberUtils.isNumber(value);
	}

	@Override
	public void initialize(CurrencyValidator constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

}