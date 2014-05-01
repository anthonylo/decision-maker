package com.decisionmaker.repository.user;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.repository.IRepository;

@Repository
@Transactional
public interface IUserRepository extends IRepository<User, Long> {

	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandom() throws NotImplementedException;
	
	User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException;
	
	Set<User> retrieveBareboneUsersFromListOfIds(List<Long> ids);
	
	boolean checkIfUsernameAlreadyExists(String username);
	
	void sendMessage(Long userId, Message message)
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException;
	
	void deleteEntityByUsername(String username) throws EntityDoesNotExistException;
	
	boolean isUserLoggedIn(Long userId);
	
	void performLogInOrOut(Long userId) throws EntityDoesNotExistException;
	
}