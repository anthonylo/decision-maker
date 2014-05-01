package com.decisionmaker.service.message;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.decisionmaker.domain.message.Message;
import com.decisionmaker.domain.message.MessageType;
import com.decisionmaker.exception.DecisionMakerException;
import com.decisionmaker.exception.EntityDoesNotExistException;
import com.decisionmaker.repository.message.IMessageRepository;

@Component
@Transactional
@Qualifier("messageService")
public class MessageService implements IMessageService {

	@Autowired
	private IMessageRepository messageRepository;

	@Override
	public String getTargetDatabase() {
		return messageRepository.getTargetDatabase();
	}

	@Override
	public void saveEntity(Message entity) throws DecisionMakerException {
		messageRepository.saveEntity(entity);
	}

	@Override
	public Message retrieveEntityById(Long messageId)
			throws DecisionMakerException, EntityDoesNotExistException {
		return messageRepository.retrieveMessageByMessageId(messageId, MessageType.ALL);
	}

	@Override
	public List<Message> retrieveEntitiesByPageAndCount(int page, int count) {
		return messageRepository.retrieveSubsetOfEndpoint(page * count, count);
	}

	@Override
	public Set<Message> retrieveMessagesThatAUserHasReceived(Long receiverId)
			throws EntityDoesNotExistException {
		return messageRepository
				.retrieveMessagesThatAUserHasReceived(receiverId);
	}

	@Override
	public Set<Message> retrieveMessagesThatAUserHasSent(Long senderId)
			throws EntityDoesNotExistException {
		return messageRepository.retrieveMessagesThatAUserHasSent(senderId);
	}

	@Override
	public Long retrieveCount() {
		return messageRepository.retrieveCount();
	}

	@Override
	public boolean checkIfEntityExistsById(Long id) {
		return messageRepository.doesEntityExistById(id);
	}

	@Override
	public void updateEntity(Message entity) throws DecisionMakerException,
			EntityDoesNotExistException {
		messageRepository.updateEntity(entity);
	}

	@Override
	public void deleteEntityById(Long id) throws EntityDoesNotExistException,
			DecisionMakerException {
		messageRepository.deleteEntityById(id);
	}

}
