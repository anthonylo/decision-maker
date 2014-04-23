package com.quickfun.message.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quickfun.message.exception.DecisionMakerException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractDecisionMakerRepository<T, K> implements IRepository<T, K> {

	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Class<T> clazz;
	
	protected abstract void setClazz();
	
	public AbstractDecisionMakerRepository() {
		setClazz();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		setClazz();
	}

	@Override
	public void saveEntity(T entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public List<T> retrieveById(K id) throws DecisionMakerException {
		List<T> results = sessionFactory.getCurrentSession().createCriteria(clazz)
			.add(Restrictions.eq("id", id)).list();
		
		if (results.size() == 0) {
			throw new DecisionMakerException("The " + clazz.getSimpleName() + " with ID " + id + " does not exist.");
		}
		
		return results;
	}

	@Override
	public List<T> retrieveAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(clazz).list();
	}

	@Override
	public List<T> retrieveSubsetOfEndpoint(int startIdx, int count) {
		return sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.setFirstResult(startIdx).setMaxResults(count)
				.list();
	}

	@Override
	public boolean doesEntityExistById(K id) {
		Long count = (Long) sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.add(Restrictions.eq("id", id))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		
		return count > 0;
	}

	@Override
	public Long retrieveCount() {
		return (Long) sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.setProjection(Projections.rowCount())
				.uniqueResult();
	}

	@Override
	public void updateEntity(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public void deleteEntityById(K id) throws DecisionMakerException {
		List<T> entities = retrieveById(id);
		for (T entity : entities) {
			sessionFactory.getCurrentSession().delete(entity);
		}
	}
	
}
