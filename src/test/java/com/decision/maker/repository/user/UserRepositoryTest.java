package com.decision.maker.repository.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.junit.Before;
import org.junit.Test;

import com.decision.maker.domain.user.Account;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;

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
		mockAccount.setUsername(targetUsername);
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
	
	@Test(expected = DecisionMakerException.class)
	public void try_to_retrieve_a_user_by_username_that_doesnt_exist() throws DecisionMakerException {
		// Given
		String targetUsername = "test.user";
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(null);
		
		// Then
		repository.retrieveUserByUsername(targetUsername); // expect exception to be thrown
	}

	@Test
	public void should_return_true_when_checking_if_a_username_exists() throws DecisionMakerException {
		// Given
		String targetUsername = "test.user";
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.setProjection((Projection) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(1L);
		
		// Then
		boolean result = repository.checkIfUsernameAlreadyExists(targetUsername);
		assertTrue(result);
	}
	
	@Test
	public void should_return_false_when_checking_for_a_fake_user() throws DecisionMakerException {
		// Given
		String targetUsername = "test.user";
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.setProjection((Projection) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(0L);
		
		// Then
		boolean result = repository.checkIfUsernameAlreadyExists(targetUsername);
		assertFalse(result);
	}
	
	@Test
	public void should_retrieve_a_random_user() throws DecisionMakerException {
		// Given
		Query query = mock(Query.class);
		String targetUsername = "test.user";
		
		User mockUser = new User();
		mockUser.setId(1L);
		mockUser.setFirstName("test");
		mockUser.setLastName("user");
		mockUser.setAge(22);
		
		Account mockAccount = new Account();
		mockAccount.setId(1L);
		mockAccount.setUsername(targetUsername);
		mockAccount.setPassword("12345");
		mockAccount.setSecretQuestion("hi");
		mockAccount.setSecretAnswer("42");
		
		mockUser.setAccount(mockAccount);
		
		String realHql = "select u from User u order by rand()";

		// When
		when(session.createQuery(realHql)).thenReturn(query);
		when(query.setMaxResults(1)).thenReturn(query);
		when(query.uniqueResult()).thenReturn(mockUser);
		
		// Then
		User result = repository.retrieveRandom();
		assertNotNull(result);
		
		assertEquals(result.getId(), mockUser.getId());
		assertEquals(result.getAccount().getUsername(), mockAccount.getUsername());
		assertNull(result.getContactInfo());
	}
}
