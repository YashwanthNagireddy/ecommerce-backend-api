# E-Commerce Backend API

A secure RESTful E-Commerce Backend API built using **Java, Spring Boot, Spring Security, JWT, MySQL, JPA/Hibernate, and Swagger/OpenAPI**.

The project provides authentication, role-based authorization, product and category management, shopping cart functionality, and order management through REST APIs.

## Features

* JWT-based authentication
* Role-based authorization with `ADMIN` and `USER`
* User registration and login
* Secure password hashing using Spring Security
* Product CRUD operations
* Category CRUD operations
* Product search
* Shopping cart management
* Order placement and order retrieval
* Admin order management
* Admin user management
* Swagger/OpenAPI API documentation
* MySQL database integration
* Input validation
* Centralized resource-not-found handling
* Stateless session management

## Tech Stack

| Technology        | Usage                            |
| ----------------- | -------------------------------- |
| Java 21           | Programming language             |
| Spring Boot 4.0.1 | Backend framework                |
| Spring MVC        | REST API                         |
| Spring Data JPA   | Database access                  |
| Hibernate         | ORM                              |
| Spring Security   | Authentication and authorization |
| JWT               | Stateless authentication         |
| MySQL 8           | Database                         |
| Maven             | Build and dependency management  |
| Swagger / OpenAPI | API documentation                |
| Lombok            | Boilerplate reduction            |

## Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── yashwanth/
    │           └── ecommerce/
    │               ├── config/
    │               ├── controller/
    │               ├── dto/
    │               ├── entity/
    │               ├── exception/
    │               ├── repository/
    │               ├── security/
    │               └── service/
    │
    └── resources/
        └── application.properties
```

## Authentication

The API uses JWT tokens for authentication.

After successful login, the API returns a JWT:

```json
{
  "token": "your-jwt-token"
}
```

Include the token in protected API requests:

```text
Authorization: Bearer <JWT_TOKEN>
```

The application supports two roles:

```text
ROLE_ADMIN
ROLE_USER
```

### ADMIN permissions

Administrators can:

* Create, update, and delete products
* Create, update, and delete categories
* View all users
* Delete users
* View all orders

### USER permissions

Regular users can:

* View products
* Search products
* View categories
* Add products to cart
* Manage their cart
* Place orders
* View their orders

Users cannot perform administrator-only operations.

## API Endpoints

### Authentication

| Method | Endpoint                         | Access |
| ------ | -------------------------------- | ------ |
| POST   | `/api/auth/register`             | Public |
| POST   | `/api/auth/login`                | Public |
| POST   | `/api/auth/reset-admin-password` | Public |

### Products

| Method | Endpoint                | Access       |
| ------ | ----------------------- | ------------ |
| GET    | `/api/products`         | USER / ADMIN |
| GET    | `/api/products/id/{id}` | USER / ADMIN |
| GET    | `/api/products/search`  | USER / ADMIN |
| POST   | `/api/products`         | ADMIN        |
| PUT    | `/api/products/id/{id}` | ADMIN        |
| DELETE | `/api/products/id/{id}` | ADMIN        |

### Categories

| Method | Endpoint           | Access       |
| ------ | ------------------ | ------------ |
| GET    | `/categories`      | USER / ADMIN |
| GET    | `/categories/{id}` | USER / ADMIN |
| POST   | `/categories`      | ADMIN        |
| PUT    | `/categories/{id}` | ADMIN        |
| DELETE | `/categories/{id}` | ADMIN        |

### Orders

| Method | Endpoint            | Access       |
| ------ | ------------------- | ------------ |
| POST   | `/orders/place`     | USER / ADMIN |
| GET    | `/orders/my-orders` | USER / ADMIN |
| GET    | `/orders/{id}`      | USER / ADMIN |
| GET    | `/orders`           | ADMIN        |

### Cart

| Method | Endpoint                | Access       |
| ------ | ----------------------- | ------------ |
| POST   | `/api/cart/{productId}` | USER / ADMIN |
| GET    | `/api/cart`             | USER / ADMIN |
| DELETE | `/api/cart`             | USER / ADMIN |
| DELETE | `/api/cart/{cartId}`    | USER / ADMIN |

### Users

| Method | Endpoint      | Access |
| ------ | ------------- | ------ |
| GET    | `/users`      | ADMIN  |
| GET    | `/users/{id}` | ADMIN  |
| DELETE | `/users/{id}` | ADMIN  |

### Home

| Method | Endpoint | Access |
| ------ | -------- | ------ |
| GET    | `/`      | Public |

## Swagger / OpenAPI

Swagger UI is available when the application is running:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger can be used to test the API directly from the browser.

For protected endpoints, click **Authorize** and provide:

```text
Bearer <JWT_TOKEN>
```

## Database

The application uses MySQL.

Create the database:

```sql
CREATE DATABASE ecommerce_db;
```

For local development, configure the database through environment variables rather than committing credentials to GitHub.

Example:

```properties
spring.application.name=ecommerce

