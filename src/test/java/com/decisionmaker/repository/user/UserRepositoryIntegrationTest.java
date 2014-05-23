package com.decisionmaker.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.EntityDoesNotExistException;
//
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(locations = "classpath:/test-applicationContext.xml")
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private IUserRepository userRepository;
	
	private static Logger log = Logger.getLogger(UserRepositoryIntegrationTest.class);
	
	@Test
	public void dummyTest() {
		log.info("This dummy test is put here so there's at least one test in the test class.");
	}
	
//	@Test
	public void should_check_if_users_not_logged_in_log_out() throws EntityDoesNotExistException, AlreadyLoggedInException {
		Long testId = 1L;
		
		Boolean loggedIn = userRepository.isUserLoggedIn(testId);
		log.info("User " + testId + " is logged in: " + loggedIn);
		
//		userRepository.logIn(testId);
		
		loggedIn = userRepository.isUserLoggedIn(testId);
		log.info("User " + testId + " is logged in: " + loggedIn);
	}
	
//	@Test
	public void see_what_happens_when_users_log_in_an_out_a_lot() throws InterruptedException {
		final IUserRepository repository = userRepository;
		final Long count = repository.retrieveCount();
		List<Thread> userThreads = new ArrayList<Thread>(count.intValue());

		while(true) {
			log.info("New loop");
			log.info("----");
			userThreads.clear();
			
			for (int i = 0; i < count.intValue(); i++) {
//				final int idx = i;
				Thread t = new Thread("Thread " + i) {
					@Override
					public void run() {
						try {
							Random gen = new Random();
							int temp = gen.nextInt(count.intValue())+1;
							Long id = Long.valueOf(temp);
							User user = repository.retrieveUniqueById(id);
							
							Boolean loggedIn = userRepository.isUserLoggedIn(id);
							log.info(this.getName() + " is attempting to log in user: " + user.getId());
							userRepository.logIn(user);
							loggedIn = userRepository.isUserLoggedIn(id);
							assert loggedIn;
							log.info(this.getName() + ": User " + id + " is logged in: " + loggedIn);
							
							log.info(this.getName() + " is attempting to log out user: " + user.getId());
							userRepository.logOut(user);
							loggedIn = userRepository.isUserLoggedIn(id);
							assert !loggedIn;
							log.info(this.getName() + " finished log out user: " + user.getId());
							
							log.info("----");
						} catch (EntityDoesNotExistException | AlreadyLoggedInException | AlreadyLoggedOutException e) {
							e.printStackTrace();
						}
					}
				};
				userThreads.add(t);
			}
		
			for (Thread t : userThreads) {
				t.start();
			}
			
			for (Thread t : userThreads) {
				t.join();
			}
		}
	}

}
