package com.quickfun.message.repository.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;

import com.quickfun.message.domain.user.Account;
import com.quickfun.message.domain.user.User;
import com.quickfun.message.exception.DecisionMakerException;

public class UserRepositoryTest {

	private MockUserRepository repository;
	
	private SessionFactory sessionFactory;
	private Session session;
	private Criteria criteria;
	
	@Before
	public void setUp() {
		repository = new MockUserRepository();
		
		sessionFactory = mock(SessionFactory.class);
		session = mock(Session.class);
		criteria = mock(Criteria.class);
		
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(User.class)).thenReturn(criteria);
		
		repository.setSessionFactory(sessionFactory);
	}
	
	@Test
	public void should_retrieve_a_user_by_username() throws DecisionMakerException {
		// Given
		String targetUsername = "test.user";
		
		User mockUser = new User();
		mockUser.setId(1L);
		mockUser.setFirstName("test");
		mockUser.setLastName("user");
		mockUser.setAge(22);
		
		Account mockAccount = new Account();
		mockAccount.setId(1L);
		mockAccount.setUsername("test.user");
		mockAccount.setPassword("12345");
		mockAccount.setSecretQuestion("hi");
		mockAccount.setSecretAnswer("42");
		
		mockUser.setAccount(mockAccount);
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(mockUser);
		
		// Then
		User result = repository.retrieveUserByUsername(targetUsername);
		assertNotNull(result);
		
		assertEquals(result.getId(), mockUser.getId());
		assertEquals(result.getAccount().getUsername(), mockAccount.getUsername());
		assertNull(result.getContactInfo());
	}
	
}
