package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import edu.asu.cse.softwaresecurity.group6.bosd.utils.UtilMessages;

public class SecurityQuestionValidatorImpl implements ConstraintValidator<SecurityQuestionValidator, String>{
	
	@Override
	public void initialize(SecurityQuestionValidator constraintAnnotation) {

	}

	@Override
	public boolean isValid(String chosenQuestion, ConstraintValidatorContext context) {
		return !(StringUtils.equalsIgnoreCase(chosenQuestion, UtilMessages.NONE));
	}

}