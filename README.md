# JAX-RS, JPA, and EJB3.1 Reference Implementation

Web application which demonstrates how to structure a project that provides a RESTful web interface using JAX-RS, using JPA for persistence, and EJB3.1 technology for dependency injection and Container-Managed Transactions (CMTs).  Demonstrates three separate methods for data access (direct JPA, a facade, and DAOs).

## Background

JavaEE, JPA, EJBs, and Hibernate are all well known frameworks for developing enterprise software, some of which are nearly 20 years old.  Each has matured and changed significantly over its long lifespan, supporting numerous configurations, integration approaches, dependencies, and design philosophies.  As a result, a large portion of examples and tutorials on the web for each contain outdated approaches, making it difficult to sort though available information to determine the current best practices for each.  For example, the following is a list of some of the decisions JavaEE application developers must make:

* JAX-RS
	* RESTEasy vs. another provider
	* Annotation-defined vs. web.xml-defined
	* Container-provided implementation vs. application-defined
* Persistence
	* Native Hibernate API (hibernate.cfg.xml)
		* Sessions vs. CurrentSession
	* JPA (persistence.xml)
		* Inject PersistenceContext vs. programmatically create it
		* Container-provided datasource vs. application-defined
		* CMTs vs. BMTs
		* Native JPA vs. DAOs vs. facades
* EJBs
	* Local/remote interface-based vs. no-interface
	* Exclusively annotation-defined vs. xml-defined
	* Interactions with JAX-RS and JPA objects

This small application demonstrates a modern, minimalistic approach to creating a JavaEE web application which uses JAX-RS for RESTful communications, JPA for database connectivity, and EJBs to provide CMTs.  CMTs allow the container (Wildfly in our case) to handle any transaction demarcation, freeing the developer from having to use repetitive code to manage them manually.  This application uses the latest versions and current recommended best practices for each constituent framework as of 2019.

Key files and packages:

* src/main/java/org/jwr/ee/ExampleApplication.java
	* Main entry point into JAX-RS application.  @ApplicationPath annotation describes root URL path and identifies it to the container.
* src/main/java/org/jwr/ee/services/ExampleRestService.java
	* JAX-RS REST service (specified by @Path) which is also an EJB (specified by @Stateless).  This enables us to provide REST methods while also leveraging an injected @PersistenceContext to use JPA.  CMTs are used for EJBs by default, so there is no need to specifically mandate them.
	* Two other services exist in the same package, one for an example of a facade and one for a DAO example.
* src/main/java/org/jwr/ee/entities/Todo.java
	* JPA entity which serves as a sample object to persist.  Only required annotations are present; defaults are assumed for others.
* src/main/java/org/jwr/ee/facade
	* Demonstrates how to create an EJB-aware database facade.
* src/main/java/org/jwr/ee/dao
	* Demonstrates how to create an EJB-aware DAO hierarchy.
* src/main/resources/META-INF/persistence.xml
	* JPA specification file that uses a container-provided datasource (<jta-data-source>), and indicates CMTs should be used (<transaction-type>).  Also includes several Hibernate-specific properties.

## Setup

1. Install the latest version of Wildfly.
2. Install your preferred SQL-based database (Postgres was used for this example).
3. Install a JDBC driver for your database as a module in your Wildfly installation.
4. Create a datasource in your Wildfly standalone.xml configuration file and configure it to use the installed JDBC driver.
5. Modify persistence.xml based on your datasource if necessary.

## Deployment

1. In the root directory of this project, run `mvn package`.
2. Copy the WAR file in the `target` directory into the deployment directory of your Wildfly instance.
3. In a browser, navigate to `http://[YourWildflyIP]:8080/example/rest/ping` to view the status message.
4. Navigate to `http://[YourWildflyIP]:8080/example/rest/test` to create an object and have JPA persist it to the database.
5. The URLs `http://[YourWildflyIP]:8080/example/facade/test` and `http://[YourWildflyIP]:8080/example/dao/test` behave the exact same way as the previous step, but use a facade and DAO design pattern respectively.

## Thoughts on DAOs

Data Access Objects (DAOs) are abstractions that provide an interface for persisting objects that is separate from the underlying implementation.  This application uses no DAOs at all - given that JPA in and of itself is an abstraction, there is a valid argument to be made that using JPA's EntityManager in application logic is reasonable and adding DAOs on top of that would just unnecessarily complicate the code.  However there are advantages and disadvantages to each approach, and each has its place.

* Direct JPA access
	* Simplest with the least overhead
	* Coupled to JPA
	* Complex queries embedded in business logic
	* Best for smaller, simpler applications
* Facade (a single interface/implementation pair that provides CRUD methods as well as application-specific ones)
	* Decouples JPA from business logic
	* Injectable using EJB
	* Likely suitable for most applications
* Traditional DAOs (generic DAO interface, generic DAO implementation, interfaces and implementations for each POJO)
	* Lots of extra code
	* Likely overkill for smaller applications
	* Best choice for large applications with complex data models