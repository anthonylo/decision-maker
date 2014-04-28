package com.decision.maker.repository.message;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.MessageUser;
import com.decision.maker.domain.message.key.MessageUserPK;
import com.decision.maker.domain.user.User;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.AbstractDecisionMakerRepository;
import com.decision.maker.repository.user.IUserRepository;

@Repository
@Transactional
@Qualifier("messageUserRepository")
@SuppressWarnings("unchecked")
public class MessageUserRepository extends
		AbstractDecisionMakerRepository<MessageUser, MessageUserPK> implements IMessageUserRepository {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public Set<User> getRecipientsOfMessage(MessageUserPK id) throws EntityDoesNotExistException {
		List<Long> friendIds = sessionFactory.getCurrentSession().createCriteria(MessageUser.class)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.property("friendId"))
				.list();
		
		return userRepository.retrieveBareboneUsersFromListOfIds(friendIds);
	}
	
	@Override
	public Set<User> getRecipientsOfMessageByMessageId(Long messageId) throws EntityDoesNotExistException {
		List<Long> friendIds = sessionFactory.getCurrentSession().createCriteria(MessageUser.class)
				.add(Restrictions.eq("id.messageId", messageId))
				.setProjection(Projections.property("friendId"))
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
				.add(Restrictions.eq("friendId", receiverId))
				.list();
		
		return new LinkedHashSet<MessageUser>(receivedMessages);
	}

	@Override
	protected void setClazz() {
		this.clazz = MessageUser.class;
	}

}
