# Demo TODO management application using Spring boot

The purpose of this project is to demonstrate the use of Spring Boot with a simple TODO management application. 

The underlying database is a H2 in-memory instance as per the following setting in **application.properties**

`spring.datasource.url=jdbc:h2:mem:tododb`

The data layer uses Spring Data with JPA entities for the core business objects

Validation is performed by the javax.validation annotations

The user interface uses Thymeleaf for templating. A separate ReST api can also be used.

## Performance 
While the business logic is trival (lookups of Todo and User entities), both the Todo and User entities are indexed as defined in their respective entity classes. Todo entities are indexed by owner while User entities are indexed by login since this is how those entities will be most commonly looked up.This ensures that lookups are as performant as possible.

## Security 
Security is implemnted using Spring Boot Security. A custom authorisation principal TodoAppUserPrincipal is provided to the controller layer. This in turn provides a User object which is used by the service layer to enforce business level access control.

Two Users are defined in data.sql as follows:

 - User bobs, password mypass, name Bob Smith
 - User fred, password mypass, name Fred Jones

The passwords are stored in cleartext and so are prefixed with {noop}. In production, bcrypt or similar would likely be used. Since the hashing algorithm is adaptive, code changes are not required to implement this.

## Testing
Unit tests are applied to the service layer and other elements of the business domain. Integration tests are applied to the web UI controller and to the ReST endpoints. 

## Error handling
Violations of business level constraints will result in a TodoException being thrown. There are three subclasses of this: 
 - TodoNotFoundException : Thrown when a user attempts a access a non-existant entry
 - TodoAccessException : Thrown when a user attemtps to access an entry that belongs to a different user
 - TodoStateException : Thrown when a state validaton check fails, for example, completing an entry that is already complete or deleting an entry that is not complete

For the web based UI, a generic error page is shown when one of these is thrown. In the case of the ReST API, the HTTP status will indicate the error that has occurred.

## Building and execution
The project can be built with a simple `mvn install` command. This results in a self contained executable jar that includes all dependencies. To run the application, use the command 

java -jar springtodo.jar

This will start up an instance of embedded tomcat listing on port 8822. The port is set using the **server.port** property in the **application.properties** file.

## Implementation Considerations
A number of the integration tests for the ReST API have been disabled. This is due to difficulties mocking the custom UserDetailsService. There are placholder tests for validating that the appropriate HTTP response is generated (for example, 404 when accessing a non existant entry).
