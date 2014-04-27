package com.decision.maker.factory.message.key;

import com.decision.maker.domain.message.key.MessageUserPK;

public final class MessageUserPKFactory {

	public static MessageUserPK newInstance(Long messageId, Long senderId) {
		return new MessageUserPK(messageId, senderId);
	}

}