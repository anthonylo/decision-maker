package com.decisionmaker.validator.user;

import org.springframework.util.StringUtils;

import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AccountValidationException;

public final class AccountValidator {

	public static Boolean validate(User user, String username, String password) {
		if (StringUtils.isEmpty(username)) {
			throw new AccountValidationException("The username is empty");
		}
		if (StringUtils.isEmpty(password)) {
			throw new AccountValidationException("The password is empty");
		}
		
		Account account = user.getAccount();
		String accUsername = account.getUsername();
		String accPassword = account.getPassword();
		if (!username.equals(accUsername)) {
			throw new AccountValidationException("The username does not exist");
		}
		if (!password.equals(accPassword)) {
			throw new AccountValidationException("The password is incorrect");
		}
		
		return true;
	}
	
}
