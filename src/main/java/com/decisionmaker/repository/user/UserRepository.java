package com.decisionmaker.repository.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.ContactInfo;
import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.Friendship;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.AlreadyLoggedInException;
import com.decisionmaker.exception.AlreadyLoggedOutException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.InvalidLoginException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.factory.user.FriendshipFactory;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;
import com.decisionmaker.repository.message.IMessageRepository;
import com.decisionmaker.util.PasswordHash;

@Repository
@Transactional
@Qualifier("userRepository")
@SuppressWarnings("unchecked")
public class UserRepository extends AbstractDecisionMakerRepository<User, Long> implements IUserRepository {

	@Autowired
	protected IMessageRepository messageRepository;

	@Autowired
	protected IFriendshipRepository friendshipRepository;
	
	@Autowired
	protected IFriendRequestRepository friendRequestRepository;
	
	@Override
	public Set<User> retrieveById(Long id) throws EntityDoesNotExistException {
		Set<User> result = super.retrieveById(id);
		Iterator<User> resultItr = result.iterator();
		
		while (resultItr.hasNext()) {
			User userItr = resultItr.next();
			handleMessages(userItr);
		}
		
		return result;
	}
	
	@Override
	public User retrieveUniqueById(Long id) throws EntityDoesNotExistException {
		User user = super.retrieveUniqueById(id);
		handleFriends(user);
		handleMessages(user);
		return user;
	}
	
	@Override
	public User retrieveUserByUsername(String username) throws EntityDoesNotExistException {
		User user = (User) sessionFactory.getCurrentSession()
			.createCriteria(clazz)
			.createAlias("account", "acc")
			.add(Restrictions.eq("acc.username", username))
			.uniqueResult();
		
		if (user == null) {
			throw new EntityDoesNotExistException("The user " + username + " does not exist", clazz, user);
		}
		
		handleFriends(user);
		handleMessages(user);
		return user;
	}
	
	@Override
	public Set<User> retrieveSimilarUsersByUsername(String username) {
		List<User> likeUsers = (List<User>) sessionFactory.getCurrentSession().createCriteria(clazz)
			.createAlias("account", "acc")
			.add(Restrictions.like("acc.username", username, MatchMode.ANYWHERE))
			.list();
		return new HashSet<User>(likeUsers);
	}

	@Override
	public Long retrieveIdByUsername(String username) throws EntityDoesNotExistException {
		return (Long) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(Projections.property("id"))
				.uniqueResult();
	}
	
	@Override
	public boolean checkIfUsernameExists(String username) {
		Long count = (Long) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count > 0;
	}

	@Override
	public User retrieveRandom() {
		return (User) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.sqlRestriction("1=1 order by dbms_random.value"))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public User retrieveBareboneUserById(Long id) {
		Object[] objList = (Object[]) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.projectionList()
						.add(Projections.property("firstName"))
						.add(Projections.property("lastName"))
						.add(Projections.property("age"))
						.add(Projections.property("contactInfo"))
					)
				.uniqueResult();

		User user = new User();
		user.setId(id);
		user.setFirstName((String) objList[0]);
		user.setLastName((String) objList[1]);
		user.setAge((Integer) objList[2]);
		user.setContactInfo((ContactInfo) objList[3]);
		
		Account account = retrieveBareboneAccountByUserId(id);
		user.setAccount(account);
		
		return user;
	}

	@Override
	public Set<User> retrieveBareboneUsersFromListOfIds(List<Long> uIds) {
		Set<User> bareboneUsers = new LinkedHashSet<User>();
		for (Long uId : uIds) {
			User bareboneUser = retrieveBareboneUserById(uId);
			bareboneUsers.add(bareboneUser);
		}
		return bareboneUsers;
	}
	
	@Override
	public Set<User> retrieveBareboneUsersFromListOfUsernames(List<String> usernames) throws EntityDoesNotExistException {
		Set<User> bareboneUsers = new LinkedHashSet<User>();
		for (String username : usernames) {
			Long id = retrieveIdByUsername(username);
			User bareboneUser = retrieveBareboneUserById(id);
			bareboneUsers.add(bareboneUser);
		}
		return bareboneUsers;
	}
	
	@Override
	public Set<User> retrieveFriendsById(Long id) throws NotImplementedException {
		throw new NotImplementedException("IUserRepository.retrieveFriendsById");
	}
		
