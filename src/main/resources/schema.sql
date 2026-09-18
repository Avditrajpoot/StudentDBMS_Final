CREATE DATABASE IF NOT EXISTS student_dbms;
USE student_dbms;

CREATE TABLE IF NOT EXISTS students (
 id INT PRIMARY KEY AUTO_INCREMENT,
 roll_no VARCHAR(30) NOT NULL UNIQUE,
 name VARCHAR(100) NOT NULL,
 email VARCHAR(120) NOT NULL UNIQUE,
 department VARCHAR(80) NOT NULL,
 semester INT NOT NULL CHECK (semester BETWEEN 1 AND 8),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS courses (
 id INT PRIMARY KEY AUTO_INCREMENT,
 code VARCHAR(20) NOT NULL UNIQUE,
 name VARCHAR(120) NOT NULL,
 credits INT NOT NULL CHECK (credits BETWEEN 1 AND 6)
);

CREATE TABLE IF NOT EXISTS enrollments (
 id INT PRIMARY KEY AUTO_INCREMENT,
 student_id INT NOT NULL,
 course_id INT NOT NULL,
 grade VARCHAR(5),
 enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 UNIQUE(student_id, course_id),
 FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
 FOREIGN KEY(course_id) REFERENCES courses(id) ON DELETE CASCADE
);

INSERT IGNORE INTO courses(code,name,credits) VALUES
('CSE101','Programming Fundamentals',4),('DBMS201','Database Management Systems',4),('CSE202','Data Structures',4),('MAT201','Discrete Mathematics',3);
