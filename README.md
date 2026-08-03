# E-Commerce Backend API

A backend REST API for an e-commerce application built using Java, Spring Boot, Spring Security, JWT Authentication, and MySQL.

## Features

- User Registration
- User Login
- JWT Authentication
- Spring Security
- Role-Based Authorization
- RESTful APIs
- MySQL Database Integration
- Spring Data JPA
- Maven Project

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- MySQL
- Spring Data JPA
- Maven
- Git & GitHub

## Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── security
 ├── config
 └── resources
```

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/YashwanthNagireddy/ecommerce-backend-api.git
```

### Configure Database

Update the `application.properties` file with your MySQL username and password.

### Run the Project

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

## API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /auth/register | Register User |
| POST | /auth/login | Login User |

## Future Enhancements

- Product Management
- Shopping Cart
- Order Management
- Payment Integration
- Swagger Documentation
- Docker Support
- Unit Testing

## Author

**Yashwanth Nagireddy**

GitHub: https://github.com/YashwanthNagireddy
