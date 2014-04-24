package com.decision.maker.repository.user;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decision.maker.domain.user.User;
import com.decision.maker.exception.DecisionMakerException;
import com.decision.maker.repository.AbstractDecisionMakerRepository;

@Repository
@Transactional
@Qualifier("userRepository")
public class UserRepository extends AbstractDecisionMakerRepository<User, Long> implements IUserRepository {
	
	@Override
	public User retrieveUserByUsername(String username) throws DecisionMakerException {
		User user = (User) sessionFactory.getCurrentSession()
			.createCriteria(clazz)
			.createAlias("account", "acc")
			.add(Restrictions.eq("acc.username", username))
			.uniqueResult();
		
		if (user == null) {
			throw new DecisionMakerException("The user " + username + " does not exist");
		}
		return user;
	}

	@Override
	public boolean checkIfUsernameAlreadyExists(String username) {
		Long count = (Long) sessionFactory.getCurrentSession().createCriteria(clazz)
				.createAlias("account", "acc")
				.add(Restrictions.eq("acc.username", username))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count > 0;
	}

	@Override
	public User retrieveRandom() {
		String hql = "select u from User u order by rand()";
		return (User) sessionFactory.getCurrentSession().createQuery(hql).setMaxResults(1).uniqueResult();
	}
	
	@Override
	protected void setClazz() {
		this.clazz = User.class;
	}

}
