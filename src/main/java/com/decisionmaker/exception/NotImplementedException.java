package com.decisionmaker.exception;

public class NotImplementedException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 8666524947198777687L;

	public NotImplementedException(String methodName) {
		super("This method is not yet implemented: " + methodName);
	}
	
}
