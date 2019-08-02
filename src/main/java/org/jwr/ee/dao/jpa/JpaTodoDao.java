package org.jwr.ee.dao.jpa;

import javax.ejb.Stateless;

import org.jwr.ee.dao.TodoDao;
import org.jwr.ee.entities.Todo;

/**
 * Implementation for a JPA-specific Todo DAO.
 * Injectable as a TodoDao-implementing EJB.
 */
@Stateless
public class JpaTodoDao extends JpaGenericDao<Todo, Long> implements TodoDao
{
	// Todo-specific method implementations
}
