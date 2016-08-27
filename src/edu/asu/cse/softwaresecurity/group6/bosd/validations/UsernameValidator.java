package edu.asu.cse.softwaresecurity.group6.bosd.validations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = edu.asu.cse.softwaresecurity.group6.bosd.validations.UsernameValidatorImpl.class)
public @interface UsernameValidator {

	String message() default "Username is not valid";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	int min() default 5;
	int max() default 15;
}
