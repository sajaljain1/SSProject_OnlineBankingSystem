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
@Constraint(validatedBy = edu.asu.cse.softwaresecurity.group6.bosd.validations.PasswordValidatorImpl.class)
public @interface PasswordValidator {

	String message() default "Password must be 8 to 15 characters long with atleast one uppercase, lowercase, numeric, special [@,#,$,%] character";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	int min() default 8;
	int max() default 15;
}