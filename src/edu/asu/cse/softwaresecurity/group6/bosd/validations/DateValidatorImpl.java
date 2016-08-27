package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateValidatorImpl implements ConstraintValidator<DateValidator, String> {

	private int min;
	private int max;

	@Override
	public void initialize(DateValidator constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {

		boolean flag = true;

		// Match length of name
		if (date.length() < min || date.length() > max) {
			flag = false;
		}
		
		DateTimeFormatter parser = DateTimeFormat.forPattern("MM-dd-YYYY");
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
//		dateFormat.setLenient(false);
		try {
			DateTime dtime = parser.parseDateTime(date);
			if(dtime.isAfter(DateTime.now())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return flag;
	}

}
