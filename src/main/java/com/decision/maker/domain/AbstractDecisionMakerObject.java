package com.decision.maker.domain;

import java.io.Serializable;

public abstract class AbstractDecisionMakerObject<K extends Serializable> implements Serializable {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -591857531821084864L;

	public abstract K getId();
	
	public abstract void setId(K id);
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);
	
}
