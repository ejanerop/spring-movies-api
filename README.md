# Movies API

Restful API built in Spring Boot that allows users to search for movies and actors. It also allows users to add movies and actors to the database.

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
