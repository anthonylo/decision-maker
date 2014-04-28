package com.decision.maker.repository.message;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.message.MessageUser;
import com.decision.maker.domain.message.key.MessageUserPK;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.factory.message.key.MessageUserPKFactory;
import com.decision.maker.repository.AbstractDecisionMakerRepository;
import com.decision.maker.repository.user.IUserRepository;

@Repository
@Transactional
@Qualifier("messageRepository")
public class MessageRepository extends AbstractDecisionMakerRepository<Message, Long> 
	implements IMessageRepository {

	@Autowired
	private IMessageUserRepository messageUserRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public Set<Message> retrieveMessagesThatAUserHasSent(Long userId) throws EntityDoesNotExistException {
		Set<MessageUser> result = messageUserRepository.getMessagesThatUserHasSent(userId);
		return generateMessageListFromMessageUserResult(result);
	}

	@Override
	public Set<Message> retrieveMessagesThatAUserHasReceived(Long userId) throws EntityDoesNotExistException {
		Set<MessageUser> result = messageUserRepository.getMessagesThatUserHasReceived(userId);
		return generateMessageListFromMessageUserResult(result);
	}

	@Override
	public Set<User> retrieveRecipientsOfMessageById(Long messageId) throws EntityDoesNotExistException {
		return messageUserRepository.getRecipientsOfMessageByMessageId(messageId);
	}
	
	@Override
	public Message retrieveMessageByMessageId(Long messageId) throws EntityDoesNotExistException {
		Set<Message> listResults = super.retrieveById(messageId);
		Message message = listResults.iterator().next();
		
		Long senderId = message.getSenderId();
		User sender = userRepository.retrieveBareboneUserById(senderId);
		message.setSender(sender);
		
		MessageUserPK muID = MessageUserPKFactory.newInstance(messageId, senderId);
		Set<User> recipients = messageUserRepository.getRecipientsOfMessage(muID);
		message.setRecipients(recipients);
		
		return message;
	}

	private Set<Message> generateMessageListFromMessageUserResult(
			Set<MessageUser> result) throws EntityDoesNotExistException {
		Set<Message> messages = new LinkedHashSet<Message>();
		for (MessageUser mu : result) {
			MessageUserPK mId = mu.getId();
			Long id = mId.getMessageId();
			Message receivedMessage = retrieveMessageByMessageId(id);
			messages.add(receivedMessage);
		}
		return messages;
	}
	
	@Override
	protected void setClazz() {
		this.clazz = Message.class;
	}

}
