package com.decisionmaker.exception;

public class IncorrectUserException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 4388162127148355201L;

	public IncorrectUserException() {
		super();
	}

	public IncorrectUserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncorrectUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectUserException(String message) {
		super(message);
	}

	public IncorrectUserException(Throwable cause) {
		super(cause);
	}

}
