package com.decisionmaker.repository.message;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.message.MessageType;
import com.decisionmaker.domain.message.MessageUser;
import com.decisionmaker.domain.message.key.MessageUserPK;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.factory.message.key.MessageUserPKFactory;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;
import com.decisionmaker.repository.user.IUserRepository;

@Repository
@Transactional
@Qualifier("messageRepository")
public class MessageRepository extends AbstractDecisionMakerRepository<Message, Long> 
	implements IMessageRepository {

	@Autowired
	private IMessageUserRepository messageUserRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Value("${message.delete.userid}")
	private String deleteByUserId;
	
	@Override
	public void saveMessage(Message message) throws NoRecipientsException, IllegalRecipientException {
		super.saveEntity(message);
		
		Set<User> recipients = message.getRecipients();
		Long senderId = message.getSenderId();
		Long messageId = message.getId();
		
		for (User recipient : recipients) {
			Long recipientId = recipient.getId();
			MessageUserPK muPK = MessageUserPKFactory.newInstance(messageId, senderId, recipientId);
			MessageUser mu = new MessageUser(muPK);
			messageUserRepository.saveEntity(mu);
		}
	}

	@Override
	public Set<Message> retrieveMessagesThatAUserHasSent(Long userId) throws EntityDoesNotExistException {
		Set<MessageUser> result = messageUserRepository.getMessagesThatUserHasSent(userId);
		return generateMessageListFromMessageUserResult(result, MessageType.SENT);
	}

	@Override
	public Set<Message> retrieveMessagesThatAUserHasReceived(Long userId) throws EntityDoesNotExistException {
		Set<MessageUser> result = messageUserRepository.getMessagesThatUserHasReceived(userId);
		return generateMessageListFromMessageUserResult(result, MessageType.RECEIVED);
	}

	@Override
	public Set<User> retrieveRecipientsOfMessageById(Long messageId) throws EntityDoesNotExistException {
		return messageUserRepository.getRecipientsOfMessageByMessageId(messageId);
	}
	
	@Override
	public Message retrieveMessageById(Long messageId, MessageType messageType)
			throws EntityDoesNotExistException {
		Message message = retrieveUniqueById(messageId);
		Long senderId = message.getSenderId();
		
		if (messageType == MessageType.RECEIVED || messageType == MessageType.ALL) {
			User sender = userRepository.retrieveBareboneUserById(senderId);
			message.setSender(sender);
		}
		
		if (messageType == MessageType.SENT || messageType == MessageType.ALL) {
			Set<User> recipients = messageUserRepository.getRecipientsOfMessage(messageId, senderId);
			message.setRecipients(recipients);
		}
		
		return message;
	}

	@Override
	public void deleteMessagesByUserId(Long userId) {
		sessionFactory.getCurrentSession().createQuery(deleteByUserId)
			.setParameter("senderId", userId).executeUpdate();
	}

	private Set<Message> generateMessageListFromMessageUserResult(
			Set<MessageUser> result, MessageType messageType) throws EntityDoesNotExistException {
		Set<Message> messages = new LinkedHashSet<Message>();
		for (MessageUser mu : result) {
			MessageUserPK mId = mu.getId();
			Long id = mId.getMessageId();
			Message message = retrieveMessageById(id, messageType);
			messages.add(message);
		}
		return messages;
	}
	
	@Override
	protected void setClazz() {
		this.clazz = Message.class;
	}

}
