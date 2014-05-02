package com.decisionmaker.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.junit.Before;
import org.junit.Test;

import com.decisionmaker.domain.DummyObject;
import com.decisionmaker.exception.EntityDoesNotExistException;

public class AbstractDecisionMakerRepositoryTest {

	private AbstractDecisionMakerRepository<DummyObject, Long> repository;
	private Class<DummyObject> targetClazz = DummyObject.class;

	private SessionFactory mockSessionFactory;
	private Session mockSession;
	
	private String mockDatabase = "fake";

	@Before
	public void setUp() {
		repository = new MockAbstractDecisionMakerRepository();

		mockSessionFactory = mock(SessionFactory.class);
		mockSession = mock(Session.class);

		when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);

		repository.setSessionFactory(mockSessionFactory);
	}

	@Test
	public void check_getter_and_setters_work() {
		assertNull(repository.getTargetDatabase());
		repository.setTargetDatabase(mockDatabase);
		assertEquals(repository.getTargetDatabase(), mockDatabase);
	}
	
	@Test
	public void should_save_object_to_database() {
		// Given
		DummyObject dummyObject = new DummyObject();
		dummyObject.setId(1L);

		// When
		repository.saveEntity(dummyObject);

		// Then
		verify(mockSessionFactory).getCurrentSession();
		verify(mockSession).save(dummyObject);
	}

	@Test
	public void should_retrieve_entity_from_database_with_an_id() throws EntityDoesNotExistException {
		// Given
		Long target = 1L;
		DummyObject dummyObject = new DummyObject(target);

		dummyObject.setId(target);
		List<DummyObject> mockList = new ArrayList<DummyObject>();
		Criteria mockCriteria = mock(Criteria.class);
		mockList.add(dummyObject);

		// When

		when(mockSession.createCriteria(DummyObject.class)).thenReturn(mockCriteria);
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.list()).thenReturn(mockList);

		Set<DummyObject> result = repository.retrieveById(target);

		// Then
		verify(mockSessionFactory).getCurrentSession();

		assertEquals(result.iterator().next(), dummyObject);
	}

	@Test(expected = EntityDoesNotExistException.class)
	public void should_retrieve_entity_from_database_that_doesnt_exist() throws EntityDoesNotExistException {
		// Given
		Long target = 1L;
		List<DummyObject> mockResults = new ArrayList<DummyObject>();
		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.list()).thenReturn(mockResults);

		repository.retrieveById(target);

		// Then
		verify(mockSessionFactory).getCurrentSession();
		verify(mockCriteria).add((Criterion) anyObject());
		// should expect an exception to get thrown
	}

	@SuppressWarnings("unchecked")
	@Test
	public void should_retrieve_all_rows_in_the_database() {
		// Given
		List<DummyObject> mockResults = mock(List.class);
		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockResults.size()).thenReturn(5);

		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);
		when(mockCriteria.list()).thenReturn(mockResults);

		List<DummyObject> result = repository.retrieveAll();

		// Then
		verify(mockSessionFactory).getCurrentSession();
		assertNotNull(result);
		assertTrue(result.size() == 5);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void should_retrieve_subset_of_rows_in_the_database() {
		// Given
		int startIdx = 25;
		int count = 25;
		List<DummyObject> mockResults = mock(List.class);
		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockResults.size()).thenReturn(15);

		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);
		when(mockCriteria.setFirstResult(startIdx)).thenReturn(mockCriteria);
		when(mockCriteria.setMaxResults(count)).thenReturn(mockCriteria);
		when(mockCriteria.list()).thenReturn(mockResults);

		// Then
		List<DummyObject> result = repository.retrieveSubsetOfEndpoint(startIdx, count);

		verify(mockSessionFactory).getCurrentSession();
		verify(mockCriteria).setFirstResult(startIdx);
		verify(mockCriteria).setMaxResults(count);

		assertNotNull(result);
		assertTrue(result.size() == 15);
		// should expect an exception to get thrown
	}

	@Test
	public void should_check_that_an_existing_id_exists_in_the_database() {
		// Given
		Long target = 1L;
		Object resultCount = 2L;

		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.setProjection((Projection) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.uniqueResult()).thenReturn(resultCount);

		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);

		boolean result = repository.doesEntityExistById(target);

		// Then
		verify(mockSessionFactory).getCurrentSession();

		assertTrue(result);
	}

	@Test
	public void should_check_if_a_non_existant_id_exists_in_the_database() {
		// Given
		Long target = 1L;
		Object resultCount = 0L;

		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.setProjection((Projection) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.uniqueResult()).thenReturn(resultCount);

		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);

		boolean result = repository.doesEntityExistById(target);

		// Then
		verify(mockSessionFactory).getCurrentSession();

		assertFalse(result);
	}

	@Test
	public void should_retrieve_the_row_count_of_a_certain_table_in_the_database() {
		// Given
		Object resultCount = 15L;

		Criteria mockCriteria = mock(Criteria.class);

		// When
		when(mockCriteria.setProjection((Projection) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.uniqueResult()).thenReturn(resultCount);

		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);

		Long result = repository.retrieveCount();

		// Then
		verify(mockSessionFactory).getCurrentSession();

		assertEquals(Long.valueOf("15"), result);
	}

	@Test
	public void should_update_entity_in_database() {
		// Given
		DummyObject obj = new DummyObject();
		obj.setId(1L);

		// When
		repository.updateEntity(obj);

		// Then
		verify(mockSessionFactory).getCurrentSession();
		verify(mockSession).merge(obj);
	}

	@Test(expected = EntityDoesNotExistException.class)
	public void should_throw_decision_exception_error_when_deleting_a_fake_id() throws EntityDoesNotExistException {
		// Given
		Criteria mockCriteria = mock(Criteria.class);
		List<DummyObject> mockResults = new ArrayList<DummyObject>();

		// When
		when(mockSession.createCriteria(targetClazz)).thenReturn(mockCriteria);
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.list()).thenReturn(mockResults);

		// Then
		repository.deleteEntityById(15L);
	}

	@Test
	public void should_delete_row_from_table() throws EntityDoesNotExistException {
		// Given
		Criteria mockCriteria = mock(Criteria.class);
		Query mockQuery = mock(Query.class);
		Long count = 5L;
		Long id = 1L;

		// When
		when(mockSession.createCriteria(repository.clazz)).thenReturn(mockCriteria);

		// For doesEntityExistById
		when(mockCriteria.add((Criterion) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.setProjection((Projection) anyObject())).thenReturn(mockCriteria);
		when(mockCriteria.uniqueResult()).thenReturn(count);
		
		// For deleteById
		when(mockSession.createQuery(anyString())).thenReturn(mockQuery);
		when(mockQuery.setParameter("id", id)).thenReturn(mockQuery);
		when(mockQuery.executeUpdate()).thenReturn(count.intValue());

		// Then
		int result = repository.deleteEntityById(1L);
		
		assertTrue(result == 5);
	}

}