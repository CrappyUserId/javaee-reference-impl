package org.jwr.ee.facade;

import java.util.List;

import javax.ejb.Local;

/**
 * Provides a single abstraction for persistence methods, including
 * generic CRUD.  Application-specific persistence query stubs
 * can be added here.
 */
@Local
public interface DatabaseFacade
{
	public <T> T save(T type);
	public <T> T find(Class<T> type, Long id);
	public <T> List<T> findAll(Class<T> type);
	public <T> T update(T type);
	public <T> void delete(T type);
	
	// Any other application-specific methods would follow...
}
