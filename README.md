# Movies API


<p align="left">
 <a href="https://www.codefactor.io/repository/github/ejanerop/spring-movies-api"><img src="https://www.codefactor.io/repository/github/ejanerop/spring-movies-api/badge" alt="CodeFactor" /></a>
 
 <img src="https://github.com/ejanerop/spring-movies-api/actions/workflows/docker-container.yml/badge.svg" />
 
</p>

Restful API built in Spring Boot that allows users to search for movies and actors. It also allows users to add movies and actors to the database.

## Project Setup

Clone the repo:

```
git clone https://github.com/ejanerop/spring-movies-api.git
```

Configure the application.properties with your database credentials.

```
spring.datasource.url={DB url}
spring.datasource.username={DB username}
spring.datasource.password={DB password}
```

Build the project and run the tests:

```
mvn clean package
```

Run the project:

```
 mvn spring-boot:run
```

To access the documentation, go to http://localhost:8080/swagger-ui/index.html (or http://localhost:8080/ , you will be redirected to the documentation).

## Project Setup with Docker

Create a `.env` file with the following environment variables:

```
MYSQLDB_ROOT_PASSWORD={DB password}
MYSQLDB_DATABASE={DB name}
MYSQLDB_LOCAL_PORT={DB port}
MYSQLDB_DOCKER_PORT={Docker DB port}
MYSQLDB_USER={DB username}
```

Run the project with docker:

```
docker-compose up -d
```

## Authentication

The avaliables endpoints are:

#### Register a user

```
POST /register
Accept: application/json
Content-Type: application/json
Body:
{
  "username": String,
  "password": String,
  "rePassword": String,
}

RESPONSE: HTTP 201
{
  "status: String,
  "message": String,
  'ok": Boolean
}
```

#### Login

```
POST /login
Accept: application/json
Content-Type: application/json
Body:
{
  "username": "string",
  "password": "string",
}

RESPONSE: HTTP 200
{
  "id": Long,
  "name": String,
  "token": String,
}
```

## Endpoints

To have access to the API, you need to be authenticated. To do so, you must first create a user using the [Register endpoint](#authentication). Then, send a POST request to the `/login` endpoint. The response will contain a token that you need to send in the `Authorization` header of all your requests: `Bearer <token>`. The token will expire in 24 hours.

### Movies

The avaliables endpoints for movies are:

-   `GET /movies`
-   `GET /movies/{id}`
-   `POST /movies`
-   `PUT /movies/{id}`
-   `DELETE /movies/{id}`

### Persons

The avaliables endpoints for persons are:

-   `GET /persons/directors`
-   `GET /persons/actors`
-   `GET /persons/{id}`
-   `POST /persons`
-   `PUT /persons/{id}`
-   `DELETE /persons/{id}`
