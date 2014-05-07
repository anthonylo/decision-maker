package com.decisionmaker.exception;

public class AlreadyLoggedOutException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2586134148039482971L;

	public AlreadyLoggedOutException() {
		super("Already logged out!");
	}

	public AlreadyLoggedOutException(Long userId) {
		super("User ID " + userId + " is already logged out");
	}
	
	public AlreadyLoggedOutException(String username) {
		super("The user '" + username + "' is already logged out");
	}
	
}