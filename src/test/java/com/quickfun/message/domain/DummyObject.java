package com.quickfun.message.domain;

public class DummyObject extends AbstractDecisionMakerObject<Long> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -6626923623486555557L;

	private Long id;

	public DummyObject() {

	}

	public DummyObject(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}