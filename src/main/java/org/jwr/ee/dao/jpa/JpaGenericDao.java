package org.jwr.ee.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jwr.ee.dao.GenericDao;

/**
 * JPA-specific implementation of CRUD methods for extension by
 * concrete JPA DAOs.
 * @param <T> the object type for the extending DAO.
 * @param <ID> the type of the identifier for the object.
 */
@Stateless
public abstract class JpaGenericDao<T, ID extends Serializable> implements GenericDao<T, ID>
{
	@PersistenceContext
	private EntityManager entityManager;	
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public JpaGenericDao()
	{
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()  
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T findById(ID id)
	{
		return entityManager.find(persistentClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
	}

	@Override
	public T save(T entity)
	{
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity)
	{
		entityManager.merge(entity);
		return entity;
	}

	@Override
	public void delete(T entity)
	{
		entityManager.remove(entity);		
	}
}
