package com.decision.maker.service;

import java.util.List;

import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.exception.EntityDoesNotExistException;

public interface IService<T, K> {

	String getTargetDatabase();
	
	void saveEntity(T entity) throws DecisionMakerException;
	
	T retrieveEntityById(K id) throws DecisionMakerException, EntityDoesNotExistException;

	List<T> retrieveEntitiesByPageAndCount(int page, int count);
	
	K retrieveCount();
	
	boolean checkIfEntityExistsById(K id);
	
	void updateEntity(T entity) throws DecisionMakerException, EntityDoesNotExistException;
	
	void deleteEntityById(K id) throws EntityDoesNotExistException, DecisionMakerException;

}
