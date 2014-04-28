package com.decision.maker.repository.message;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.IRepository;

@Repository
@Transactional
public interface IMessageRepository extends IRepository<Message, Long> {
	
	Set<Message> retrieveMessagesThatAUserHasSent(Long userId) throws EntityDoesNotExistException;

	Set<Message> retrieveMessagesThatAUserHasReceived(Long userId) throws EntityDoesNotExistException;

	Message retrieveMessageByMessageId(Long messageId) throws EntityDoesNotExistException;

	Set<User> retrieveRecipientsOfMessageById(Long messageId) throws EntityDoesNotExistException;
	
}
