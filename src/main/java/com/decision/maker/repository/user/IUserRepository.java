package com.decision.maker.repository.user;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.exception.IllegalMessageInsertException;
import com.decision.maker.exception.NoRecipientsException;
import com.decision.maker.exception.NotImplementedException;
import com.decision.maker.repository.IRepository;

@Repository
@Transactional
public interface IUserRepository extends IRepository<User, Long> {

	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandom() throws NotImplementedException;
	
	User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException;
	
	Set<User> retrieveBareboneUsersFromListOfIds(List<Long> ids);
	
	boolean checkIfUsernameAlreadyExists(String username);
	
	void sendMessage(Long userId, Message message)
			throws EntityDoesNotExistException, NoRecipientsException, IllegalMessageInsertException;
	
	void deleteEntityByUsername(String username) throws EntityDoesNotExistException;
	
}