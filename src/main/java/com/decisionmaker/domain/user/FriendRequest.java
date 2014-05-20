package com.decisionmaker.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.decisionmaker.domain.AbstractDecisionMakerObject;
import com.decisionmaker.domain.user.key.FriendshipPK;

@Entity
@Table(name = "dm_friend_request")
public class FriendRequest extends AbstractDecisionMakerObject<FriendshipPK> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 8200301049075638564L;

	@EmbeddedId
	private FriendshipPK id;

	@Column(name = "request_stated", updatable = false, insertable = false)
	private Date requestStated;
	
	@Column(name = "user_username", updatable = false)
	private String userUsername;
	
	@Column(name = "friend_username", updatable = false)
	private String friendUsername;
	
	public FriendRequest() {

	}

	public FriendRequest(FriendshipPK id, Date requestStated,
			String userUsername, String friendUsername) {
		this.id = id;
		this.requestStated = requestStated;
		this.userUsername = userUsername;
		this.friendUsername = friendUsername;
	}

	@Override
	public FriendshipPK getId() {
		return id;
	}

	@Override
	public void setId(FriendshipPK id) {
		this.id = id;
	}

	public Date getRequestStated() {
		return requestStated;
	}

	public void setRequestStated(Date requestStated) {
		this.requestStated = requestStated;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((friendUsername == null) ? 0 : friendUsername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((requestStated == null) ? 0 : requestStated.hashCode());
		result = prime * result
				+ ((userUsername == null) ? 0 : userUsername.hashCode());
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
		FriendRequest other = (FriendRequest) obj;
		if (friendUsername == null) {
			if (other.friendUsername != null)
				return false;
		} else if (!friendUsername.equals(other.friendUsername))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (requestStated == null) {
			if (other.requestStated != null)
				return false;
		} else if (!requestStated.equals(other.requestStated))
			return false;
		if (userUsername == null) {
			if (other.userUsername != null)
				return false;
		} else if (!userUsername.equals(other.userUsername))
			return false;
		return true;
	}
	
}