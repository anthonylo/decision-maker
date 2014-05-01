package com.decision.maker.service.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.user.Account;
import com.decision.maker.domain.user.ContactInfo;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.exception.IllegalRecipientException;
import com.decision.maker.exception.NoRecipientsException;
import com.decision.maker.exception.NotImplementedException;
import com.decision.maker.repository.user.IUserRepository;

@Component
@Qualifier("userService")
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public String getTargetDatabase() {
		return userRepository.getTargetDatabase();
	}

	@Override
	public void saveEntity(User user) throws DecisionMakerException {
		Account account = user.getAccount();
		if (checkIfUserExistsByUsername(account.getUsername())) {
			throw new DecisionMakerException("The username " + account.getUsername() + " already exists");
		}

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
	public Long retrieveCount() {
		return userRepository.retrieveCount();
	}

	@Override
	public boolean checkIfEntityExistsById(Long id) {
		return userRepository.doesEntityExistById(id);
	}
	
	@Override
	public boolean checkIfUserExistsByUsername(String username) {
		return userRepository.checkIfUsernameAlreadyExists(username);
	}

	@Override
	public void sendMessage(Long id, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException {
		userRepository.sendMessage(id, message);
	}
	
	@Override
	public void updateEntity(User user) throws DecisionMakerException, EntityDoesNotExistException {
		if (checkIfEntityExistsById(user.getId())) {
			try {
				User checkUser = retrieveUserByUsername(user.getAccount().getUsername());
				if (checkUser.getId().equals(user.getId())) {
					userRepository.updateEntity(user);
				} else {
					throw new DecisionMakerException("A user already exists with the username '" 
							+ user.getAccount().getUsername() + "'.");
				}
			} catch (EntityDoesNotExistException e) {
				userRepository.updateEntity(user);
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
	public User retrieveRandomUser() throws NotImplementedException {
		return userRepository.retrieveRandom();
	}

	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

}