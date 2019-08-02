package org.jwr.ee.dao;

import javax.ejb.Local;

import org.jwr.ee.entities.Todo;

/**
 * Provides an abstraction for Todo DAOs.
 */
@Local
public interface TodoDao extends GenericDao<Todo, Long>
{
	// Todo-specific method stubs
}
