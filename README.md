# Movies API

Restful API built in Spring Boot that allows users to search for movies and actors. It also allows users to add movies and actors to the database.

## Project Setup

Clone the repo:

```
git clone https://github.com/ejanerop/spring-movies-api.git
```

Configure the application.properties with your database credentials.

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

Build the project and run the tests:

```
mvn clean package
```

Run the project:

```
 mvn spring-boot:run
```

## Endpoints

### Authentication

The avaliables endpoints are:

#### `POST /register`

Params:

-   `username`: String
-   `password`: String
-   `rePassword`: String

Response:

```
{
  "id": 0,
  "username": "string",
  "password": "string",
  "authorities": [
    {
      "authority": "string"
    }
  ]
}
```

#### `POST /login`

Params:

-   `username`: String
-   `password`: String

Response:

```
{
  "id": 0,
  "username": "string",
  "token": "string",
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
