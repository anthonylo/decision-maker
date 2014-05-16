package com.decisionmaker.validator.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.decisionmaker.exception.AccountValidationException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.repository.user.IUserRepository;

public final class AccountValidator {

	@Autowired
	private static IUserRepository userRepository;
	
	public static void validate(String username, String password) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidLoginException {
		if (StringUtils.isEmpty(username)) {
			throw new AccountValidationException("The username is empty");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountValidationException("The password is empty");
		}
		
		userRepository.validatePasswordByUsername(username, password);
	}
	
}
