
# Bank Management System

## Project Overview
This project is a Spring Boot application that provides a basic banking system with functionalities for user and employee account management, bank account transactions, and secure data handling.

## Technologies Used
- **Backend**: Java, Spring Boot, Spring Data JPA
- **Database**: MySQL
- **Security**: Spring Security with role-based access
- **Testing**: JUnit

## Key Functionalities
1. **User Account Management**
   - User creation, updating, and deletion.
   - Bank account creation for various types (Personal, Savings, Business).

2. **Employee Account Management**
   - Employee creation, updating, and deletion with HR PIN validation.
   - Viewing and managing user accounts.

3. **Bank Transactions**
   - Deposit, withdrawal, and fund transfer operations.
   - Scheduled interest application for savings accounts meeting criteria.

## Database Schema (ERD)
The ERD for this project consists of tables: `User`, `Employee`, and `Account`.

## UML Diagram
A high-level UML class diagram for the services and controllers implemented in this project.

## Getting Started
### Prerequisites
- MySQL installed and running.
- Java 17+
- Maven

### Running the Application
1. Configure your MySQL credentials in `application.properties`.
2. Run `mvn spring-boot:run` to start the application.

### Testing the Application
Run `mvn test` to execute unit tests for various services and controllers.

## Endpoints Overview
- **User Controller**: `/api/users` for user management.
- **Employee Controller**: `/api/employees` for employee management with HR validation.
- **Account Controller**: `/api/accounts` for bank account operations.

