package com.decisionmaker.repository;

import com.decisionmaker.domain.DummyObject;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;

public class MockAbstractDecisionMakerRepository extends AbstractDecisionMakerRepository<DummyObject, Long> {

	@Override
	protected void setClazz() {
		this.clazz = DummyObject.class;
	}

}