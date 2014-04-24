package com.decision.maker.domain.message;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.decision.maker.domain.message.key.MessagePK;
import com.decision.maker.domain.user.User;

@Entity
@Table(name = "dm_message")
public class Message extends AbstractDecisionMakerObject<MessagePK> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = -3192375856186445618L;

	@EmbeddedId
	private MessagePK id;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "dm_message_user", 
		joinColumns = { 
			@JoinColumn(name = "message_id", 
					referencedColumnName="message_id", insertable = false, updatable = false),
			@JoinColumn(name = "user_id",
					referencedColumnName = "user_id", insertable = false, updatable = false) }, 
		inverseJoinColumns = { 
			@JoinColumn(name = "friend_id", 
					referencedColumnName="user_id", insertable = false, updatable = false) }
	)
	private Set<User> friendUser;
//	
//	@Column(name = "message", length = 140, insertable = true, updatable = false)
//	private String message;
//	
//	@Column(name = "date_posted")
//	private Date datePosted;
//	
	public Message() {
		
	}
	
	public Message(MessagePK id, Set<User> friendUser) {
		this.id = id;
		this.friendUser = friendUser;
	}


//
//	public Message(Long id, User primaryUser, User friendUser,
//			String message, Date datePosted) {
//		this.id = id;
//		this.primaryUser = primaryUser;
//		this.friendUser = friendUser;
//		this.message = message;
//		this.datePosted = datePosted;
//	}
//
	@Override
	public MessagePK getId() {
		return id;
	}

	@Override
	public void setId(MessagePK id) {
		this.id = id;
	}

	public Set<User> getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(Set<User> friendUser) {
		this.friendUser = friendUser;
	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public Date getDatePosted() {
//		return datePosted;
//	}
//
//	public void setDatePosted(Date datePosted) {
//		this.datePosted = datePosted;
//	}
	
}
