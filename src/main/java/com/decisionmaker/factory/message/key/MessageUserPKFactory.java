package com.decisionmaker.factory.message.key;

import com.decisionmaker.domain.message.key.MessageUserPK;

public final class MessageUserPKFactory {

	public static MessageUserPK newInstance(Long messageId, Long senderId, Long recipientId) {
		return new MessageUserPK(messageId, senderId, recipientId);
	}

}
