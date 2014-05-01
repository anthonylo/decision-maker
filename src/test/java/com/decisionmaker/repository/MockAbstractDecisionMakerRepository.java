package com.decision.maker.repository;

import com.decision.maker.domain.DummyObject;
import com.decision.maker.repository.AbstractDecisionMakerRepository;

public class MockAbstractDecisionMakerRepository extends AbstractDecisionMakerRepository<DummyObject, Long> {

	@Override
	protected void setClazz() {
		this.clazz = DummyObject.class;
	}

}