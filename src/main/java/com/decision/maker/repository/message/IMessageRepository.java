package com.decision.maker.repository.message;

import com.decision.maker.domain.message.Message;
import com.decision.maker.domain.message.key.MessagePK;
import com.decision.maker.repository.IRepository;

public interface IMessageRepository extends IRepository<Message, MessagePK> {
	
}
