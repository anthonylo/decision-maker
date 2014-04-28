package com.decision.maker.domain.message;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.decision.maker.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "dm_message")
public class Message extends AbstractDecisionMakerObject<Long> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3192375856186445618L;

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_message_seq")
	@SequenceGenerator(name = "dm_message_seq", sequenceName = "dm_message_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Id
	@Column(name = "user_id", insertable = true, updatable = false)
	@JsonIgnore
	private Long senderId;

	@Column(name = "message", length = 140, insertable = true, updatable = false)
	@JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "CST")
	private String message;
	
	@Column(name = "date_posted")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "CST")
	private Date datePosted;

	@Transient
	@JsonInclude(value = Include.NON_NULL)
	private User sender;
	
	@Transient
	@JsonInclude(value = Include.NON_NULL)
	private Set<User> recipients;

	@Transient
	@JsonIgnore
	private MessageUser userMessage;

	public Message() {
		
	}

	public Message(Long id, Long senderId, String message, Date datePosted,
			User sender, Set<User> recipients, MessageUser userMessage) {
		this.id = id;
		this.senderId = senderId;
		this.message = message;
		this.datePosted = datePosted;
		this.sender = sender;
		this.recipients = recipients;
		this.userMessage = userMessage;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Set<User> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<User> recipients) {
		this.recipients = recipients;
	}

	public MessageUser getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(MessageUser userMessage) {
		this.userMessage = userMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datePosted == null) ? 0 : datePosted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((recipients == null) ? 0 : recipients.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result
				+ ((senderId == null) ? 0 : senderId.hashCode());
		result = prime * result
				+ ((userMessage == null) ? 0 : userMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (datePosted == null) {
			if (other.datePosted != null)
				return false;
		} else if (!datePosted.equals(other.datePosted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (recipients == null) {
			if (other.recipients != null)
				return false;
		} else if (!recipients.equals(other.recipients))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		if (userMessage == null) {
			if (other.userMessage != null)
				return false;
		} else if (!userMessage.equals(other.userMessage))
			return false;
		return true;
	}
	
}