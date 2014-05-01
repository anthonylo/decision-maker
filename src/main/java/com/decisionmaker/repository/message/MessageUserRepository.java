package com.decisionmaker.repository.message;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.MessageUser;
import com.decisionmaker.domain.message.key.MessageUserPK;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;
import com.decisionmaker.repository.user.IUserRepository;

@Repository
@Transactional
@Qualifier("messageUserRepository")
@SuppressWarnings("unchecked")
public class MessageUserRepository extends
		AbstractDecisionMakerRepository<MessageUser, MessageUserPK> implements IMessageUserRepository {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public Set<User> getRecipientsOfMessage(Long messageId, Long senderId) throws EntityDoesNotExistException {
		List<Long> friendIds = sessionFactory.getCurrentSession().createCriteria(MessageUser.class)
				.add(Restrictions.eq("id.messageId", messageId))
				.add(Restrictions.eq("id.senderId", senderId))
				.setProjection(Projections.property("id.recipientId"))
				.list();
		
		return userRepository.retrieveBareboneUsersFromListOfIds(friendIds);
	}
	
	@Override
	public Set<User> getRecipientsOfMessageByMessageId(Long messageId) throws EntityDoesNotExistException {
		List<Long> friendIds = sessionFactory.getCurrentSession().createCriteria(MessageUser.class)
				.add(Restrictions.eq("id.messageId", messageId))
				.setProjection(Projections.property("id.recipientId"))
				.list();
		
		return userRepository.retrieveBareboneUsersFromListOfIds(friendIds);
	}

	@Override
	public Set<MessageUser> getMessagesThatUserHasSent(Long senderId) {
		List<MessageUser> sentMessages = sessionFactory.getCurrentSession()
				.createCriteria(MessageUser.class)
				.add(Restrictions.eq("id.senderId", senderId))
				.list();
		
		return new LinkedHashSet<MessageUser>(sentMessages);
	}

	@Override
	public Set<MessageUser> getMessagesThatUserHasReceived(Long receiverId) {
		List<MessageUser> receivedMessages = sessionFactory.getCurrentSession()
				.createCriteria(MessageUser.class)
				.add(Restrictions.eq("id.recipientId", receiverId))
				.list();
		
		return new LinkedHashSet<MessageUser>(receivedMessages);
	}

	@Override
	protected void setClazz() {
		this.clazz = MessageUser.class;
	}

}
