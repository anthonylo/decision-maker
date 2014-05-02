package com.decisionmaker.domain.user.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.decisionmaker.domain.IPrimaryKey;

@Embeddable
public class FriendshipPK implements IPrimaryKey {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6486700083605532080L;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "friend_id")
	private Long friendId;

	public FriendshipPK() {
		
	}

	public FriendshipPK(Long userId, Long friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userId == null) ? 0 : userId.hashCode());
		result = prime * result
				+ ((friendId == null) ? 0 : friendId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FriendshipPK other = (FriendshipPK) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (friendId == null) {
			if (other.friendId != null)
				return false;
		} else if (!friendId.equals(other.friendId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[ user=" + userId + ", friend=" + friendId + "]";
	}	
	
}