package com.decisionmaker.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.decisionmaker.domain.DecisionMakerConstants;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.ContactInfo;
import com.decisionmaker.domain.user.User;

public final class DecisionMakerUtils {
	
	private static enum Gender { MALE, FEMALE };
	
	public static User createAnthony() {
		User user = new User();
		user.setFirstName("Anthony");
		user.setLastName("Lo");
		user.setAge(22);
		
		Account account = new Account();
		account.setAdmin(true);
		account.setUsername("anthony.lo");
		account.setPassword("mypass");
		account.setSecretQuestion("What's the meaning of life?");
		account.setSecretAnswer("42");
		
		ContactInfo ci = new ContactInfo();
		ci.setEmail("anthony.lo@fdmgroup.com");
		ci.setPhoneNumber("2032581172");
		
		user.setAccount(account);
		user.setContactInfo(ci);
		return user;
	}
	
	public static User randomUser() {
		Random rng = new Random();
		Gender gender = (Math.random() < 0.50) ? Gender.MALE : Gender.FEMALE;
		String firstName = (gender == Gender.MALE)
				? DecisionMakerConstants.MALE_NAME_POOL[rng.nextInt(DecisionMakerConstants.MALE_POOL_SIZE)]
				: DecisionMakerConstants.FEMALE_NAME_POOL[rng.nextInt(DecisionMakerConstants.FEMALE_POOL_SIZE)];
		String lastName = DecisionMakerConstants.FAMILY_NAME_POOL[rng.nextInt(DecisionMakerConstants.FAMILY_POOL_SIZE)];

		StringBuilder sb = new StringBuilder();
		sb.append(firstName);
		sb.append(".");
		sb.append(lastName);
		String fullName = sb.toString();
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAge(rng.nextInt(75) + 1);
		user.setAccount(randomAccount(fullName));
		user.setContactInfo(randomContactInfo(fullName));
		
		return user;
	}

	private static ContactInfo randomContactInfo(String fullName) {
		ContactInfo ci = new ContactInfo();
		String username = StringUtils.lowerCase(fullName);
		ci.setEmail(username + "@test.com");
		return ci;
	}
	
	private static Account randomAccount(String fullName) {
		Account account = new Account();
		String username = StringUtils.lowerCase(fullName);
		
		account.setUsername(username);
		account.setPassword("testacc");
		account.setSecretQuestion("fake question");
		account.setSecretAnswer("answer - " + username);
		return account;
	}

}