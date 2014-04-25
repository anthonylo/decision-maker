package com.decision.maker.repository.message;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.message.Message;
import com.decision.maker.repository.AbstractDecisionMakerRepository;

@Repository
@Transactional
@Qualifier("messageRepository")
public class MessageRepository extends AbstractDecisionMakerRepository<Message, Long> 
	implements IMessageRepository {

	@Override
	protected void setClazz() {
		this.clazz = Message.class;
	}

}
