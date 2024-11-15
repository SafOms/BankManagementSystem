# Bank Management System

## Introduction
This project is a Bank Management System built using Spring Boot and MySQL. It allows the creation and management of bank user accounts, employee accounts, and provides various banking operations such as deposits, withdrawals, account creation, and more.

The project does not include a graphical user interface (GUI). Instead, users can interact with the system using REST API endpoints, which can be tested via Postman.

## Setup Instructions

### Prerequisites
- Java 17 or above
- Maven
- MySQL Server

### Steps to Set Up Locally
1. **Clone the repository**:
   ```sh
   git clone https://github.com/SafOms/BankManagementSystem.git
   cd BankManagementSystem
   ```

2. **Configure MySQL Database**:
   - Create a new database called `bank_db2`.
   - Update the `src/main/resources/application.properties` file with your MySQL username and password:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/bank_db2
     spring.datasource.username=YOUR_USERNAME
     spring.datasource.password=YOUR_PASSWORD
     spring.jpa.hibernate.ddl-auto=update
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     ```

3. **Build the Project**:
   ```sh
   mvn clean install
   ```

4. **Run the Application**:
   ```sh
   mvn spring-boot:run
   ```

## Database Setup
To initialize the database with some sample data, use the provided SQL scripts in `src/main/resources/data.sql`. This will populate tables with users, accounts, and employee records for testing.

## Endpoints Overview
### User Endpoints
- **Create User**: 
  - **URL**: `/api/users/create`
  - **Method**: `POST`
  - **Request Body**: `{ "fullName": "John Doe", "email": "john@example.com", "address": "123 Main St" }`

- **Get User by ID**: 
  - **URL**: `/api/users/{id}`
  - **Method**: `GET`

- **Get All Users**: 
  - **URL**: `/api/users/all`
  - **Method**: `GET`

- **Update User**: 
  - **URL**: `/api/users/{id}`
  - **Method**: `PUT`
  - **Request Body**: `{ "fullName": "John Smith", "email": "john.smith@example.com", "address": "456 Main St" }`

- **Delete User**: 
  - **URL**: `/api/users/{id}`
  - **Method**: `DELETE`

### Account Endpoints
- **Create Account**: 
  - **URL**: `/api/accounts/create`
  - **Method**: `POST`
  - **Request Params**: `userId`, `initialBalance`, `accountType`

- **Deposit Money**: 
  - **URL**: `/api/accounts/{accountId}/deposit`
  - **Method**: `PUT`
  - **Request Params**: `amount`

- **Withdraw Money**: 
  - **URL**: `/api/accounts/{accountId}/withdraw`
  - **Method**: `PUT`
  - **Request Params**: `amount`

- **Close Account**: 
  - **URL**: `/api/accounts/{accountId}/close`
  - **Method**: `DELETE`

### Employee Endpoints
- **Create Employee**: 
  - **URL**: `/api/employees/create`
  - **Method**: `POST`
  - **Request Params**: `name`, `email`, `position`, `hrPin`

- **Get Employee by ID**: 
  - **URL**: `/api/employees/{id}`
  - **Method**: `GET`

- **Get All Employees**: 
  - **URL**: `/api/employees/all`
  - **Method**: `GET`

- **Update Employee**: 
  - **URL**: `/api/employees/{id}`
  - **Method**: `PUT`
  - **Request Params**: `name`, `email`, `position`

- **Delete Employee**: 
  - **URL**: `/api/employees/{id}`
  - **Method**: `DELETE`
  - **Request Params**: `hrPin`

## Testing Instructions
To test the endpoints, use Postman to send HTTP requests to the running application.
1. **Start the Application**.
2. **Open Postman**.
3. **Create Requests** for each endpoint using the provided details above.
   - Make sure to use the correct HTTP method (GET, POST, PUT, DELETE).
   - For endpoints requiring parameters or request bodies, provide the necessary data.

### Example Test Cases
- **Create a User**: Use the `/api/users/create` endpoint to add a new user.
- **Create an Account for the User**: Use `/api/accounts/create` with the `userId` from the previously created user.
- **Deposit Money**: Use `/api/accounts/{accountId}/deposit` to deposit money into the user's account.

## Diagrams
The `src/documentation` folder contains the following diagrams:
- **ERD**: Describes the relationships between `User`, `Account`, and `Employee`.
- **Class Diagram (UML)**: Shows the structure of the main classes and their associations.
- **Flowchart**: Explains the flow of major features like account creation or money transfer.

### Diagrams for Bank Management System
The `BankManagementSystem` project includes various diagrams to provide a visual understanding of the system's architecture, database, and workflows. Below are the key diagrams included with the project:

## Diagrams
The following diagrams illustrate the structure and workflows of the Bank Management System:

1. **Entity-Relationship Diagram (ERD)** - Shows the relationships between users, accounts, and employees.
2. **Class Diagram** - Depicts the system's key classes, attributes, and their associations.
3. **Flowchart** - Visualizes the workflows for major system processes.

For detailed reference, see the `src/documentation` folder.

## Folder Structure
```
BankManagementSystem/
 └── src/
     ├── main/
     │   ├── java/com/example/bankmanagementsystem/
     │   │   ├── Controller/
     │   │   ├── Model/
     │   │   ├── Repository/
     │   │   ├── Service/
     │   │   ├── Exception/
     │   │   └── BankManagementSystemApplication.java
     │   └── resources/
     │       ├── application.properties
     │       ├── data.sql
     │       └── schema.sql
     ├── documentation/
     │   ├── ERD.png
     │   ├── ClassDiagram.png
     │   └── Flowchart.png
     └── test/
         └── java/com/example/bankmanagementsystem/
             ├── Controller/
             └── Service/
```

## Known Issues
- No GUI is implemented. All interactions are done via API endpoints.
- Authentication (such as Spring Security) is not implemented.

## Future Improvements
- Add a user-friendly GUI using a framework like React.
- Implement authentication and role-based access using Spring Security.

## Author
Bank Management System Project

