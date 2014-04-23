package com.quickfun.message.repository.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickfun.message.domain.user.User;
import com.quickfun.message.exception.DecisionMakerException;
import com.quickfun.message.repository.IRepository;

@Service
@Transactional
public interface IUserRepository extends IRepository<User, Long> {

	User retrieveUserByUsername(String username) throws DecisionMakerException;
	
	boolean checkIfUsernameAlreadyExists(String username);

	User retrieveRandom();
	
}