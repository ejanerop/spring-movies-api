# Movies API

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

Run the project with docker:

```
docker-compose up -d
```

## Endpoints

### Authentication

The avaliables endpoints are:

#### `POST /register`

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
  "id": 0,
  "username": String,
  "password": String,
  "authorities": [
    {
      "authority": String
    }
  ]
}
```

Response:

```
{
  "id": 0,
  "username": String,
  "password": String,
  "authorities": [
    {
      "authority": String
    }
  ]
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
