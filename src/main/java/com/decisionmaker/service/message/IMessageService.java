package com.decision.maker.service.message;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.decision.maker.domain.message.Message;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.service.IService;

@Service
public interface IMessageService extends IService<Message, Long> {
	
	Set<Message> retrieveMessagesThatAUserHasReceived(Long receiverId) throws EntityDoesNotExistException;
	
	Set<Message> retrieveMessagesThatAUserHasSent(Long senderId) throws EntityDoesNotExistException;
	
}
