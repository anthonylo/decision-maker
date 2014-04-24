package com.decision.maker.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.decision.maker.domain.AbstractDecisionMakerObject;

@Entity
@Table(name = "dm_account_jumbo")
public class Account extends AbstractDecisionMakerObject<Long> {
	
	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 5133692849561290233L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id", insertable = false)
	private Long id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "secret_question", nullable = false)
	private String secretQuestion;
	
	@Column(name = "secret_answer", nullable = false)
	private String secretAnswer;

	public Account() {
		
	}
	
	public Account(Long id, String username, String password,
			String secretQuestion, String secretAnswer) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.secretQuestion = secretQuestion;
		this.secretAnswer = secretAnswer;
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
	
}
