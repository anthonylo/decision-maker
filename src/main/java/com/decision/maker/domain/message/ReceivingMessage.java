package com.decision.maker.domain.message;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.decision.maker.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "dm_message")
public class ReceivingMessage extends Message {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 6717241418145968606L;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
	@JsonInclude(Include.NON_EMPTY)
	private User primaryUser;
	
	public User getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(User primaryUser) {
		this.primaryUser = primaryUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((primaryUser == null) ? 0 : primaryUser.hashCode());
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
		ReceivingMessage other = (ReceivingMessage) obj;
		if (primaryUser == null) {
			if (other.primaryUser != null)
				return false;
		} else if (!primaryUser.equals(other.primaryUser))
			return false;
		return true;
	}

}
