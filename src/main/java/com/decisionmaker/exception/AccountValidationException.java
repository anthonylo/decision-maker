package com.decisionmaker.exception;

public class AccountValidationException extends RuntimeException {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3587161873735831218L;

	public AccountValidationException() {
		
	}
	
	public AccountValidationException(String message) {
		super(message);
	}
	
}
