package com.quickfun.message.repository;

import com.quickfun.message.domain.DummyObject;

public class MockAbstractDecisionMakerRepository extends AbstractDecisionMakerRepository<DummyObject, Long> {

	@Override
	protected void setClazz() {
		this.clazz = DummyObject.class;
	}

}