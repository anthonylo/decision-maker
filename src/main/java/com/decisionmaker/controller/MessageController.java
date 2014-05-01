package com.decisionmaker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.service.message.IMessageService;

@Controller
@RequestMapping(value = "message")
public class MessageController {
	
	@Autowired
	private IMessageService messageService;
	
	@RequestMapping(value = "count", method = RequestMethod.GET)
	public @ResponseBody String retrieveCount() {
		return "There are " + messageService.retrieveCount() + " messages in the database";
	}
	
	@RequestMapping(value = "id/{messageId}", method = RequestMethod.GET)
	public @ResponseBody Message retrieveMessageById(@PathVariable Long messageId)
			throws EntityDoesNotExistException, DecisionMakerException {
		return messageService.retrieveEntityById(messageId);
	}

	@RequestMapping(value = "sent/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesSentByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageService.retrieveMessagesThatAUserHasSent(userId);
	}
	
	@RequestMapping(value = "received/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesReceivedByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageService.retrieveMessagesThatAUserHasReceived(userId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody Message message)
			throws DecisionMakerException {
		messageService.saveEntity(message);
		return "The message posted by user " + message.getSenderId() + " has been created."; 
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateUser(@PathVariable Long id, @RequestBody Message message)
			throws DecisionMakerException, EntityDoesNotExistException {
		message.setId(id);
		messageService.updateEntity(message);
		return "Message " + id + " has been updated.";
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUserById(@PathVariable Long id) throws EntityDoesNotExistException, DecisionMakerException {
		messageService.deleteEntityById(id);
		return "Message " + id + " has been deleted.";
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.HEAD)
	public @ResponseBody String checkIfUserExistsById(@PathVariable Long id) {
		boolean exists = messageService.checkIfEntityExistsById(id);
		return "Message '" + id + "': " + generateExistString(exists);
	}

	private String generateExistString(boolean exists) {
		return (exists) ? "exists." : "does not exist.";
	}
	
}
