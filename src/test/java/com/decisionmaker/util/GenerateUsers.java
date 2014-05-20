package com.decisionmaker.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.service.user.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-applicationContext.xml")
public class GenerateUsers {

	@Autowired
	private IUserService userService;
	
	@Test
	public void should_generate_100_users() throws NoSuchAlgorithmException, InvalidKeySpecException, DecisionMakerException {
		for (int i = 0; i < 100; i++) {
			User user = null;
			try {
				user = DecisionMakerUtils.randomUser();
				userService.saveEntity(user);
			} catch (DecisionMakerException e) {
				user = DecisionMakerUtils.randomUser();
				userService.saveEntity(user);
			}
		}
	}
	
}
