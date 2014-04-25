package com.decision.maker.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.exception.EntityDoesNotExistException;

@Service
public interface IUserService {

	String getTargetDatabase();
	
	void saveUser(User user) throws DecisionMakerException;
	
	User retrieveUserById(Long id) throws DecisionMakerException, EntityDoesNotExistException;
	
	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandomUser();
	
	List<User> retrieveUsersByPageAndCount(int page, int count);
	
	Long retrieveCount();
	
	boolean checkIfUserExistsById(Long id);
	
	boolean checkIfUserExistsByUsername(String username);

	void updateUser(User user) throws DecisionMakerException, EntityDoesNotExistException;
	
	void deleteUserById(Long id) throws EntityDoesNotExistException;

	void deleteUserByUsername(String username) throws EntityDoesNotExistException;

	
}
