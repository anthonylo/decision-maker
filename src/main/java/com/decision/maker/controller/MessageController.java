package com.decision.maker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decision.maker.domain.message.Message;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.message.IMessageRepository;
import com.decision.maker.repository.message.IMessageUserRepository;

@Controller
@RequestMapping(value = "message")
public class MessageController {

	@Autowired
	private IMessageRepository messageRepository;
	
	@Autowired
	private IMessageUserRepository messageUserRepository;

	public void setMessageRepository(IMessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void setMessageUserRepository(IMessageUserRepository messageUserRepository) {
		this.messageUserRepository = messageUserRepository;
	}
	
	@RequestMapping(value = "count", method = RequestMethod.GET)
	public @ResponseBody String retrieveCount() {
		return "There are " + messageRepository.retrieveCount() + " messages in the database";
	}
	
	@RequestMapping(value = "id/{messageId}", method = RequestMethod.GET)
	public @ResponseBody Message retrieveMessageById(@PathVariable Long messageId) throws EntityDoesNotExistException {
		Message message = messageRepository.retrieveMessageByMessageId(messageId);
		return message;
	}

	@RequestMapping(value = "sent/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesSentByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageRepository.retrieveMessagesThatAUserHasSent(userId);
	}
	
	@RequestMapping(value = "received/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesReceivedByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageRepository.retrieveMessagesThatAUserHasReceived(userId);
	}
	
}
