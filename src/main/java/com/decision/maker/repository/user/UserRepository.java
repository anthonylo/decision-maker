package com.decision.maker.repository.user;

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

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.user.Account;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.exception.IllegalMessageInsertException;
import com.decision.maker.exception.NoRecipientsException;
import com.decision.maker.exception.NotImplementedException;
import com.decision.maker.repository.AbstractDecisionMakerRepository;
import com.decision.maker.repository.message.IMessageRepository;

@Repository
@Transactional
@Qualifier("userRepository")
public class UserRepository extends AbstractDecisionMakerRepository<User, Long> implements IUserRepository {

	@Autowired
	private IMessageRepository messageRepository;
	
	private User handleMessages(User user) throws EntityDoesNotExistException {
		Set<Message> messagesReceived = user.getMessagesReceived();
		if (!messagesReceived.isEmpty()) {
			for (Message message : messagesReceived) {
				Long senderId = message.getSenderId();
				message.setSender(retrieveBareboneUserById(senderId));
			}
		}
		
		Set<Message> messagesSent = user.getMessagesSent();
		if (!messagesSent.isEmpty()) {
			for (Message message : messagesSent) {
				Long messageId = message.getId();
				Set<User> recipients = messageRepository.retrieveRecipientsOfMessageById(messageId);
				message.setRecipients(recipients);
			}
		}
		return user;
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
	public void sendMessage(Long userId, Message message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalMessageInsertException {
		Set<User> recipients = message.getRecipients();
		if (recipients == null || recipients.isEmpty()) {
			throw new NoRecipientsException();
		}

		User sender = retrieveUniqueById(userId);

		message.setSenderId(userId);
		Set<Message> messagesSent = sender.getMessagesSent();
		messagesSent.add(message);
		sender.setMessagesSent(messagesSent);
		
		messageRepository.saveMessage(message);
	}
	
	@Override
	public void deleteEntityByUsername(String username) throws EntityDoesNotExistException {
		User user = retrieveUserByUsername(username);
		sessionFactory.getCurrentSession().delete(user);
	}
	
	@Override
	protected void setClazz() {
		this.clazz = User.class;
	}
	
}