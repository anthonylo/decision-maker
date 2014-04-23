package com.quickfun.message.repository.user;

import org.hibernate.SessionFactory;

public class MockUserRepository extends UserRepository {

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
