package com.decisionmaker.factory.user;

import com.decisionmaker.domain.user.Friendship;
import com.decisionmaker.domain.user.key.FriendshipPK;

public final class FriendshipFactory {

	public static final Friendship newInstance(Long userId, Long friendId) {
		return new Friendship(newPKInstance(userId, friendId), null);
	}
	
	public static final Friendship newInstance(FriendshipPK id) {
		return new Friendship(id, null);
	}
	
	public static final FriendshipPK newPKInstance(Long userId, Long friendId) {
		return new FriendshipPK(userId, friendId);
	}
	
}