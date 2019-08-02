package org.jwr.ee.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JPA-specific implementation of all persistence methods 
 * needed in an application.
 */
@Stateless
public class JpaDatabaseFacade implements DatabaseFacade
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public <T> T save(T type)
	{
		entityManager.persist(type);
		return type;
	}

	@Override
	public <T> T find(Class<T> type, Long id)
	{
		return entityManager.find(type, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> type)
	{
		return entityManager.createQuery("Select t from " + type.getSimpleName() + " t").getResultList();
	}

	@Override
	public <T> T update(T type)
	{
		entityManager.merge(type);
		return type;
	}

	@Override
	public <T> void delete(T type)
	{
		entityManager.remove(type);		
	}
}
