package com.decisionmaker.repository.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.user.Account;
import com.decisionmaker.domain.user.Friendship;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.exception.NotImplementedException;
import com.decisionmaker.factory.user.FriendshipFactory;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;
import com.decisionmaker.repository.message.IMessageRepository;

@Repository
@Transactional
@Qualifier("userRepository")
public class UserRepository extends AbstractDecisionMakerRepository<User, Long> implements IUserRepository {

	@Autowired
	private IMessageRepository messageRepository;
	
	@Autowired
	private IFriendshipRepository friendshipRepository;

	@Override
	public void addFriend(Long userId, Long friendId) throws AlreadyFriendsException, EntityDoesNotExistException, IllegalFriendException {
		if (doesEntityExistById(userId) && doesEntityExistById(friendId)) {
			friendshipRepository.addFriend(userId, friendId);
		} else {
			throw new EntityDoesNotExistException("One of the users does not exist when trying to add a friend");
		}
	}
	
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
		handleMessages(user);
		return user;
	}

	@Override
	public boolean checkIfUsernameAlreadyExists(String username) {
		Long count = (Long) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count > 0;
	}

	@Override
	public User retrieveRandom() throws NotImplementedException {
		// TODO - Implement method
		throw new NotImplementedException("IUserRepository.retrieveRandom()");
	}

	@Override
	public User retrieveBareboneUserById(Long id) {
		String username = (String) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.projectionList()
						.add(Projections.property("acc.username"))
					)
				.uniqueResult();
		
		Account account = new Account();
		account.setUsername(username);
		
		Object[] objList = (Object[]) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.projectionList()
						.add(Projections.property("firstName"))
						.add(Projections.property("lastName"))
						.add(Projections.property("age"))
					)
				.uniqueResult();

		User user = new User();
		user.setId(id);
		user.setFirstName((String) objList[0]);
		user.setLastName((String) objList[1]);
		user.setAge((Integer) objList[2]);
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
	public Set<User> retrieveFriendsById(Long id) {
		return null;
	}
	
	@Override
	public boolean checkIfUsersAreFriends(Long userId, Long possibleFriendId) {
		FriendshipPK friendshipPK = FriendshipFactory.newPKInstance(userId, possibleFriendId);
		return friendshipRepository.doesEntityExistById(friendshipPK);
	}
	
	@Override
	public void sendMessage(Long userId, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException {
		Set<User> recipients = message.getRecipients();
		if (recipients == null || recipients.isEmpty()) {
			throw new NoRecipientsException();
		}

		containsIllegalRecipient(recipients, userId);
		
		User sender = retrieveUniqueById(userId);

		message.setSenderId(userId);
		Set<Message> messagesSent = sender.getMessagesSent();
		messagesSent.add(message);
		sender.setMessagesSent(messagesSent);
		
		messageRepository.saveMessage(message);
	}
	
	@Override
	public void deleteEntityById(Long id) throws EntityDoesNotExistException {
		messageRepository.deleteMessagesByUserId(id);
		super.deleteEntityById(id);
	}

	@Override
	public void deleteEntityByUsername(String username) throws EntityDoesNotExistException {
		User user = retrieveUserByUsername(username);
		sessionFactory.getCurrentSession().delete(user);
	}
	
	@Override
	public void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException {
		friendshipRepository.removeFriend(userId, friendId);
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
	public void performLogInOrOut(Long userId) throws EntityDoesNotExistException {
		User user = retrieveUniqueById(userId);
		Account account = user.getAccount();
		Boolean loggedIn = account.getActive();
		account.setActive(!loggedIn);
		updateEntity(user);
	}

	private void containsIllegalRecipient(Set<User> recipients, Long senderId) throws IllegalRecipientException {
		for (User recipient : recipients) {
			if (recipient.getId() == senderId) {
				throw new IllegalRecipientException("The message has been "
						+ "cancelled because a recipient of this message is the sender");
			}
		}
	}
	
	private User handleFriends(User user) {
		Long id = user.getId();
		Set<Friendship> friendIds = friendshipRepository.discoverFriendsOfUserId(id);
		if (friendIds != null && !friendIds.isEmpty()) {
			Set<User> friends = new HashSet<User>();
			for (Friendship friendship : friendIds) {
				Long friendId = friendship.getId().getUserId();
				User friend = retrieveBareboneUserById(friendId);
				friends.add(friend);
			}
			user.setFriends(friends);
		}
		return user;
	}
	
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
	
	@Override
	protected void setClazz() {
		this.clazz = User.class;
	}
	
}