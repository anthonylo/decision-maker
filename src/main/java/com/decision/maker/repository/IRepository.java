package com.decision.maker.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.decision.maker.exception.EntityDoesNotExistException;

@Service
public interface IRepository<T, K> {

	void saveEntity(T entity);
	
	List<T> retrieveById(K id) throws EntityDoesNotExistException;
	
	List<T> retrieveAll();
	
	List<T> retrieveSubsetOfEndpoint(int startIdx, int count);
	
	boolean doesEntityExistById(K id);
	
	Long retrieveCount();
	
	void updateEntity(T entity);
	
	void deleteEntityById(K id) throws EntityDoesNotExistException;
	
}
