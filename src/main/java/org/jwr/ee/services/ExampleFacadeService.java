package org.jwr.ee.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jwr.ee.entities.Todo;
import org.jwr.ee.facade.DatabaseFacade;

/**
 * Sample class demonstrating how to combine a JAX-RS REST resource with an EJB
 * that uses an injected facade for data persistence.
 */
@Path("/facade")
@Stateless
public class ExampleFacadeService
{
	@EJB
	private DatabaseFacade db;
	
	/**
	 * Exercise database connection by executing a CMT using injected facade.
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
		db.save(todo);

		return Response.status(200).entity("Todo object created.").build();
	}
}
