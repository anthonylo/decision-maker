package com.decision.maker.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "dm_account_jumbo")
@JsonInclude(value = Include.NON_NULL)
public class Account extends AbstractDecisionMakerObject<Long> {
	
	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 5133692849561290233L;
	
	@Id
	@Column(name = "account_id", insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_account_jumbo_seq")
	@SequenceGenerator(name = "dm_account_jumbo_seq", sequenceName = "dm_account_jumbo_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "username", unique = true, nullable = false, updatable = true)
	private String username;
	
	@Column(name = "password", nullable = false, updatable = true)
	private String password;
	
	@Column(name = "secret_question", nullable = false, updatable = true)
	private String secretQuestion;
	
	@Column(name = "secret_answer", nullable = false, updatable = true)
	private String secretAnswer;
	
	@Column(name = "date_created", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "CST")
	private Date dateCreated;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "active", insertable = false, updatable = true)
	private Boolean active;

	public Account() {
		
	}
	
	public Account(Long id, String username, String password,
			String secretQuestion, String secretAnswer, Date dateCreated,
			Boolean active) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
		this.dateCreated = dateCreated;
		this.active = active;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result
				+ ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((secretAnswer == null) ? 0 : secretAnswer.hashCode());
		result = prime * result
				+ ((secretQuestion == null) ? 0 : secretQuestion.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Account other = (Account) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (secretAnswer == null) {
			if (other.secretAnswer != null)
				return false;
		} else if (!secretAnswer.equals(other.secretAnswer))
			return false;
		if (secretQuestion == null) {
			if (other.secretQuestion != null)
				return false;
		} else if (!secretQuestion.equals(other.secretQuestion))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}