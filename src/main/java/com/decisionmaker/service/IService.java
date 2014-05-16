package com.decisionmaker.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;

public interface IService<T, K> {

	String getTargetDatabase();
	
	void saveEntity(T entity) throws DecisionMakerException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	T retrieveEntityById(K id) throws DecisionMakerException, EntityDoesNotExistException;

	List<T> retrieveEntitiesByPageAndCount(int page, int count);
	
	Long retrieveCount();
	
	boolean checkIfEntityExistsById(K id);
	
	void updateEntity(T entity) throws DecisionMakerException, EntityDoesNotExistException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	void deleteEntityById(K id) throws EntityDoesNotExistException, DecisionMakerException;

}
