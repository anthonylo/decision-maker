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
	
	/**
	 * Save a message to the database.
	 * 
	 * @param message - The message to persist.
	 * @throws NoRecipientsException - Thrown if the message did not contain recipients.
	 * @throws IllegalRecipientException - Thrown if the message had a recipient that pointed to itself.
	 */
	void saveMessage(Message message) throws NoRecipientsException, IllegalRecipientException;
	
	/**
	 * Retrieve a set of Messages that a User has sent by ID.
	 * 
	 * @param userId - User ID to query for.
	 * @return A set of messages.
	 * @throws EntityDoesNotExistException - Thrown if the Message does not exist.
	 */
	Set<Message> retrieveMessagesThatAUserHasSent(Long userId) throws EntityDoesNotExistException;

	/**
	 * Retrieve a set of Messages that a User has received by ID.
	 * 
	 * @param userId - User ID to query for.
	 * @return A set of messages.
	 * @throws EntityDoesNotExistException - Thrown if the Message does not exist.
	 */
	Set<Message> retrieveMessagesThatAUserHasReceived(Long userId) throws EntityDoesNotExistException;

	/**
	 * Retrieve a Message by ID.
	 * 
	 * @param messageId - ID to query for.
	 * @param messageType - The {@link MessageType} of the Message.
	 * @return A single message.
	 * @throws EntityDoesNotExistException - Thrown if the Message does not exist.
	 */
	Message retrieveMessageById(Long messageId, MessageType messageType)
			throws EntityDoesNotExistException;

	/**
	 * Retrieve the Users that have received a certain message.
	 * 
	 * @param messageId - The ID of the message to query.
	 * @return A set of User's.
	 * @throws EntityDoesNotExistException - Thrown if the Message or the Users do not exist.
	 */
	Set<User> retrieveRecipientsOfMessageById(Long messageId) throws EntityDoesNotExistException;
	
	/**
	 * Delete messages that were sent by a certain user.
	 * 
	 * @param userId - The User ID that is ordering the delete.
	 */
	void deleteMessagesByUserId(Long userId);

}
