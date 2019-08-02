package org.jwr.ee.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Provides basic CRUD methods.
 * @param <T> the object type for the implementing DAO.
 * @param <ID> the type of the identifier for the object.
 */
public interface GenericDao<T, ID extends Serializable>
{
    public T findById(ID id);      
    public List<T> findAll();
    public T save(T entity); 
    public T update(T entity);
    public void delete(T entity);
}
