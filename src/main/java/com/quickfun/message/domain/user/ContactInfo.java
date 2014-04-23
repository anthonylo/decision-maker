package com.quickfun.message.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.quickfun.message.domain.AbstractDecisionMakerObject;

@Entity
@Table(name = "dm_contact_info")
public class ContactInfo extends AbstractDecisionMakerObject<Long> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 4954388113996898719L;

	@Id
	@GeneratedValue
	@Column(name = "contact_info_id")
	private Long id;
	
	@Column(name = "email_address")
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	public ContactInfo() {
		
	}
	
	public ContactInfo(Long id, String email, String phoneNumber) {
		this.id = id;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
