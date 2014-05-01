package com.decisionmaker.repository.message;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.MessageUser;
import com.decisionmaker.domain.message.key.MessageUserPK;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.repository.IRepository;

@Repository
@Transactional
public interface IMessageUserRepository extends IRepository<MessageUser, MessageUserPK> {

	Set<MessageUser> getMessagesThatUserHasSent(Long senderId);

	Set<MessageUser> getMessagesThatUserHasReceived(Long receiverId);

	Set<User> getRecipientsOfMessageByMessageId(Long messageId)
			throws EntityDoesNotExistException;

	Set<User> getRecipientsOfMessage(Long messageId, Long senderId)
			throws EntityDoesNotExistException;
	
}
