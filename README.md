# VITyarthi Student Database Management System

A Java 17 + MySQL database management system with a desktop GUI for managing students, courses, enrollments, grades and reports.

## Features
- Dashboard with live database statistics
- Student CRUD: add, view, edit and delete
- Course management
- Student-course enrollment management
- Grade management
- Department-wise reporting
- JDBC + MySQL relational database
- Prepared statements and database constraints
- Console mode retained for demonstration/testing

## Architecture
Presentation (Swing GUI) → DAO layer → JDBC → MySQL

## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8+

## Database setup
```bash
mysql -u root -p < src/main/resources/schema.sql
```

Configure the connection using environment variables:
```bash
export DB_URL='jdbc:mysql://localhost:3306/student_dbms?useSSL=false&serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD='your_password'
```

The default password in the code is `root` for classroom/demo convenience; environment variables override it.

## Run
```bash
mvn clean test
mvn exec:java
```

Console mode:
```bash
mvn exec:java -Dexec.args=console
```

## Project structure
```text
src/main/java/com/vityarthi/dbms/
├── config/       Database configuration/connection
├── dao/          Student, Course and Enrollment data access
├── model/        Domain records
├── service/      Reporting logic
├── ui/           Swing dashboard + console UI
└── util/         Validation utilities
```

## Academic alignment
The project contains multiple functional modules, CRUD operations, database/storage design, modular Java classes, validation, testing and documentation to align with the VITyarthi Build Your Own Project requirements.
