package com.decision.maker.repository;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.decision.maker.domain.AbstractDecisionMakerObject;
import com.decision.maker.exception.EntityDoesNotExistException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public abstract class AbstractDecisionMakerRepository<T extends AbstractDecisionMakerObject<K>, K extends Serializable>
	implements IRepository<T, K> {
	
	private static Logger log = Logger.getLogger(AbstractDecisionMakerRepository.class);
	
	@Value("${target.database}")
	private String targetDatabase;
	
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
	public Set<T> retrieveById(K id) throws EntityDoesNotExistException {
		log.info("Attempting to retrieve " + clazz.getSimpleName() + " by ID: " + id);
		log.info("The ID is a " + id.getClass().getName());
		log.info(ReflectionUtils.findField(clazz, "id"));
		List<T> listResults = sessionFactory.getCurrentSession().createCriteria(clazz)
			.add(Restrictions.eq("id", id)).list();
		
		Set<T> results = new LinkedHashSet<T>(listResults);
		
		if (results.size() == 0) {
			throw new EntityDoesNotExistException("The " + clazz.getSimpleName() 
					+ " with ID " + id + " does not exist.", 
					id.getClass(), id);
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
		sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public void deleteEntityById(K id) throws EntityDoesNotExistException {
		Set<T> entities = retrieveById(id);
		Iterator<T> iterator = entities.iterator();
		
		while (iterator.hasNext()) {
			sessionFactory.getCurrentSession().delete(iterator.next());
		}
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}
	
	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}
	
}
