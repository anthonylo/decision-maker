package com.decisionmaker.exception;

import java.io.Serializable;


public class EntityDoesNotExistException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -8786520123010552435L;

	private Class<? extends Serializable> idType;
	
	private Serializable id;
	
	public EntityDoesNotExistException() {
		
	}
	
	public EntityDoesNotExistException(String message) {
		super(message);
	}
	
	public EntityDoesNotExistException(String message, Class<? extends Serializable> idType, Serializable id) {
		super(message);
		this.idType = idType;
		this.id = id;
	}

	public Class<? extends Serializable> getIdType() {
		return idType;
	}

	public void setIdType(Class<? extends Serializable> idType) {
		this.idType = idType;
	}

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}
	
}
