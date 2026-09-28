Loan Management System

Backend API for managing loans, customers, loan officers, repayments and loan records.

Technologies Used

Java 17

Spring Boot

Spring Data JPA

Spring Security

JWT Authentication

PostgreSQL

Maven

Swagger / OpenAPI

Docker

JUnit 5

Mockito

Main Features

Authentication

Customer registration and login

JWT based authentication

Role based authorization

Roles: CUSTOMER, LOAN_OFFICER, ADMIN

Customer

Apply for a loan

View own loan applications

Upload loan documents

View payment history

Repay EMI

Download loan statement

Loan Officer

View loan applications

Search and filter loans

Review loan applications

Approve or reject applications with remarks

Calculate EMI

Generate EMI schedule

Close completed loans

Admin

Manage users

Manage loan types

Manage interest rates

View loan reports

Loan Types

The system supports configurable loan types such as:

Home Loan

Personal Loan

Education Loan

Vehicle Loan

Loan type information includes interest rate, maximum amount and maximum tenure.

Database

The main tables are:

roles

users

loan_types

loan_applications

loan_documents

emi_schedule

payments

loan_history

The ER diagram is included in the project documentation.

API Documentation

Swagger UI:

http://localhost:9095/swagger-ui/index.html

OpenAPI JSON:

http://localhost:9095/v3/api-docs

Configuration

Update PostgreSQL credentials in:

src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/loan_management_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD

server.port=9095

JWT configuration is also present in application.properties.

Running the Project

Using Maven

mvn clean install
mvn spring-boot:run

Using the JAR

java -jar target/loanmanagement-0.0.1-SNAPSHOT.jar

The application runs on:

http://localhost:9095

Docker

Build and start the application using:

docker compose up --build

The application will be available on port 9095 and PostgreSQL runs in the Docker container.

To stop the containers:

docker compose down

Testing

Unit tests are implemented using JUnit 5 and Mockito.

Run tests with:

mvn test

Project Structure

src/main/java/com/example/loanmanagement
├── controller
├── service
│   └── serviceimpl
├── repository
├── entity
├── dto
├── security
├── exception
├── config
└── util

Security

Protected APIs require a JWT token.

In Swagger, use the Authorize button and enter the JWT token received from the login API.

Role based access is applied to customer, loan officer and admin APIs.

Postman Collection

A Postman collection is included with the project for testing the APIs.

Notes

Loan applications follow a status based approval workflow.

EMI is calculated using the reducing balance method.

Loan documents support file upload and soft delete.

Global exception handling is used for API errors.

Pagination and filtering are available for loan officer loan records