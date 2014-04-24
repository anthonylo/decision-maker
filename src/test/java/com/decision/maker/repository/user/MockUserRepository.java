package com.decision.maker.repository.user;

import org.hibernate.SessionFactory;

import com.decision.maker.repository.user.UserRepository;

public class MockUserRepository extends UserRepository {

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
