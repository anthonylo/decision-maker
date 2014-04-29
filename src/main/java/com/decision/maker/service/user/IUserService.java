package com.decision.maker.service.user;

import org.springframework.stereotype.Service;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.service.IService;

@Service
public interface IUserService extends IService<User, Long> {

	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandomUser();
	
	User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException;

	boolean checkIfUserExistsByUsername(String username);
	
	void sendMessage(Long id, Message message) throws EntityDoesNotExistException;

	void deleteUserByUsername(String username) throws EntityDoesNotExistException;
	
}
