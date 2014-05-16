package com.decisionmaker.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.ContactInfo;
import com.decisionmaker.domain.user.User;

public final class DecisionMakerUtil {

	private static String[] maleNamePool = {
		"Anthony", "Bob", "Carl", "David", "Earl", "Frank", "George", "Henry",
		"Ian", "John", "Kory", "Luis", "Matthew", "Nick", "Ox", "Paul", "Quincey",
		"Richard", "Steven", "Troy", "Ulfred", "Vincent", "William"
	};
	private static Integer malePoolSize = maleNamePool.length;
	
	private static String[] femaleNamePool = {
		"Amanda", "Brittney", "Catherine", "Darcy", "Emily", "Francine", "Georgina", "Haley",
		"Ida", "Jamie", "Kelly", "Linda", "Megan", "Nina", "Olga", "Patricia", "Quinn",
		"Rachel", "Sarah", "Tara", "Usagi", "Valentia", "Wendy"
	};
	private static Integer femalePoolSize = femaleNamePool.length;
	
	private static String[] familyNamePool = {
		"Anderson", "Brown", "Clark", "Donohue", "Evans", "Ford", "Green", "Hill", "I",
		"Jones", "King", "Lee", "Matthews", "Nelson", "Olson", "Peterson", "Quintero", "Robinson",
		"Stevens", "Torres", "Williams"
	};
	private static Integer familyPoolSize = familyNamePool.length;
	
	private static enum Gender { MALE, FEMALE };
	
	public static User randomUser() {
		Random rng = new Random();
		Gender gender = (Math.random() < 0.50) ? Gender.MALE : Gender.FEMALE;
		String firstName = (gender == Gender.MALE) 
				? maleNamePool[rng.nextInt(malePoolSize)] 
						: femaleNamePool[rng.nextInt(femalePoolSize)];
		String lastName = familyNamePool[rng.nextInt(familyPoolSize)];

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
		ci.setEmail(fullName + "@test.com");
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
