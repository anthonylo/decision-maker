package com.decisionmaker.validator.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.decisionmaker.exception.AccountValidationException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.repository.user.IUserRepository;

@Component
public final class AccountValidator {

	private static IUserRepository userRepository;

	@Autowired
	public void setUserRepository(IUserRepository userRepository) {
		AccountValidator.userRepository = userRepository;
	}
	
	public static void validate(String username, String password) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidLoginException, EntityDoesNotExistException {
		if (StringUtils.isEmpty(username)) {
			throw new AccountValidationException("The username is empty");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountValidationException("The password is empty");
		}
		
		boolean exists = userRepository.checkIfUsernameExists(username);
		if (!exists) {
			throw new EntityDoesNotExistException("The username '" + username + "' is not in our records.");
		}
		
		userRepository.validatePasswordByUsername(username, password);
	}
	
}
