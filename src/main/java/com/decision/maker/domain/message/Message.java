package com.decision.maker.domain.message;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.decision.maker.domain.user.User;

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

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	private User primaryUser;

	
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
	
	public Message(Long id, User primaryUser, Set<User> friendUser) {
		this.id = id;
		this.primaryUser = primaryUser;
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
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public User getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(User primaryUser) {
		this.primaryUser = primaryUser;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((friendUser == null) ? 0 : friendUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((primaryUser == null) ? 0 : primaryUser.hashCode());
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
		if (friendUser == null) {
			if (other.friendUser != null)
				return false;
		} else if (!friendUser.equals(other.friendUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (primaryUser == null) {
			if (other.primaryUser != null)
				return false;
		} else if (!primaryUser.equals(other.primaryUser))
			return false;
		return true;
	}

}
