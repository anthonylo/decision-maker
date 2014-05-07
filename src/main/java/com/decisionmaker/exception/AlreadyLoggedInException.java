package com.decisionmaker.exception;

public class AlreadyLoggedInException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6307559052133450100L;

	public AlreadyLoggedInException() {
		super("Already logged in!");
	}

	public AlreadyLoggedInException(Long userId) {
		super("User ID " + userId + " is already logged in");
	}
	
	public AlreadyLoggedInException(String username) {
		super("The user '" + username + "' is already logged in");
	}
	
}