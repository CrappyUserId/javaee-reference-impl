package org.jwr.ee;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Main entry into application, automatically picked up by JAX-RS due to annotation.
 * Leave blank to enable scanning for resources.
 */
@ApplicationPath("/")
public class ExampleApplication extends Application
{
	/*
	 * Left intentionally blank to enable scanning.  If you need to manually specify
	 * services, add them here as classes instead of singletons.  Singletons created 
	 * here will not register as EJBs, since they will not be container-managed.
	 */
}
