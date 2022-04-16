# Movies API

Restful API built in Spring Boot that allows users to search for movies and actors. It also allows users to add movies and actors to the database.

## Endpoints

The avaliables endpoints are:

### Authentication

#### `POST /register`

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
