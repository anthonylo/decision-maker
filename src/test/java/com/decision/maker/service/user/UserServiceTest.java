package com.decision.maker.service.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.decision.maker.domain.user.Account;
import com.decision.maker.domain.user.ContactInfo;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.repository.user.IUserRepository;
import com.decision.maker.repository.user.UserRepository;
import com.decision.maker.service.user.UserService;

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
	public void save_user_and_pass() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account(0L, "test.guy", "54321", "bleh", "green");
		ContactInfo mockContactInfo = new ContactInfo(0L, "5421@test.com", "54321");
		User mockUser = new User(0L, "test", "guy" , 22, mockContactInfo, mockAccount);
		
		// When

		// Then
		userService.saveUser(mockUser);
		verify(userRepository).saveEntity(mockUser);
	}

	@Test
	public void try_to_save_user_that_already_exists_in_database() throws DecisionMakerException {
		// Given
		Account mockAccount = new Account(0L, "test.guy", "54321", "bleh", "green");
		User mockUser = new User(0L, "test", "guy" , 22, null, mockAccount);
		
		// When
		when(userRepository.checkIfUsernameAlreadyExists(mockAccount.getUsername())).thenReturn(true);
		
		// Then
		exception.expect(DecisionMakerException.class);
		userService.saveUser(mockUser);
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
		userService.saveUser(mockUser);
		
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
		userService.saveUser(mockUser);
		
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
		userService.saveUser(mockUser);
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
		userService.saveUser(mockUser);
		
		assertNotNull(mockContactInfo);
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
		userService.saveUser(mockUser);
		
		assertFalse(StringUtils.isEmpty(mockContactInfo.getEmail()));
		assertFalse(StringUtils.isEmpty(mockContactInfo.getPhoneNumber()));
	}
	
	@Test
	public void should_retrieve_random_user() {
		// Given
		User mockUser = new User();

		// When
		when(userRepository.retrieveRandom()).thenReturn(mockUser);
		
		// Then
		User user = userService.retrieveRandomUser();
		assertNotNull(user);
		
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
	}
	
	@Test
	public void should_retrieve_user_by_username() {
		
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
	
}
