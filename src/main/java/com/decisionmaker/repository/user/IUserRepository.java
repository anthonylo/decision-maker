package com.decisionmaker.repository.user;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
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
	
	Set<User> retrieveFriendsById(Long id) throws NotImplementedException;
	
	boolean checkIfUsernameAlreadyExists(String username);
	
	void deleteEntityByUsername(String username) throws EntityDoesNotExistException;
	
	void sendMessage(Long userId, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException;
	
	boolean checkIfUsersAreFriends(Long userId, Long possibleFriendId);
	
	void addFriend(Long userId, Long friendId) 
			throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException;

	void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException;
	
	boolean isUserLoggedIn(Long userId);
	
	void performLogInOrOut(Long userId) throws EntityDoesNotExistException;
	
}