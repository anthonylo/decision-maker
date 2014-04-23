package com.quickfun.message.exception;

import com.quickfun.message.domain.DecisionMakerExceptionObject;

public class DecisionMakerException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -7402512086290011082L;

	private DecisionMakerExceptionObject decisionExceptionObject;
	
	public DecisionMakerExceptionObject getDecisionExceptionObject() {
		return decisionExceptionObject;
	}
	
	public void setDecisionExceptionObject(DecisionMakerExceptionObject decisionExceptionObject) {
		this.decisionExceptionObject = decisionExceptionObject;
	}
	
	public DecisionMakerException() {
		super();
	}

	public DecisionMakerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DecisionMakerException(String message, Throwable cause, DecisionMakerExceptionObject decisionExceptionObject) {
		super(message, cause);
		this.decisionExceptionObject = decisionExceptionObject;
	}

	public DecisionMakerException(String message) {
		super(message);
	}
	
	public DecisionMakerException(String message, DecisionMakerExceptionObject decisionExceptionObject) {
		super(message);
		this.decisionExceptionObject = decisionExceptionObject;
	}

	public DecisionMakerException(Throwable cause) {
		super(cause);
	}

}