package com.decision.maker.domain.message.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.decision.maker.domain.IPrimaryKey;

@Embeddable
public class MessageUserPK implements IPrimaryKey {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 4989325031305362995L;

	@Column(name = "message_id")
	private Long messageId;

	@Column(name = "sender_id")
	private Long senderId;
	
	@Column(name = "recipient_id")
	private Long recipientId;

	public MessageUserPK() {
		
	}

	public MessageUserPK(Long messageId, Long senderId, Long recipientId) {
		this.messageId = messageId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result
				+ ((recipientId == null) ? 0 : recipientId.hashCode());
		result = prime * result
				+ ((senderId == null) ? 0 : senderId.hashCode());
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
		MessageUserPK other = (MessageUserPK) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (recipientId == null) {
			if (other.recipientId != null)
				return false;
		} else if (!recipientId.equals(other.recipientId))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		return true;
	}

}