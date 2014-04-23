package com.quickfun.message.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quickfun.message.exception.DecisionMakerException;

@Service
public interface IRepository<T, K> {

	void saveEntity(T entity) throws DecisionMakerException;
	
	List<T> retrieveById(K id) throws DecisionMakerException;
	
	List<T> retrieveAll();
	
	List<T> retrieveSubsetOfEndpoint(int startIdx, int count);
	
	boolean doesEntityExistById(K id);
	
	Long retrieveCount();
	
	void updateEntity(T entity);
	
	void deleteEntityById(K id) throws DecisionMakerException;
	
}
