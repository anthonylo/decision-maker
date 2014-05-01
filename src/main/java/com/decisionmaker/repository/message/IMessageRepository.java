package com.decisionmaker.repository.message;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.message.MessageType;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.repository.IRepository;

@Repository
@Transactional
public interface IMessageRepository extends IRepository<Message, Long> {
	
	void saveMessage(Message message) throws NoRecipientsException, IllegalRecipientException;
	
	Set<Message> retrieveMessagesThatAUserHasSent(Long userId) throws EntityDoesNotExistException;

	Set<Message> retrieveMessagesThatAUserHasReceived(Long userId) throws EntityDoesNotExistException;

	Message retrieveMessageByMessageId(Long messageId, MessageType messageType)
			throws EntityDoesNotExistException;

	Set<User> retrieveRecipientsOfMessageById(Long messageId) throws EntityDoesNotExistException;
	
	void deleteMessagesByUserId(Long userId);

}
