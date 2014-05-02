package com.decisionmaker.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.decisionmaker.domain.AbstractDecisionMakerObject;
import com.decisionmaker.domain.user.key.FriendshipPK;

@Entity
@Table(name = "dm_user_friend")
public class Friendship extends AbstractDecisionMakerObject<FriendshipPK> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -9140462585985436346L;

	@EmbeddedId
	private FriendshipPK id;

	@Column(name = "friendship_started", updatable = false, insertable = false)
	private Date friendshipStarted;
	
	public Friendship() {
		
	}
	
	public Friendship(FriendshipPK id, Date friendshipStarted) {
		this.id = id;
		this.friendshipStarted = friendshipStarted;
	}

	@Override
	public FriendshipPK getId() {
		return id;
	}

	@Override
	public void setId(FriendshipPK id) {
		this.id = id;
	}

	public Date getFriendshipStarted() {
		return friendshipStarted;
	}

	public void setFriendshipStarted(Date friendshipStarted) {
		this.friendshipStarted = friendshipStarted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((friendshipStarted == null) ? 0 : friendshipStarted
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Friendship other = (Friendship) obj;
		if (friendshipStarted == null) {
			if (other.friendshipStarted != null)
				return false;
		} else if (!friendshipStarted.equals(other.friendshipStarted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}