package com.decision.maker.domain.message;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.decision.maker.domain.user.User;

@Entity
@Table(name = "dm_message")
public class SendingMessage extends Message {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 5425205766831985677L;

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
	private Set<User> receivingUsers;
	
	public Set<User> getReceivingUsers() {
		return receivingUsers;
	}

	public void setReceivingUsers(Set<User> receivingUsers) {
		this.receivingUsers = receivingUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((receivingUsers == null) ? 0 : receivingUsers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendingMessage other = (SendingMessage) obj;
		if (receivingUsers == null) {
			if (other.receivingUsers != null)
				return false;
		} else if (!receivingUsers.equals(other.receivingUsers))
			return false;
		return true;
	}

}
