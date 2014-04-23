package com.quickfun.message.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quickfun.message.domain.user.User;
import com.quickfun.message.exception.DecisionMakerException;

@Service
public interface IUserService {

	void saveUser(User user) throws DecisionMakerException;
	
	User retrieveUserById(Long id) throws DecisionMakerException;
	
	User retrieveUserByUsername(String username) throws DecisionMakerException;
	
	User retrieveRandomUser();
	
	List<User> retrieveUsersByPageAndCount(int page, int count);
	
	Long retrieveCount();
	
	boolean checkIfUserExistsById(Long id);
	
	boolean checkIfUserExistsByUsername(String username);

	void updateUser(User user) throws DecisionMakerException;
	
	void deleteUserById(Long id) throws DecisionMakerException;

	
}
