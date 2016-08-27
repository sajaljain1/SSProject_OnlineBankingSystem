package edu.asu.cse.softwaresecurity.group6.bosd.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
public class Authorization {
	
	
	@Pointcut("execution(void edu.asu.cse.softwaresecurity.group6.bosd.model.user.ExternalUserImpl.debitFunds())")
	public void doDebitCheck() {
		/// edu.asu.cse.softwaresecurity.group6.bosd.model.user.ExternalUserImpl.debitFunds()
	}
//
//	@Pointcut("execution(void edu.cse.softwaresecurity.group6.bosd.model.user.ExternalUserImpl.creditFunds())")
//	public void doCreditCheck() {
//
//	}
//
//	@Pointcut("execution(void edu.cse.softwaresecurity.group6.bosd.model.ExternalUserImpl.submitIndividualPayment())")
//	public void doIndividualPaymentCheck() {
//
//	}
//
	@Around("doDebitCheck()")
	public void isAuthorizedToDebit(ProceedingJoinPoint jp) {

		// Check for balance.
		System.out.println("Check for the debit possibility");
		Boolean flag = false;
		if (flag) {
			try {
				// Proceed with the Debit job
				jp.proceed();
			} catch (Throwable e) {
				System.out.println("Exception: " + e.getMessage());
			}
		}
		System.out.println("Debit done.");
	}
//
//	@Around("doCreditCheck()")
//	public void isAuthorizedToCrebit(ProceedingJoinPoint jp) {
//
//		// Check if credit transaction is possible
//		Boolean flag = false;
//
//		if (flag) {
//			try {
//				// Proceed with the Debit job
//				jp.proceed();
//			} catch (Throwable e) {
//				System.out.println("Exception: " + e.getMessage());
//			}
//		}
//		System.out.println("Debit done.");
//	}
//	
//	@Around("doIndividualPaymentCheck()")
//	public void isAuthorizedToDoPayment(ProceedingJoinPoint jp) {
//
//		// Check if credit transaction is possible
//		Boolean flag = false;
//
//		if (flag) {
//			try {
//				// Proceed with the Debit job
//				jp.proceed();
//			} catch (Throwable e) {
//				System.out.println("Exception: " + e.getMessage());
//			}
//		}
//		System.out.println("Debit done.");
//	}

	public boolean isAuthorizedToUpdateInformation() {

		return true;
	}

	public boolean isAuthorizedToViewTransactions() {

		return true;
	}

	public boolean isAuthorizedToEditTransactions() {

		return true;
	}

}
