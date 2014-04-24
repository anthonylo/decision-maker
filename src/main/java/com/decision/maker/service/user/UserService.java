package com.decision.maker.service.user;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.user.Account;
import com.decision.maker.domain.user.ContactInfo;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.user.IUserRepository;

@Component
@Qualifier("userService")
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	
	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void saveUser(User user) throws DecisionMakerException {
		Account account = user.getAccount();
		if (checkIfUserExistsByUsername(account.getUsername())) {
			throw new DecisionMakerException("The username " + account.getUsername() + " already exists");
		}

		ContactInfo contactInfo = user.getContactInfo();
		if (contactInfo != null
				&& StringUtils.isEmpty(contactInfo.getEmail()) 
				&& StringUtils.isEmpty(contactInfo.getPhoneNumber())) {
			user.setContactInfo(null);
		}
		userRepository.saveEntity(user);
	}

	@Override
	public User retrieveUserById(Long id) throws DecisionMakerException, EntityDoesNotExistException {
		List<User> result = userRepository.retrieveById(id);
		
		if (result.size() > 1) {
			throw new DecisionMakerException("There are too many users with ID " + id);
		}
		
		return result.get(0);
	}

	@Override
	public User retrieveUserByUsername(String username) throws DecisionMakerException {
		return userRepository.retrieveUserByUsername(username);
	}

	@Override
	public List<User> retrieveUsersByPageAndCount(int page, int count) {
		return userRepository.retrieveSubsetOfEndpoint(page*count, count);
	}

	@Override
	public Long retrieveCount() {
		return userRepository.retrieveCount();
	}

	@Override
	public boolean checkIfUserExistsById(Long id) {
		return userRepository.doesEntityExistById(id);
	}
	
	@Override
	public boolean checkIfUserExistsByUsername(String username) {
		return userRepository.checkIfUsernameAlreadyExists(username);
	}

	@Override
	public void updateUser(User user) throws DecisionMakerException {
		if (checkIfUserExistsById(user.getId())) {
			try {
				User checkUser = retrieveUserByUsername(user.getAccount().getUsername());
				if (checkUser.getId() == user.getId()) {
					userRepository.updateEntity(user);
				} else {
					throw new DecisionMakerException("A user already exists with this username");
				}
			} catch (DecisionMakerException e) {
				userRepository.updateEntity(user);
			}
		} else {
			throw new DecisionMakerException("User " + user.getId() + " does not exist");
		}
	}

	@Override	
	public void deleteUserById(Long id) throws EntityDoesNotExistException {
		userRepository.deleteEntityById(id);
	}

	@Override
	public User retrieveRandomUser() {
		return userRepository.retrieveRandom();
	}
	
}