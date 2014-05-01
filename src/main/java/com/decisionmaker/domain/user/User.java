package com.decisionmaker.domain.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.decisionmaker.domain.AbstractDecisionMakerObject;
import com.decisionmaker.domain.message.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "dm_user")
public class User extends AbstractDecisionMakerObject<Long> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 140145762237385729L;

	@Id
	@Column(name = "user_id", updatable = false, insertable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_user_seq")
	@SequenceGenerator(name = "dm_user_seq", sequenceName = "dm_user_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "first_name", nullable = false, updatable = true, insertable = true)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, updatable = true, insertable = true)
	private String lastName;
	
	@Column(name = "age", nullable = false, updatable = true, insertable = true)
	private Integer age;
	
	@OneToOne(optional = true, cascade = { CascadeType.ALL }, 
			fetch = FetchType.EAGER, targetEntity = ContactInfo.class)
	@JoinColumn(name = "contact_info_id", referencedColumnName = "contact_info_id")
	@JsonInclude(value = Include.NON_NULL)
	private ContactInfo contactInfo;
	
	@OneToOne(cascade = { CascadeType.ALL }, 
			fetch = FetchType.EAGER, targetEntity = Account.class)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id")
	@JsonInclude(value = Include.NON_NULL)
	private Account account;

	@Transient
	@JsonInclude(value = Include.NON_NULL)
	private Set<Message> messagesSent = null;
	
	@Transient
	@JsonInclude(value = Include.NON_NULL)
	private Set<Message> messagesReceived = null;

	public User() {
	}

	public User(Long id, String firstName, String lastName, Integer age,
			ContactInfo contactInfo, Account account,
			Set<Message> messagesSent, Set<Message> messagesReceived) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.contactInfo = contactInfo;
		this.account = account;
		this.messagesSent = messagesSent;
		this.messagesReceived = messagesReceived;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Message> getMessagesSent() {
		return messagesSent;
	}

	public void setMessagesSent(Set<Message> messagesSent) {
		this.messagesSent = messagesSent;
	}

	public Set<Message> getMessagesReceived() {
		return messagesReceived;
	}

	public void setMessagesReceived(Set<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((contactInfo == null) ? 0 : contactInfo.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime
				* result
				+ ((messagesReceived == null) ? 0 : messagesReceived.hashCode());
		result = prime * result
				+ ((messagesSent == null) ? 0 : messagesSent.hashCode());
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
		User other = (User) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (contactInfo == null) {
			if (other.contactInfo != null)
				return false;
		} else if (!contactInfo.equals(other.contactInfo))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (messagesReceived == null) {
			if (other.messagesReceived != null)
				return false;
		} else if (!messagesReceived.equals(other.messagesReceived))
			return false;
		if (messagesSent == null) {
			if (other.messagesSent != null)
				return false;
		} else if (!messagesSent.equals(other.messagesSent))
			return false;
		return true;
	}

}