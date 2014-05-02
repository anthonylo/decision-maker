package com.decisionmaker.repository.user;

import java.util.Set;

import com.decisionmaker.domain.user.Friendship;
import com.decisionmaker.domain.user.key.FriendshipPK;
import com.decisionmaker.exception.AlreadyFriendsException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalFriendException;
import com.decisionmaker.repository.IRepository;

public interface IFriendshipRepository extends IRepository<Friendship, FriendshipPK> {
	
	Set<Friendship> discoverUserIdIsFriendOf(Long userId);
	
	Set<Friendship> discoverFriendsOfUserId(Long userId);
	
	void addFriend(Long userId, Long possibleFriendId) throws AlreadyFriendsException, IllegalFriendException;
	
	void removeFriend(Long userId, Long friendId) throws EntityDoesNotExistException;

}