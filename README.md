Task Management System
Project Overview
The Task Management System is a Spring Boot-based application designed for managing tasks with JWT-based authentication and role-based access control. The system allows users to create, update, and manage tasks, view task history, and receive notifications about task status updates.

This application includes functionality such as:

Task creation, editing, and deletion.
Sending task-related notifications via email using SMTP.
Storing task history with status updates.

Prerequisites
Ensure that the following tools and technologies are installed:

Java 17 (or later)
Spring Boot 3.3.4
PostgreSQL (for database storage)
Maven (for project build)
SMTP Configuration for email sending (Gmail or any SMTP server)
Getting Started
1. Clone the Repository
Clone this repository to your local machine:

bash
Copy code
git clone <repository_url>
cd task-management-system
2. Setup the Database (PostgreSQL)
Ensure that PostgreSQL is installed and running on your system. Create the database task_management:

sql
Copy code
CREATE DATABASE task_management;
Then, manually create the necessary tables with the following SQL scripts:

sql
Copy code
-- Create roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Insert predefined roles into the roles table
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');  -- Added ROLE_MODERATOR

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_roles table to map users to roles
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Create tasks table
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    due_date DATE NOT NULL,
    assigned_to_id BIGINT NOT NULL,
    assigned_by_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_to_id) REFERENCES users(id),
    FOREIGN KEY (assigned_by_id) REFERENCES users(id)
);

-- Create notifications table
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

Sequences Used in the Project
notification_sequence: Used for the id field in the Notification table.
CREATE SEQUENCE notification_sequence START WITH 1 INCREMENT BY 1;

task_sequence: Used for the id field in the Task table.
CREATE SEQUENCE task_sequence START WITH 1 INCREMENT BY 1;

history_sequence: Used for the id field in the User table.
CREATE SEQUENCE history_sequence START WITH 1 INCREMENT BY 1;


3. Postman Collection
You can interact with the API using the provided Postman collection. It includes all the necessary endpoints for:

User registration, login, and role-based authorization.
Task management: create, update, delete, and view tasks.
Sending and viewing notifications.
Make sure you import the provided Postman collection for testing and interacting with the application.

Configuration
application.properties
Configure the application properties with your database and email settings in the src/main/resources/application.properties file.

properties
Copy code
spring.application.name=task_management_system
spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
spring.datasource.username=postgres
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.default_schema=task_management

# JWT Configuration
taskmanager.app.jwtSecret= ======================tasksecret=Spring===========================
taskmanager.app.jwtExpirationMs=86400000
Dependencies
The following dependencies are used in this project:

Spring Boot 3.3.4
Spring Data JPA
PostgreSQL
Spring Security (JWT-based authentication)
Spring Mail (For email notifications)
How to Run the Application
Build the application using Maven:
bash
Copy code
mvn clean install
Run the application:
bash
Copy code
mvn spring-boot:run
The application will run on localhost:8080.
note:
-Create Two Users: You need to sign up two users for testing purposes. For example, let's create a "User A" who will be assigning tasks and a "User B" who will be receiving notifications.

-Send Task Notification: When you assign a task to "User B," an email notification will be sent to "User B" with details about the task. Ensure that the email used for "User B" is valid (e.g., a Gmail address) to receive the notification successfully.

-Token for Authentication: After signing in with "User A" or "User B," you will receive a JWT token. Make sure to include this token in the Authorization header as a Bearer token in all subsequent requests. This will be required for authentication and to ensure secure access to the API.