package com.decisionmaker.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.message.MessageRequestBody;
import com.decisionmaker.domain.message.MessageType;
import com.decisionmaker.domain.user.User;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.exception.IllegalRecipientException;
import com.decisionmaker.exception.NoRecipientsException;
import com.decisionmaker.service.message.IMessageService;
import com.decisionmaker.service.user.IUserService;

@Controller
@RequestMapping(value = "message")
public class MessageController {

	@Autowired
	private IMessageService messageService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "count", method = RequestMethod.GET)
	public @ResponseBody String retrieveCount() {
		return "There are " + messageService.retrieveCount() + " messages in the database";
	}
	
	@RequestMapping(value = "id/{messageId}", method = RequestMethod.GET)
	public @ResponseBody Message retrieveMessageById(@PathVariable Long messageId)
			throws EntityDoesNotExistException, DecisionMakerException {
		return messageService.retrieveEntityById(messageId);
	}

	@RequestMapping(value = "read/received", method = RequestMethod.GET)
	public ModelAndView viewMessagesReceived(HttpServletRequest request, ModelMap map)
			throws DecisionMakerException, EntityDoesNotExistException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long id = user.getId();
		userService.retrieveEntityById(id);
		map.put("messages", user.getMessagesReceived());
		map.put("type", MessageType.RECEIVED);
		session.setAttribute("user", user);
		return new ModelAndView("messages/view", map);
	}
	@RequestMapping(value = "read/sent", method = RequestMethod.GET)
	public ModelAndView viewMessagesSent(HttpServletRequest request, ModelMap map)
			throws DecisionMakerException, EntityDoesNotExistException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long id = user.getId();
		userService.retrieveEntityById(id);
		map.put("messages", user.getMessagesSent());
		map.put("type", MessageType.SENT);
		session.setAttribute("user", user);
		return new ModelAndView("messages/view", map);
	}

	@RequestMapping(value = "sent/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesSentByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageService.retrieveMessagesThatAUserHasSent(userId);
	}
	
	@RequestMapping(value = "received/user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Set<Message> retrieveMessagesReceivedByUserId(@PathVariable Long userId) throws EntityDoesNotExistException {
		return messageService.retrieveMessagesThatAUserHasReceived(userId);
	}

	@RequestMapping(value = "send", method = RequestMethod.GET)
	public ModelAndView goToSendMessagePage(HttpServletRequest request, ModelMap map)
			throws EntityDoesNotExistException,	NoRecipientsException, IllegalRecipientException {
		User user = (User) request.getSession().getAttribute("user");
		map.put("friends", user.getFriends());
		return new ModelAndView("messages/send", map);
	}
	
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public @ResponseBody String sendMessageToFriends(@RequestBody MessageRequestBody message, HttpServletRequest request) 
			throws EntityDoesNotExistException, NoRecipientsException, IllegalRecipientException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long senderId = user.getId();
		messageService.sendMessageToFriends(senderId, message);
		return "The message has successfully been sent to: " + Arrays.toString(message.getRecipients());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String createMessage(@RequestBody Message message)
			throws DecisionMakerException, NoSuchAlgorithmException, InvalidKeySpecException {
		messageService.saveEntity(message);
		return "The message posted by user " + message.getSenderId() + " has been created."; 
	}
	
	@RequestMapping(value = "id/{id}", method = RequestMethod.PUT)
	public @ResponseBody String updateUser(@PathVariable Long id, @RequestBody Message message)
			throws DecisionMakerException, EntityDoesNotExistException, NoSuchAlgorithmException, InvalidKeySpecException {
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
