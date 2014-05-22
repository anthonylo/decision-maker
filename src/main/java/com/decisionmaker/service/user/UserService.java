package com.decisionmaker.service.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.ContactInfo;
import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.repository.user.IFriendRequestRepository;
import com.decisionmaker.repository.user.IUserRepository;
import com.decisionmaker.service.AbstractDecisionMakerService;
import com.decisionmaker.util.PasswordHash;

@Component
@Qualifier("userService")
@Transactional
public class UserService extends AbstractDecisionMakerService<User, Long> implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IFriendRequestRepository friendRequestRepository;
	
	@Override
	public void saveEntity(User user) throws DecisionMakerException, NoSuchAlgorithmException, InvalidKeySpecException {
		Account account = user.getAccount();
		if (checkIfUserExistsByUsername(account.getUsername())) {
			throw new DecisionMakerException("The username " + account.getUsername() + " already exists");
		}
		String hashedPassword = PasswordHash.createHash(account.getPassword());
		account.setPassword(hashedPassword);
		
		ContactInfo contactInfo = user.getContactInfo();
		if (contactInfo != null) {
			contactInfo.setId(null);
			if(StringUtils.isEmpty(contactInfo.getEmail()) 
				&& StringUtils.isEmpty(contactInfo.getPhoneNumber())) {
				user.setContactInfo(null);
			}
		}
		account.setId(null);
		user.setId(null);
		userRepository.saveEntity(user);
	}

	@Override
	public void addFriend(Long userId, Long friendId) throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException {
		userRepository.addFriend(userId, friendId);
	}
	
	@Override
	public User retrieveEntityById(Long id) throws DecisionMakerException, EntityDoesNotExistException {
		return userRepository.retrieveUniqueById(id);
	}

	@Override
	public User retrieveUserByUsername(String username) throws EntityDoesNotExistException {
		return userRepository.retrieveUserByUsername(username);
	}

	@Override
	public List<User> retrieveEntitiesByPageAndCount(int page, int count) {
		return userRepository.retrieveSubsetOfEndpoint(page*count, count);
	}

	@Override
	public User retrieveBareboneUserById(Long id) throws EntityDoesNotExistException {
		return userRepository.retrieveBareboneUserById(id);
	}
	
	@Override
	public Set<User> retrieveSimilarUsersByUsername(String username) {
		return userRepository.retrieveSimilarUsersByUsername(username);
	}
	
	@Override
	public Long retrieveCount() {
		return userRepository.retrieveCount();
	}

	@Override
	public boolean checkIfEntityExistsById(Long id) {
		return userRepository.doesEntityExistById(id);
	}
	
	@Override
	public boolean checkIfUserExistsByUsername(String username) {
		return userRepository.checkIfUsernameExists(username);
	}
	
	@Override
	public boolean checkIfUsersAreFriends(Long userId, Long friendId) {
		return userRepository.checkIfUsersAreFriends(userId, friendId);
	}
	
	@Override
	public void validatePasswordByUsername(String username, String enteredPassword) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidLoginException {
		userRepository.validatePasswordByUsername(username, enteredPassword);
	}

	@Override
	public void sendMessage(Long id, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException {
		userRepository.sendMessage(id, message);
	}
	
	@Override
	public void updateEntity(User user) throws DecisionMakerException, EntityDoesNotExistException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (checkIfEntityExistsById(user.getId())) {
			User checkUser = null; 
			try {
				checkUser = retrieveUserByUsername(user.getAccount().getUsername());
			} catch (EntityDoesNotExistException e) {
				checkUser = retrieveEntityById(user.getId());
			}
			if (checkUser.getId().equals(user.getId())) {
				if (user.getAccount().getPassword() == null) {
					user.getAccount().setPassword(
						checkUser.getAccount().getPassword()
					);
				}
				if (user.getMessagesReceived() == null) {
					user.setMessagesReceived(checkUser.getMessagesReceived());
				}
				if (user.getMessagesSent() == null) {
					user.setMessagesSent(checkUser.getMessagesSent());
				}
				userRepository.updateEntity(user);
			} else {
				throw new DecisionMakerException("A user already exists with the username '" 
						+ user.getAccount().getUsername() + "'.");
			}
		} else {
			saveEntity(user);
		}
	}
	
	@Override
	public void deleteEntityById(Long id) throws EntityDoesNotExistException {
		userRepository.deleteEntityById(id);
	}

	@Override
	public void deleteUserByUsername(String username) throws EntityDoesNotExistException {
		userRepository.deleteEntityByUsername(username);
	}
	
	@Override
	public void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException {
		userRepository.removeFriend(userId, friendId);
	}
	
	@Override
	public User retrieveRandomUser() {
		return userRepository.retrieveRandom();
	}

	@Override
	public void logOut(User user) throws EntityDoesNotExistException,
			AlreadyLoggedOutException {
		userRepository.logOut(user);
	}
	
	@Override
	public void logIn(User user) throws EntityDoesNotExistException, AlreadyLoggedInException {
		userRepository.logIn(user);
	}

	@Override
	public void makeUserAdministrator(String username) {
		userRepository.giveAdminPrivileges(username);
	}

	@Override
	public boolean checkIfUserIsAnAdmin(String username) {
		return userRepository.isUserAdmin(username);
	}
	
	@Override
	public void sendFriendRequest(FriendRequest friendRequest) {
		friendRequestRepository.saveEntity(friendRequest);
	}
	
	@Override
	public void cancelFriendRequest(Long userId, Long cancelId) throws EntityDoesNotExistException {
		userRepository.cancelFriendRequest(userId, cancelId);
	}

	@Override
	public List<User> getUsersWhoArentFriends(Long id, String name, int page, int count) {
		return userRepository.getUsersWhoArentFriends(id, name, page*count, count);
	}
	
	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}