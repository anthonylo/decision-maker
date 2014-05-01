package com.decision.maker.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.decision.maker.exception.EntityDoesNotExistException;

@Service
public interface IRepository<T, K> {
	
	String getTargetDatabase();

	void saveEntity(T entity);
	
	Set<T> retrieveById(K id) throws EntityDoesNotExistException;
	
	T retrieveUniqueById(K id) throws EntityDoesNotExistException;
	
	List<T> retrieveAll();
	
	List<T> retrieveSubsetOfEndpoint(int startIdx, int count);
	
	boolean doesEntityExistById(K id);
	
	boolean tableEmpty();
	
	Long retrieveCount();
	
	void updateEntity(T entity);
	
	void deleteEntityById(K id) throws EntityDoesNotExistException;
	
}
