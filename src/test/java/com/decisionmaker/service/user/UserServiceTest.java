package com.decisionmaker.service.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.ContactInfo;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.repository.user.IUserRepository;
import com.decisionmaker.repository.user.UserRepository;
import com.decisionmaker.service.user.UserService;

public class UserServiceTest {

	private UserService userService;
	
	private IUserRepository userRepository;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		userService = new MockUserService();
		
		userRepository = mock(UserRepository.class);

		userService.setUserRepository(userRepository);
	}
	
	@Test
	public void check_that_the_get_target_database_works() {
		assertNull(userService.getTargetDatabase());
		when(userRepository.getTargetDatabase()).thenReturn("test");
		assertEquals("test", userService.getTargetDatabase());
	}
	
	@Test
	public void save_user_and_pass() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account(0L, "test.guy", "54321", "bleh", "green", new Date(), false);
		ContactInfo mockContactInfo = new ContactInfo(0L, "5421@test.com", "54321");
		User mockUser = new User(0L, "test", "guy" , 22, mockContactInfo, mockAccount, null, null);
		
		// When

		// Then
		userService.saveEntity(mockUser);
		verify(userRepository).saveEntity(mockUser);
	}

	@Test
	public void try_to_save_user_that_already_exists_in_database() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account(0L, "test.guy", "54321", "bleh", "green", new Date(), false);
		User mockUser = new User(0L, "test", "guy" , 22, null, mockAccount, null, null);
		
		// When
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(true);
		
		// Then
		exception.expect(DecisionMakerException.class);
		userService.saveEntity(mockUser);
	}

	@Test
	public void try_to_save_user_that_doesnt_have_contact_info() throws DecisionMakerException {
		// Given
		Account mockAccount = mock(Account.class);
		User mockUser = mock(User.class);
		ContactInfo mockContactInfo = mock(ContactInfo.class);
		
		// When
		when(mockContactInfo.getEmail()).thenReturn("");
		when(mockContactInfo.getPhoneNumber()).thenReturn("");
		
		when(mockAccount.getUsername()).thenReturn("test");
		
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(mockUser.getContactInfo()).thenReturn(mockContactInfo);
		
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(false);
		
		// Then
		userService.saveEntity(mockUser);
		
		verify(mockAccount, times(2)).getUsername();
		verify(mockUser).setContactInfo(null);
	}

	@Test
	public void try_to_save_user_that_has_a_null_contact_info() throws DecisionMakerException {
		// Given
		Account mockAccount = mock(Account.class);
		User mockUser = mock(User.class);
		
		// When
		when(mockAccount.getUsername()).thenReturn("test");
		
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(mockUser.getContactInfo()).thenReturn(null);
		
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(false);
		
		// Then
		userService.saveEntity(mockUser);
		
		verify(mockAccount, times(2)).getUsername();
	}

	@Test
	public void try_to_save_user_that_has_does_not_have_a_null_contact_info_but_no_email_or_phone() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account();
		mockAccount.setUsername("test");
		
		ContactInfo mockContactInfo = new ContactInfo();
		mockContactInfo.setEmail(null);
		mockContactInfo.setPhoneNumber(null);
		
		User mockUser = new User();
		mockUser.setAccount(mockAccount);
		mockUser.setContactInfo(mockContactInfo);
		
		// When
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(false);
		// Then
		userService.saveEntity(mockUser);
		assertNull(mockContactInfo.getEmail());
		assertNull(mockContactInfo.getPhoneNumber());
	}

	@Test
	public void try_to_save_user_that_has_a_contact_info_with_email_but_no_phone() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account();
		mockAccount.setUsername("test");
		
		ContactInfo mockContactInfo = new ContactInfo();
		mockContactInfo.setEmail("test");
		mockContactInfo.setPhoneNumber(null);
		
		User mockUser = new User();
		mockUser.setAccount(mockAccount);
		mockUser.setContactInfo(mockContactInfo);

		// When
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(false);
		
		// Then
		userService.saveEntity(mockUser);

		assertNotNull(mockContactInfo);
		assertNotNull(mockContactInfo.getEmail());
		assertNull(mockContactInfo.getPhoneNumber());
		assertFalse(StringUtils.isEmpty(mockContactInfo.getEmail()));
		assertTrue(StringUtils.isEmpty(mockContactInfo.getPhoneNumber()));
	}
	
	@Test
	public void try_to_save_user_that_has_a_contact_info_with_email_and_phone() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account();
		mockAccount.setUsername("test");
		
		ContactInfo mockContactInfo = new ContactInfo();
		mockContactInfo.setEmail("test");
		mockContactInfo.setPhoneNumber("54321");
		
		User mockUser = new User();
		mockUser.setAccount(mockAccount);
		mockUser.setContactInfo(mockContactInfo);
		
		// When
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(false);
		
		// Then
		userService.saveEntity(mockUser);
		
		assertFalse(StringUtils.isEmpty(mockContactInfo.getEmail()));
		assertFalse(StringUtils.isEmpty(mockContactInfo.getPhoneNumber()));
	}

	@Test
	public void should_retrieve_user_by_id() throws EntityDoesNotExistException, DecisionMakerException {
		// Given
		User user = new User();
		Long targetId = 1L;
		
		// When
		when(userRepository.retrieveUniqueById(targetId)).thenReturn(user);
		
		// Then
		User result = userService.retrieveEntityById(targetId);
		assertNotNull(result);
		assertTrue(result.equals(user));
		
		verify(userRepository).retrieveUniqueById(targetId);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = EntityDoesNotExistException.class)
	public void should_retrieve_no_user_by_id() throws EntityDoesNotExistException, DecisionMakerException {
		// Given
		Long targetId = 1L;
		
		// When
		when(userRepository.retrieveUniqueById(targetId)).thenThrow(EntityDoesNotExistException.class);
		
		// Then
		userService.retrieveEntityById(targetId); 
		
		// Expect exception to be thrown because size > 1
	}

	@Test(expected = EntityDoesNotExistException.class)
	@SuppressWarnings("unchecked")
	public void should_retrieve_user_by_id_that_doesnt_exist() throws EntityDoesNotExistException, DecisionMakerException {
		// Given
		Long targetId = 1L;
		
		// When
		when(userRepository.retrieveUniqueById(targetId)).thenThrow(EntityDoesNotExistException.class);
		
		// Then
		userService.retrieveEntityById(targetId);
		verify(userRepository).retrieveUniqueById(targetId);
	}

	@Test
	public void should_retrieve_user_by_username() throws EntityDoesNotExistException, DecisionMakerException {
		// Given
		String username = "test";
		User user = mock(User.class);
		
		// When
		when(userRepository.retrieveUserByUsername(username)).thenReturn(user);
		
		// Then
		User result = userService.retrieveUserByUsername(username);
		assertNotNull(result);
		assertTrue(result.equals(user));
		
		verify(userRepository).retrieveUserByUsername(username);
	}
	
	@Test(expected = EntityDoesNotExistException.class)
	@SuppressWarnings("unchecked")
	public void should_retrieve_user_by_username_that_doesnt_exist() throws EntityDoesNotExistException, DecisionMakerException {
		// Given
		String username = "test";
		
		// When
		when(userRepository.retrieveUserByUsername(username)).thenThrow(EntityDoesNotExistException.class);
		
		// Then
		userService.retrieveUserByUsername(username);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void should_retrieve_users_from_a_page_and_count() {
		// Given
		List<User> users = mock(List.class);
		int page = 1;
		int count = 25;
		
		// When
		when(userRepository.retrieveSubsetOfEndpoint(page*count, count)).thenReturn(users);
		when(users.size()).thenReturn(1);
		
		// Then
		List<User> results = userService.retrieveEntitiesByPageAndCount(page, count);
		assertEquals(results, users);
		assertTrue(results.size() == 1);
	}
	
	@Test(expected = NotImplementedException.class)
	public void should_retrieve_random_user() throws NotImplementedException {
		// When
		when(userRepository.retrieveRandom()).thenCallRealMethod();
		
		// Then
		userService.retrieveRandomUser();
		
		verify(userRepository).retrieveRandom();
	}
	
	@Test
	public void should_retrieve_user_count() {
		// Given
		Long count = 2L;
		
		// When
		when(userRepository.retrieveCount()).thenReturn(count);
		
		// Then
		Long result = userService.retrieveCount();
		
		assertNotNull(result);
		assertEquals(result, count);
		verify(userRepository).retrieveCount();
	}

	@Test
	public void check_if_user_exists_by_id_true() {
		// Given
		Long id = 1L;
		boolean result = true;
		
		// When
		when(userRepository.doesEntityExistById(id)).thenReturn(result);
		
		// Then
		assertTrue(userService.checkIfEntityExistsById(id));
		verify(userRepository).doesEntityExistById(id);
	}
	
	@Test
	public void check_if_user_exists_by_id_false() {
		// Given
		Long id = 1L;
		boolean result = false;
		
		// When
		when(userRepository.doesEntityExistById(id)).thenReturn(result);
		
		// Then
		assertFalse(userService.checkIfEntityExistsById(id));
		verify(userRepository).doesEntityExistById(id);
	}

	@Test
	public void check_if_user_exists_by_username() {
		// Given
		String username = "test.guy";
		
		// When
		when(userRepository.checkIfUsernameAlreadyExists(username)).thenReturn(true);
		
		// Then
		assertTrue(userService.checkIfUserExistsByUsername(username));
	}
	
	@Test
	public void try_to_update_user_that_exists() throws DecisionMakerException, EntityDoesNotExistException {
		// Given
		User mockUser = mock(User.class);
		Account mockAccount = mock(Account.class);
		String username = "fake";
		Long id = 1L;
		boolean result = true;
		
		// When
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockUser.getId()).thenReturn(id);
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(userRepository.doesEntityExistById(mockUser.getId())).thenReturn(result);
		when(userRepository.retrieveUserByUsername(username)).thenReturn(mockUser);
		
		// Then
		userService.updateEntity(mockUser);
		
		verify(userRepository).doesEntityExistById(id);
		verify(userRepository).retrieveUserByUsername(username);
		verify(mockUser, times(4)).getId();
		
		verify(userRepository).updateEntity(mockUser);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void try_to_update_user_with_a_username_that_doesnt_exist() throws DecisionMakerException, EntityDoesNotExistException {
		// Given
		User mockUser = mock(User.class);
		Account mockAccount = mock(Account.class);
		String username = "fake";
		Long id = 1L;
		boolean result = true;
		
		// When
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockUser.getId()).thenReturn(id);
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(userRepository.doesEntityExistById(mockUser.getId())).thenReturn(result);
		when(userRepository.retrieveUserByUsername(username)).thenThrow(EntityDoesNotExistException.class);
		
		// Then
		userService.updateEntity(mockUser);
		
		verify(userRepository).doesEntityExistById(id);
		verify(userRepository).retrieveUserByUsername(username);
		verify(mockUser, times(2)).getId();
		
		verify(userRepository).updateEntity(mockUser);
	}
	
	@Test(expected = DecisionMakerException.class)
	public void try_to_update_user_with_a_username_that_already_exists() throws DecisionMakerException, EntityDoesNotExistException {
		// Given
		User mockUser = mock(User.class);
		User fakeUser = mock(User.class);
		Account mockAccount = mock(Account.class);
		String username = "fake";
		Long id = 1L;
		Long fakeId = 2L;
		boolean result = true;
		
		// When
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(mockUser.getId()).thenReturn(id);
		when(fakeUser.getId()).thenReturn(fakeId);
		when(userRepository.doesEntityExistById(mockUser.getId())).thenReturn(result);
		when(userRepository.retrieveUserByUsername(username)).thenReturn(fakeUser);
		
		// Then
		userService.updateEntity(mockUser);
		
		verify(userRepository).doesEntityExistById(id);
		verify(userRepository).retrieveUserByUsername(username);
		verify(mockUser, times(3)).getId();
		
		verify(userRepository).updateEntity(mockUser);
	}
	
	@Test
	public void try_to_update_user_that_doesnt_exist() throws DecisionMakerException, EntityDoesNotExistException {
		// Given
		User mockUser = mock(User.class);
		Account mockAccount = mock(Account.class);
		ContactInfo mockContact = mock(ContactInfo.class);
		String username = "fake";
		Long id = 1L;
		boolean result = false;
		
		// When
		when(mockAccount.getUsername()).thenReturn(username);
		when(mockUser.getId()).thenReturn(id);
		when(mockUser.getAccount()).thenReturn(mockAccount);
		when(mockContact.getId()).thenReturn(id);
		when(mockUser.getContactInfo()).thenReturn(mockContact);
		when(userRepository.doesEntityExistById(mockUser.getId())).thenReturn(result);
		when(userRepository.checkIfUsernameAlreadyExists(username)).thenReturn(result);
		
		// Then
		userService.updateEntity(mockUser);
		
		verify(userRepository).doesEntityExistById(id);
		verify(mockAccount).getUsername();
		verify(mockContact).setId(null);
		verify(mockContact).getEmail();
		verify(mockContact).getPhoneNumber();
		
		verify(mockUser).setContactInfo(null);
		verify(mockAccount).setId(null);
		verify(mockUser).setId(null);
		
		verify(userRepository).saveEntity(mockUser);
	}

	@Test
	public void should_delete_user_by_id() throws EntityDoesNotExistException {
		// Given
		Long id = 1L;

		// Then
		userService.deleteEntityById(id);
		verify(userRepository).deleteEntityById(id);
	}
	
	@Test
	public void should_delete_user_by_username() throws EntityDoesNotExistException {
		// Given
		String username = "fake";
		
		// Then
		userService.deleteUserByUsername(username);
		verify(userRepository).deleteEntityByUsername(username);
	}
	
}