server.port=${PORT:8080}

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.open-in-view=false

jwt.secret=${JWT_SECRET}
```

### Local environment variables

Example values for local development:

```text
DB_URL=jdbc:mysql://localhost:3306/ecommerce_db
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_long_random_jwt_secret
```

Do not commit actual database passwords or JWT secrets to the repository.

## Running Locally

### Prerequisites

Install:

* Java 21
* Maven
* MySQL 8+
* Git

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

### Clone the repository

```bash
git clone https://github.com/YashwanthNagireddy/ecommerce-backend-api.git
```

Enter the project:

```bash
cd ecommerce-backend-api
```

### Configure the database

Create:

```sql
CREATE DATABASE ecommerce_db;
```

Configure the required environment variables:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

### Build the project

```bash
mvn clean package
```

A successful build produces:

```text
target/ecommerce-0.0.1-SNAPSHOT.jar
```

### Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or using the executable JAR:

```bash
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

The application starts on:

```text
http://localhost:8080
```

## Example Registration

```json
{
  "name": "Security Test User",
  "email": "user@example.com",
  "password": "User@123"
}
```

Normal registration creates a user with:

```text
ROLE_USER
```

## Example Login

```json
{
  "email": "user@example.com",
  "password": "User@123"
}
```

Response:

```json
{
  "token": "your-jwt-token"
}
```

## Example Product

```json
{
  "name": "Samsung Galaxy S25",
  "description": "Samsung flagship smartphone",
  "price": 85000,
  "quantity": 10
}
```

Creating products requires the `ADMIN` role.

## Security Testing

The API has been tested for role-based authorization.

Example expected behavior:

```text
ADMIN
POST /api/products          → Allowed
PUT /api/products/id/{id}   → Allowed
DELETE /api/products/id/{id} → Allowed

USER
POST /api/products          → 403 Forbidden
PUT /api/products/id/{id}   → 403 Forbidden
DELETE /api/products/id/{id} → 403 Forbidden

USER
GET /api/products           → Allowed
GET /categories             → Allowed
```

JWT authentication is implemented through a custom authentication filter that loads the user from the database and establishes the Spring Security context.

## Error Handling

The application handles missing resources using a dedicated resource-not-found exception.

For example:

```text
Product not found
Category not found
User not found
Order not found
```

Invalid requests are returned with appropriate HTTP status codes.

## Build Status

The project currently builds successfully with Maven:

```text
BUILD SUCCESS
```

Executable JAR:

```text
target/ecommerce-0.0.1-SNAPSHOT.jar
```

## GitHub

Repository:

https://github.com/YashwanthNagireddy/ecommerce-backend-api

## Deployment

The project is structured for deployment as an executable Spring Boot JAR.

Production deployment requires:

1. A cloud MySQL database
2. Environment variables for database credentials
3. A secure JWT secret
4. A hosting platform capable of running the Spring Boot JAR

The application supports the deployment port through:

```properties
server.port=${PORT:8080}
```

## Future Improvements

Possible future enhancements include:

* Product image uploads
* Pagination and sorting
* Product categories linked directly to products
* Order status management
* Payment gateway integration
* Email notifications
* Refresh tokens
* Rate limiting
* Docker containerization
* Automated CI/CD
* Automated integration tests
* Frontend client

## Author

**Nagireddy Yashwanth Reddy**

B.Tech Computer Science & Engineering

### Project

**E-Commerce Backend API**

Built with:

```text
Java
Spring Boot
Spring Security
JWT
MySQL
JPA/Hibernate
Swagger/OpenAPI
Maven
```

---
