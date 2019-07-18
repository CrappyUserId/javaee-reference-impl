package org.jwr.ee.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jwr.ee.entities.Todo;

/**
 * Sample class demonstrating how to combine a JAX-RS REST resource with an EJB 
 * that uses JPA and Container Managed Transactions (CMTs), which are enabled 
 * by default (thus no TransactionAttribute annotations are needed).
 */
@Path("/rest")
@Stateless
public class ExampleRestService
{
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Check service availability.
	 * @return success message.
	 */
	@GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() 
    {
        return Response.status(200).entity(this.getClass().getSimpleName() + " is running.").build();
    }
    
	/**
	 * Exercise database connection by executing a CMT using injected EntityManager.
	 * @return success message.
	 */
    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() 
    {
    	Todo todo = new Todo();
    	todo.setName("a new task");
    	todo.setDescription("let's do some stuff");
    	todo.setDone(false);
    	entityManager.persist(todo);
    	
        return Response.status(200).entity("Todo object created.").build();
    }
}
