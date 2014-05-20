package com.decisionmaker.repository.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.repository.AbstractDecisionMakerRepository;

@Repository
@Transactional
@Qualifier("friendRequestRepository")
public class FriendRequestRepository extends
		AbstractDecisionMakerRepository<FriendRequest, FriendshipPK> implements IFriendRequestRepository {

	@Override
	protected void setClazz() {
		this.clazz = FriendRequest.class;
	}

}