//	private String accountDeleteHql = "delete Account a where a.id = (select u.account.id from User u where u.id = :id)";
//	private String contactInfoDeleteHql = "delete ContactInfo ci where ci.id = (select u.contactInfo.id from User u where u.id = :id)";
	
	@Override
	public void deleteEntityById(Long id) throws EntityDoesNotExistException {
		super.deleteEntityById(id);
		messageRepository.deleteMessagesByUserId(id);
	}

	@Override
	public void deleteEntityByUsername(String username) throws EntityDoesNotExistException {
		User user = retrieveUserByUsername(username);
		sessionFactory.getCurrentSession().delete(user);
	}

	@Override
	public void sendMessage(Long userId, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException {
		if (!doesEntityExistById(userId)) {
			throw new EntityDoesNotExistException("The user attempting to send a message does not exist");
		}
		Set<User> recipients = message.getRecipients();
		if (recipients == null || recipients.isEmpty()) {
			throw new NoRecipientsException();
		}

		containsIllegalRecipient(recipients, userId);
		message.setSenderId(userId);
		
		messageRepository.saveMessage(message);
	}
	
	@Override
	public boolean checkIfUsersAreFriends(Long userId, Long possibleFriendId) {
		FriendshipPK friendshipPK = FriendshipFactory.newPKInstance(userId, possibleFriendId);
		return friendshipRepository.doesEntityExistById(friendshipPK);
	}

	@Override
	public void addFriend(Long userId, Long friendId) throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException {
		if (doesEntityExistById(userId) && doesEntityExistById(friendId)) {
			friendshipRepository.addFriend(userId, friendId);
		} else {
			throw new EntityDoesNotExistException("One of the users does not exist when trying to add a friend");
		}
	}
	
	@Override
	public void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException {
		friendshipRepository.removeFriend(userId, friendId);
	}

	@Override
	public List<User> getUsersWhoArentFriends(Long id, String username, Integer startIdx, Integer count) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.not(Restrictions.eq("id", id)));

		List<Long> friendIds = friendshipRepository.retrieveFriendIds(id);
		if (!friendIds.isEmpty()) {
			criteria.add(Restrictions.not(Restrictions.in("id", friendIds)));
		}
		
		List<Long> knownFriendRequests = friendRequestRepository.retrievePossibleUsersRelatedToFriendRequest(id);
		if (!knownFriendRequests.isEmpty()) {
			criteria.add(Restrictions.not(Restrictions.in("id", knownFriendRequests)));
		}
		
		List<User> result = (List<User>) criteria
				.add(Restrictions.ilike("acc.username", username, MatchMode.ANYWHERE))
				.setFirstResult(startIdx).setMaxResults(count)
				.addOrder(Order.asc("id"))
				.list();
		return result;
	}
	
	@Override
	public void cancelFriendRequest(Long userId, Long cancelId) throws EntityDoesNotExistException {
		FriendshipPK id = FriendshipFactory.newPKInstance(userId, cancelId);
		friendRequestRepository.deleteEntityById(id);
	}
	
	@Override
	public boolean isUserLoggedIn(Long userId) {
		Boolean loggedIn = (Boolean) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("id", userId))
				.setProjection(Projections.property("acc.active"))
				.uniqueResult();
		return loggedIn;
	}

	@Override
	public void logOut(User user) 
			throws EntityDoesNotExistException, AlreadyLoggedOutException {
		Long userId = user.getId();
		if (!isUserLoggedIn(userId)) {
			throw new AlreadyLoggedOutException(user.getId());
		}
		Account account = user.getAccount();
		account.setActive(false);
		updateEntity(user);
	}
	
	@Override
	public void logIn(User user) 
			throws EntityDoesNotExistException, AlreadyLoggedInException {
		Long userId = user.getId();
		if (isUserLoggedIn(userId)) {
			throw new AlreadyLoggedInException(user.getAccount().getUsername());
		}
		
		Account account = user.getAccount();
		account.setActive(true);
		updateEntity(user);
	}

	@Override
	public void validatePasswordByUsername(String username, String enteredPassword)
			throws InvalidLoginException, NoSuchAlgorithmException, InvalidKeySpecException {
		String hashedPassword = retrieveHashedPasswordByUsername(username);
		Boolean valid = PasswordHash.validatePassword(enteredPassword, hashedPassword);
		if (!valid) {
			throw new InvalidLoginException(
					"Invalid login attempt. Your password does not match the one stored for '" + username + "'.");
		}
	}
	
	@Override
	public boolean isUserAdmin(String username) {
		return (Boolean) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(Projections.property("acc.admin"))
				.uniqueResult();
	}
	
	@Override
	public void giveAdminPrivileges(String username) {
		String adminHql = "update Account a "
				+ "set a.admin = true "
				+ "where a.username = :username";
		sessionFactory.getCurrentSession().createQuery(adminHql)
				.setParameter("username", username).executeUpdate();
	}

	@Override
	protected void setClazz() {
		this.clazz = User.class;
	}

	private String retrieveHashedPasswordByUsername(String username) {
		return (String) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(
						Projections.projectionList().add(
								Projections.property("acc.password")))
				.uniqueResult();
	}

	private Account retrieveBareboneAccountByUserId(Long userId) {
		String username = (String) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("id", userId))
				.setProjection(Projections.projectionList()
						.add(Projections.property("acc.username"))
					)
				.uniqueResult();
		
		Account account = new Account();
		account.setUsername(username);
		return account;
	}
	
	
	private void containsIllegalRecipient(Set<User> recipients, Long senderId) throws IllegalRecipientException {
		for (User recipient : recipients) {
			if (recipient.getId() == senderId) {
				throw new IllegalRecipientException("The message has been "
						+ "cancelled because a recipient of this message is the sender");
			}
			if (!checkIfUsersAreFriends(senderId, recipient.getId())) {
				throw new IllegalRecipientException("The users: { sender=" 
						+ senderId + ", recipient=" + recipient.getId() + " } are not friends");
			}
		}
	}
	
	/**
	 * Should retrieve the messages that a user has sent/received.
	 * 
	 * @param user - User performing handle message on
	 * @return the user with their messages
	 * @throws EntityDoesNotExistException - in case the User does not exist
	 */
	private User handleMessages(User user) throws EntityDoesNotExistException {
		Set<Message> messagesReceived = messageRepository.retrieveMessagesThatAUserHasReceived(user.getId());
		if (messagesReceived != null && !messagesReceived.isEmpty()) {
			for (Message message : messagesReceived) {
				Long senderId = message.getSenderId();
				message.setSender(retrieveBareboneUserById(senderId));
			}
			user.setMessagesReceived(messagesReceived);
		}
		
		Set<Message> messagesSent = messageRepository.retrieveMessagesThatAUserHasSent(user.getId());
		if (messagesSent != null && !messagesSent.isEmpty()) {
			for (Message message : messagesSent) {
				Long messageId = message.getId();
				Set<User> recipients = messageRepository.retrieveRecipientsOfMessageById(messageId);
				message.setRecipients(recipients);
			}
			user.setMessagesSent(messagesSent);
		}
		return user;
	}
	
	private User handleFriends(User user) {
		Long id = user.getId();
		Set<Friendship> friendIds = friendshipRepository.discoverFriendsOfUserId(id);
		if (friendIds != null && !friendIds.isEmpty()) {
			Set<User> friends = new HashSet<User>();
			for (Friendship friendship : friendIds) {
				Long friendId = friendship.getId().getUserId();
				User friend = retrieveBareboneUserById(friendId);
				friend.setFriendshipStarted(friendship.getFriendshipStarted());
				friends.add(friend);
			}
			user.setFriends(friends);
		}
		Set<FriendRequest> friendRequesters = friendRequestRepository.retrieveFriendRequestsRetrievedById(id);
		Set<FriendRequest> friendRequestees = friendRequestRepository.retrieveFriendRequestsSentById(id);
		user.setFriendRequested(friendRequestees);
		user.setFriendRequesters(friendRequesters);
		return user;
	}
	
}