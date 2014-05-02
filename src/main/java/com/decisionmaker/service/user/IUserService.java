package com.decisionmaker.service.user;

import org.springframework.stereotype.Service;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.service.IService;

@Service
public interface IUserService extends IService<User, Long> {

	void addFriend(Long userId, Long friendId) throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException;
	
	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandomUser() throws NotImplementedException;
	
	User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException;

	boolean checkIfUserExistsByUsername(String username);
	
	boolean checkIfUsersAreFriends(Long userId, Long friendId);
	
	void sendMessage(Long id, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException;

	void deleteUserByUsername(String username) throws EntityDoesNotExistException;

	void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException;
	
}