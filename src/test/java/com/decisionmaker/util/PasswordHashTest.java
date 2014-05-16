package com.decisionmaker.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class PasswordHashTest {

	private static Logger log = Logger.getLogger(PasswordHashTest.class);
	
	@Test
	public void hash_a_fake_password() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String passwordToTest = "fakePasssword1";
		String wrongPassword = "fakePassword2";
		String firstHash = PasswordHash.createHash(passwordToTest);
		String secondHash = PasswordHash.createHash(passwordToTest);
		
		if (firstHash.equals(secondHash)) {
			fail("The hashes are equal");
		}
		
		log.info("First Hash: " + firstHash);
		log.info("Second Hash: " + secondHash);
		assertTrue(PasswordHash.validatePassword(passwordToTest, firstHash));
		assertTrue(PasswordHash.validatePassword(passwordToTest, secondHash));
		assertFalse(PasswordHash.validatePassword(wrongPassword, firstHash));
	}
	
	/**
	 * This test came with the PasswordHash class.
	 * It was originally put into the main method.
	 * 
	 * Origin: https://crackstation.net/hashing-security.htm
	 */
	@Test
	public void testBasicFunctionalityOfPasswordHash() {
		try {
			// Print out 10 hashes
			for (int i = 0; i < 10; i++)
				log.info(PasswordHash.createHash("p\r\nassw0Rd!"));

			// Test password validation
			boolean failure = false;
			log.info("Running tests...");
			for (int i = 0; i < 100; i++) {
				String password = "" + i;
				String hash = PasswordHash.createHash(password);
				String secondHash = PasswordHash.createHash(password);
				if (hash.equals(secondHash)) {
					log.info("FAILURE: TWO HASHES ARE EQUAL!");
					failure = true;
				}
				String wrongPassword = "" + (i + 1);
				if (PasswordHash.validatePassword(wrongPassword, hash)) {
					log.info("FAILURE: WRONG PASSWORD ACCEPTED!");
					failure = true;
				}
				if (!PasswordHash.validatePassword(password, hash)) {
					log.info("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
					failure = true;
				}
			}
			if (failure)
				log.info("TESTS FAILED!");
			else
				log.info("TESTS PASSED!");
		} catch (Exception ex) {
			log.info("ERROR: " + ex);
		}
	}

}
