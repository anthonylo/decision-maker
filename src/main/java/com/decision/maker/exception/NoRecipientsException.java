package com.decision.maker.exception;

public class NoRecipientsException extends RuntimeException {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 5331259962226094231L;

	public NoRecipientsException() {
		super("There are no recipients for this new message!");
	}
	
}
