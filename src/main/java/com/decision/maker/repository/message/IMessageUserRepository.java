package com.decision.maker.repository.message;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.MessageUser;
import com.decision.maker.domain.message.key.MessageUserPK;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.IRepository;

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
