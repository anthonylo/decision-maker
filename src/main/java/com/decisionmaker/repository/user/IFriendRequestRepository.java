package com.decisionmaker.repository.user;

import java.util.List;
import java.util.Set;

import com.decisionmaker.domain.user.FriendRequest;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.repository.IRepository;

public interface IFriendRequestRepository extends IRepository<FriendRequest, FriendshipPK> {

	Set<FriendRequest> retrieveFriendRequestsSentById(Long id);
	
	Set<FriendRequest> retrieveFriendRequestsRetrievedById(Long id);

	List<Long> retrievePossibleUsersRelatedToFriendRequest(Long id);
	
}
