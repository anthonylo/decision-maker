package com.decisionmaker.service.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.service.IService;

@Service
public interface IUserService extends IService<User, Long> {

	void addFriend(Long userId, Long friendId) throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException;
	
	void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException;

	void sendFriendRequest(FriendRequest friendRequest);
	
	User retrieveUserByUsername(String username) throws EntityDoesNotExistException;
	
	User retrieveRandomUser();
	
	User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException;
	
	Set<User> retrieveSimilarUsersByUsername(String username);
	
	boolean checkIfUserExistsByUsername(String username);
	
	boolean checkIfUsersAreFriends(Long userId, Long friendId);
	
	void validatePasswordByUsername(String username, String enteredPassword) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidLoginException;
	
	boolean checkIfUserIsAnAdmin(String username);
	
	void makeUserAdministrator(String username);
	
	void sendMessage(Long id, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException;
	
	void deleteUserByUsername(String username) throws EntityDoesNotExistException;
	
	void logIn(User user) throws EntityDoesNotExistException, AlreadyLoggedInException;
	
	void logOut(User user) throws EntityDoesNotExistException, AlreadyLoggedOutException;

	List<User> getUsersWhoArentFriends(Long id, String name, int page, int count);
	
}