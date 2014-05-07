package com.decisionmaker.repository.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.junit.Before;
import org.junit.Test;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.repository.message.IMessageRepository;

public class UserRepositoryTest {

	private MockUserRepository repository;
	
	private SessionFactory sessionFactory;
	private Session session;
	private Criteria criteria;

	private IMessageRepository messageRepository;
	private IFriendshipRepository friendshipRepository;
	
	@Before
	public void setUp() {
		repository = new MockUserRepository();
		
		sessionFactory = mock(SessionFactory.class);
		session = mock(Session.class);
		criteria = mock(Criteria.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(User.class)).thenReturn(criteria);
		repository.setSessionFactory(sessionFactory);
		
		messageRepository = mock(IMessageRepository.class);
		friendshipRepository = mock(IFriendshipRepository.class);
		repository.setMessageRepository(messageRepository);
		repository.setFriendRepository(friendshipRepository);
	}

	@Test
	public void should_retrieve_unique_user_by_id_that_doesnt_have_messages_or_friends() throws EntityDoesNotExistException {
		// Given
		String targetUsername = "test.user";
		Long id = 1L;
		
		User mockUser = new User();
		mockUser.setId(id);
		mockUser.setFirstName("test");
		mockUser.setLastName("user");
		mockUser.setAge(22);
		
		Account mockAccount = new Account();
		mockAccount.setId(id);
		mockAccount.setUsername(targetUsername);
		mockAccount.setPassword("12345");
		mockAccount.setSecretQuestion("hi");
		mockAccount.setSecretAnswer("42");
		
		mockUser.setAccount(mockAccount);
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(mockUser);
		when(messageRepository.retrieveMessagesThatAUserHasSent(id)).thenReturn(null);
		when(messageRepository.retrieveMessagesThatAUserHasReceived(id)).thenReturn(null);
		
		// Then
		User result = repository.retrieveUniqueById(id);
		assertNotNull(result);
		
		assertEquals(result.getId(), mockUser.getId());
		assertEquals(result.getAccount().getUsername(), mockAccount.getUsername());
		assertNull(result.getContactInfo());
	}

	@Test
	public void should_retrieve_unique_user_by_id_that_has_messages_and_friends() throws EntityDoesNotExistException {
		// Given
		String targetUsername = "test.user";
		Long id = 1L;
		
		User mockUser = new User();
		mockUser.setId(id);
		mockUser.setFirstName("test");
		mockUser.setLastName("user");
		mockUser.setAge(22);
		
		Account mockAccount = new Account();
		mockAccount.setId(id);
		mockAccount.setUsername(targetUsername);
		mockAccount.setPassword("12345");
		mockAccount.setSecretQuestion("hi");
		mockAccount.setSecretAnswer("42");
		
		mockUser.setAccount(mockAccount);
		
		Set<Message> mockMessage = new HashSet<Message>();
		for (int i = 0; i < 5; i++) {
			Message message = new Message();
			message.setId(Long.valueOf(i));
			message.setMessage("test");
			message.setDatePosted(new Date());
			message.setSenderId(Long.valueOf(i));
			mockMessage.add(message);
		}
		
		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(mockUser);
		when(messageRepository.retrieveMessagesThatAUserHasSent(id)).thenReturn(mockMessage);
		when(messageRepository.retrieveMessagesThatAUserHasReceived(id)).thenReturn(mockMessage);
		
		// Then
		User result = repository.retrieveUniqueById(id);
		assertNotNull(result);
		
		assertEquals(result.getId(), mockUser.getId());
		assertEquals(result.getAccount().getUsername(), mockAccount.getUsername());
		assertNull(result.getContactInfo());
	}
	
	@Test
	public void should_retrieve_a_user_by_username() throws EntityDoesNotExistException {
		// Given
		String targetUsername = "test.user";
		Long id = 1L;
		
		User mockUser = new User();
		mockUser.setId(id);
		mockUser.setFirstName("test");
		mockUser.setLastName("user");
		mockUser.setAge(22);
		
		Account mockAccount = new Account();
		mockAccount.setId(id);
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
	
	@Test(expected = EntityDoesNotExistException.class)
	public void try_to_retrieve_a_user_by_username_that_doesnt_exist() throws EntityDoesNotExistException {
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
	
	@Test(expected = NotImplementedException.class)
	public void retrieve_random_user_exception() throws NotImplementedException {
		repository.retrieveRandom();
	}
	
//	@Test -- TODO
	public void should_retrieve_a_random_user() throws DecisionMakerException, NotImplementedException {
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

	@Test(expected = EntityDoesNotExistException.class)
	public void should_delete_a_user_by_username_that_doesnt_exist() throws EntityDoesNotExistException {
		// Given
		String username = "test";

		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(null);
		
		// Then
		repository.deleteEntityByUsername(username);
		verify(criteria).uniqueResult();
	}
	
	@Test
	public void should_delete_a_user_by_username() throws EntityDoesNotExistException {
		// Given
		String username = "test";
		User mockUser = mock(User.class);

		// When
		when(criteria.createAlias("account", "acc")).thenReturn(criteria);
		when(criteria.add((Criterion) anyObject())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(mockUser);
		
		// Then
		repository.deleteEntityByUsername(username);
		verify(criteria).uniqueResult();
		verify(session).delete(mockUser);
	}
}
