# Store manager

## Overview

The store manager offers the possibility to create and update new stores through its REST API interface:
- creates a new store
- updates an existing store
- deletes an existing store
- lists all stores

## Technology stack

- for backend: RESTful service with Java 17 and Spring Boot
- for security: it uses @PreAuthorize with role ADMIN for creating, updating, and deleting of stores
- it uses H2 as in memory database in order to have a fast development and testing
- it uses Hibernate as ORM with JPA
- for data validation it uses Jakarta persistentence API and Jakarta validation e.g. for the requests that receive data
in the body of the request
- Maven is used as the build tool
- Lombok is used for minimizing the boilerplate code for getters, setters,++

It features error handling with consistent error codes and error messages being returned in the request in the following
cases:
- when there is no store to be retrieved, then 404 HTTP status code with an appropiate error code and error message is
returned
- when we try to update or delete a store that does not exist e.g. wrong store ID, then an appropriate error code and 
message is returned
- when we try to update a store with invalid/incomplete data, then the validation fails and an appropriate error code 
and message is returned
- when we try to create the same store i.e. with the same store name (which MUST be unique), then an appropriate error 
code and message is returned

## TODO: 
- **OpenAPI/Swagger UI** should be added too which improves service documentation and allows clients to do test
calls through it's 'Try it out' out of the box functionality