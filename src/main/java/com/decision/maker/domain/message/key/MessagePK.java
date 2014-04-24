package com.decision.maker.domain.message.key;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.decision.maker.domain.IPrimaryKey;
import com.decision.maker.domain.user.User;

@Embeddable
public class MessagePK implements IPrimaryKey {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2780111792516816497L;

	@Column(name = "message_id")
	private Long messageId;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User primaryUser;

	public MessagePK() {

	}

	public MessagePK(Long messageId, User primaryUser) {
		this.messageId = messageId;
		this.primaryUser = primaryUser;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public User getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(User primaryUser) {
		this.primaryUser = primaryUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result
				+ ((primaryUser == null) ? 0 : primaryUser.hashCode());
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
		MessagePK other = (MessagePK) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (primaryUser == null) {
			if (other.primaryUser != null)
				return false;
		} else if (!primaryUser.equals(other.primaryUser))
			return false;
		return true;
	}

}
