package com.decision.maker.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decision.maker.domain.message.Message;
import com.decision.maker.exception.EntityDoesNotExistException;
import com.decision.maker.repository.IRepository;

@Controller
@RequestMapping(value = "message")
public class MessageController {

	@Autowired
	private IRepository<Message, Long> messageRepository;
	
	public void setMessageRepository(IRepository<Message, Long> messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	@RequestMapping(value = "id/{id}")
	public @ResponseBody Set<Message> retrieveMessageById(@PathVariable Long id) throws EntityDoesNotExistException {
		Set<Message> message = messageRepository.retrieveById(id);
		return message;
	}
	
}
