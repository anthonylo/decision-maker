package com.decisionmaker.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.AbstractDecisionMakerObject;
import com.decisionmaker.domain.DecisionMakerConstants;

@Service
@Transactional
public abstract class AbstractDecisionMakerService<T extends AbstractDecisionMakerObject<K>, K extends Serializable> 
	implements IService<T, K> {

	@Override
	public String getTargetDatabase() {
		return "The target database is: " + DecisionMakerConstants.TARGET_DATABASE;
	}

	/**
	 * TODO - Implement generic CRUD service methods...
	 */
	
}