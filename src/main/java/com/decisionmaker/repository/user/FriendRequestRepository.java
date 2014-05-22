package com.decisionmaker.repository.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;

@Repository
@Transactional
@Qualifier("friendRequestRepository")
@SuppressWarnings("unchecked")
public class FriendRequestRepository extends
		AbstractDecisionMakerRepository<FriendRequest, FriendshipPK> implements IFriendRequestRepository {
	
	@Override
	public Set<FriendRequest> retrieveFriendRequestsSentById(Long id) {
	return new HashSet<FriendRequest>(sessionFactory.getCurrentSession().createCriteria(clazz)
			.add(Restrictions.eq("id.userId", id))
			.list());
	}
	
	@Override
	public Set<FriendRequest> retrieveFriendRequestsRetrievedById(Long id) {
		return new HashSet<FriendRequest>(sessionFactory.getCurrentSession().createCriteria(clazz)
			.add(Restrictions.eq("id.friendId", id))
			.list());
	}
	
	@Override
	public List<Long> retrievePossibleUsersRelatedToFriendRequest(Long id) {
		List<Long> asRequestor = (List<Long>) sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("id.friendId", id))
				.setProjection(Projections.property("id.userId"))
				.list();

		List<Long> asRequestee = sessionFactory.getCurrentSession().createCriteria(clazz)
				.add(Restrictions.eq("id.userId", id))
				.setProjection(Projections.property("id.friendId"))
				.list();
		asRequestor.addAll(asRequestee);
		return asRequestor;
	}

	@Override
	protected void setClazz() {
		this.clazz = FriendRequest.class;
	}
}
