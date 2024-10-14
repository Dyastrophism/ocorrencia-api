# Welcome to the Occurrences API!

This project consists of a REST API for registering occurrences, clients, and addresses in a PostgreSQL database, with the possibility of uploading photos to an S3 file server.

## How to run and test the project

### Prerequisites

- Java SDK
- Maven
- Docker
- Docker Compose

### How to run the project

In a terminal, navigate to the project folder and run the command:

```shell
docker compose up -d
```

This command will start the PostgreSQL database, the S3 file server, and the Spring server.

### How to access the API documentation

The project is documented with Swagger. To access the API documentation, 
simply go to [Swagger UI](http://localhost:8080/swagger-ui/index.html).

To send requests, you need to authenticate. To do this, send a POST request to the
`/users/register` endpoint with the request body containing the user's login and password:

```shell
curl -X 'POST' \
'http://localhost:8080/users/register' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"login": "admin",
"password": "admin"
}'
```

or via Swagger UI: 
user-controller -> POST /users/register -> Try it out

```json
{
  "login": "admin",
  "senha": "admin"
}
```

After creating the user, authenticate at the `/users/login` endpoint and copy the generated token.

```shell
curl -X 'POST' \
  'http://localhost:8080/users/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "login": "admin",
  "password": "admin"
}'
```

or via Swagger UI: 
user-controller -> POST /users/login -> Try it out

```json
{
  "login": "admin",
  "password": "admin"
}
```

With the token in hand, add it as a 'Bearer token' authorization parameter in the request headers. 
In Swagger UI, click the 'Authorize' button and enter the token in the 'Value' field.

## Project development schedule:

I decided to adopt this order of priority according to my knowledge, thus speeding up the 
development of the project to a functional point and allowing time to study new concepts.

### Part 1

- **[OK]** Plan and outline the project.
- **[OK]** Create the project.
- **[OK]** Configure Docker environments, database, and file server.
- **[OK]** Configure Flyway.

### Part 2

- **[OK]** Configure CRUD for Client.
- **[OK]** Configure CRUD for Address.

### Part 3

- **[OK]** Configure CRUD for Occurrences.
- **[OK]** Unit tests for Occurrences.
- **[OK]** Service for uploading and downloading Occurrence Photos.

### Part 4
- **[OK]** Configure authentication.

### Part 5

- **[OK]** Configure CRUD for Occurrences.
- **[OK]** Service for uploading and downloading Occurrence Photos.
- **[OK]** Endpoint to filter occurrences.
- **[OK]** Unit tests for Client.
- **[OK]** Unit tests for Address.
- **[OK]** Configure Compose to start Spring.
- **[OK]** Final adjustments and documentation.
- **[OK]** Upload the project to GitHub.