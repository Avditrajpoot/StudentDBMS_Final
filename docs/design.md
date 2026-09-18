# Design Documentation

## System Architecture
```mermaid
flowchart LR
 A[Swing Dashboard] --> B[Service / UI Logic]
 B --> C[DAO Layer]
 C --> D[JDBC]
 D --> E[(MySQL Database)]
```

## Workflow
```mermaid
flowchart TD
 A[Start] --> B[Connect to MySQL]
 B --> C[Dashboard]
 C --> D[Manage Students]
 C --> E[Manage Courses]
 D --> F[Enroll Student]
 E --> F
 F --> G[Assign Grade]
 G --> H[Generate Reports]
 H --> I[Exit]
```

## Use Cases
```mermaid
flowchart LR
 U[Administrator / User] --> S[Manage Students]
 U --> C[Manage Courses]
 U --> E[Manage Enrollments]
 U --> G[Manage Grades]
 U --> R[View Reports]
```

## ER Diagram
```mermaid
erDiagram
 STUDENTS ||--o{ ENROLLMENTS : has
 COURSES ||--o{ ENROLLMENTS : contains
 STUDENTS { int id PK string roll_no string name string email string department int semester }
 COURSES { int id PK string code string name int credits }
 ENROLLMENTS { int id PK int student_id FK int course_id FK string grade }
```

## Non-functional requirements
- Performance: indexed primary/unique keys and parameterized SQL.
- Security: prepared statements; credentials supplied through environment variables.
- Usability: GUI navigation and validation/error dialogs.
- Reliability: relational constraints, duplicate prevention and exception handling.
- Maintainability: package-based layered structure and separate DAO/model/service/UI responsibilities.
