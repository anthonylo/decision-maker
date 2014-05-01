package com.decisionmaker.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.decisionmaker.domain.AbstractDecisionMakerObject;

@Entity
@Table(name = "dm_contact_info")
public class ContactInfo extends AbstractDecisionMakerObject<Long> {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 4954388113996898719L;

	@Id
	@Column(name = "contact_info_id", updatable = false, insertable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_contact_info_seq")
	@SequenceGenerator(name = "dm_contact_info_seq", sequenceName = "dm_contact_info_seq", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "email_address", nullable = true, updatable = true, insertable = true)
	private String email;
	
	@Column(name = "phone_number", nullable = true, updatable = true, insertable = true)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		ContactInfo other = (ContactInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

}