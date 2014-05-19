package com.decisionmaker.exception;

public class NotAdministratorException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2718203423927392014L;

	public NotAdministratorException() {
		super();
	}

	public NotAdministratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotAdministratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAdministratorException(String message) {
		super(message);
	}

	public NotAdministratorException(Throwable cause) {
		super(cause);
	}
 
}
