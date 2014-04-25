package com.decision.maker.repository.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.IRepository;

@Service
@Transactional
public interface IUserRepository extends IRepository<User, Long> {

	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	boolean checkIfUsernameAlreadyExists(String username);

	User retrieveRandom();
	
	void deleteEntityByUsername(String username) throws EntityDoesNotExistException;
	
}