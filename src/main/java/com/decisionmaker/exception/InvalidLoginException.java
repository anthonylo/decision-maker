package com.decisionmaker.exception;

public class InvalidLoginException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3838064075161675561L;

	public InvalidLoginException() {
		super("You have just made a login attempt that failed. Try again.");
	}
	
	public InvalidLoginException(String reason) {
		super(reason);
	}
	
}
