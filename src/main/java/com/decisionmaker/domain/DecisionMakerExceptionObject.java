package com.decisionmaker.domain;

public class DecisionMakerExceptionObject {

	private String message;
	
	public DecisionMakerExceptionObject() {
		
	}
	
	public DecisionMakerExceptionObject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
