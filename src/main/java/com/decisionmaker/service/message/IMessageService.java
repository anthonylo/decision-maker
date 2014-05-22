package com.decisionmaker.service.message;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.message.MessageRequestBody;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.service.IService;

@Service
public interface IMessageService extends IService<Message, Long> {
	
	Set<Message> retrieveMessagesThatAUserHasReceived(Long receiverId) throws EntityDoesNotExistException;
	
	Set<Message> retrieveMessagesThatAUserHasSent(Long senderId) throws EntityDoesNotExistException;

	void sendMessageToFriends(Long senderId, MessageRequestBody message) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException;
	
}
