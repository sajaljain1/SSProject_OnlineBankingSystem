package edu.asu.cse.softwaresecurity.group6.bosd.errorhandling;

import java.security.SignatureException;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseErrorHandler {
	
	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException(DataAccessException ex) {
		System.out.println(ex.getMessage());
		return "error";
	}
	
	@ExceptionHandler(SignatureException.class)
	public String handleSignatureException(SignatureException ex) {
		System.out.println(ex.getMessage());
		return "error";
	}
}
