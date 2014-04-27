package com.decision.maker.domain.message;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.decision.maker.domain.message.key.MessageUserPK;

@Entity
@Table(name = "dm_message_user")
public class MessageUser extends AbstractDecisionMakerObject<MessageUserPK> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -1446225667619856737L;

	@EmbeddedId
	private MessageUserPK id;
	
//	@Transient
	@Column(name = "friend_id")
	private Long friendId;
	
	public MessageUser() {
		
	}

	public MessageUser(MessageUserPK id, Long friendId) {
		this.id = id;
		this.friendId = friendId;
	}

	@Override
	public MessageUserPK getId() {
		return id;
	}

	@Override
	public void setId(MessageUserPK id) {
		this.id = id;
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
		MessageUser other = (MessageUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
