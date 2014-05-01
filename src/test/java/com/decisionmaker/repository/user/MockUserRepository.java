package com.decisionmaker.repository.user;

import org.hibernate.SessionFactory;

import com.decisionmaker.repository.user.UserRepository;

public class MockUserRepository extends UserRepository {

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
