# Title of Project: Academic Management System

## Functional Dependencies

## PERSON

**Primary Key:** PersonID  
**Candidate Key:** Email

**Functional Dependencies:**

- PersonID → FirstName, LastName, Email, DOB, Age
- Email → PersonID, FirstName, LastName, DOB, Age

## PERSON_PHONE

**Primary Key:** (PersonID, PhoneNumber)

**Functional Dependencies:**

- (PersonID, PhoneNumber) → ∅

## DEPARTMENT

**Primary Key:** DepartmentID  
**Candidate Key:** DepartmentName

**Functional Dependencies:**

- DepartmentID → DepartmentName
- DepartmentName → DepartmentID

## PROGRAM

**Primary Key:** ProgramID  
**Candidate Key:** (ProgramName, DepartmentID)

**Functional Dependencies:**

- ProgramID → ProgramName, Level, DurationYears, DepartmentID
- (ProgramName, DepartmentID) → ProgramID, Level, DurationYears

## STUDENT

**Primary Key:** PersonID  
**Candidate Key:** RollNo

**Functional Dependencies:**

- PersonID → RollNo, AdmissionYear, ProgramID
- RollNo → PersonID, AdmissionYear, ProgramID

## FACULTY

**Primary Key:** PersonID  
**Candidate Key:** EmployeeCode

**Functional Dependencies:**

- PersonID → EmployeeCode, Designation, DepartmentID
- EmployeeCode → PersonID, Designation, DepartmentID

## COURSE

**Primary Key:** CourseID  
**Candidate Key:** (CourseTitle, ProgramID)

**Functional Dependencies:**

- CourseID → CourseTitle, Credits, ProgramID
- (CourseTitle, ProgramID) → CourseID, Credits

## THEORY_COURSE

**Primary Key:** CourseID

**Functional Dependencies:**

- CourseID → LectureHours

## LAB_COURSE

**Primary Key:** CourseID

**Functional Dependencies:**

- CourseID → LabHours

## SEMESTER

**Primary Key:** SemesterID  
**Candidate Key:** SemesterName

**Functional Dependencies:**

- SemesterID → SemesterName, StartDate, EndDate, Duration
- SemesterName → SemesterID, StartDate, EndDate, Duration

## COURSE_OFFERING

**Primary Key:** OfferingID  
**Candidate Key:** (CourseID, SemesterID, Section)

**Functional Dependencies:**

- OfferingID → CourseID, SemesterID, Section, MaxSeats
- (CourseID, SemesterID, Section) → OfferingID, MaxSeats

## ENROLLMENT

**Primary Key:** (StudentID, OfferingID)

**Functional Dependencies:**

- (StudentID, OfferingID) → EnrollmentDate, Status

## TEACHING_ASSIGNMENT

**Primary Key:** (FacultyID, OfferingID)

**Functional Dependencies:**

- (FacultyID, OfferingID) → Role, AssignedHours

## ASSESSMENT

**Primary Key:** AssessmentID  
**Candidate Key:** (Title, OfferingID)

**Functional Dependencies:**

- AssessmentID → Title, OfferingID
- (Title, OfferingID) → AssessmentID

## QUIZ

**Primary Key:** AssessmentID

**Functional Dependencies:**

- AssessmentID → ∅

## ASSIGNMENT

**Primary Key:** AssessmentID

**Functional Dependencies:**

- AssessmentID → ∅

## EXAM

**Primary Key:** AssessmentID

**Functional Dependencies:**

- AssessmentID → ∅

## ASSESSMENT_ITEM

**Primary Key:** (AssessmentID, ItemName)

**Functional Dependencies:**

- (AssessmentID, ItemName) → MaxMarks, Weightage

## ATTEMPTS

**Primary Key:** (StudentID, AssessmentID)

**Functional Dependencies:**

- (StudentID, AssessmentID) → MarksObtained, Grade, PassFail

## Additional Notes

### Derived / Transitive Dependencies (Across Tables)

- StudentID → PersonID → FirstName, LastName
- CourseID → ProgramID → DepartmentID
- OfferingID → CourseID → ProgramID

### Multivalued Dependency

- PersonID →→ PhoneNumber

## Normalizations and Anomalies

### UNNORMALIZED FORM (UNF)

- Shows data with repeating groups and redundancy
- StudentID, StudentName, CourseName, FacultyName, Marks stored as comma-separated values
- Demonstrates data anomalies and redundancy issues
- Multiple courses and marks in a single cell (not atomic)

### Anomalies in UNF:

### Insertion Anomaly

- Cannot insert a new course unless at least one student is enrolled
- Cannot add a faculty without associating it with a student record

### Update Anomaly

- If a faculty name changes (e.g., Dr. Smith → Dr. John Smith), it must be updated in multiple comma-separated lists → high risk of inconsistency

### Deletion Anomaly

- If a student record is deleted, all associated course and faculty information may also be lost

```
--------------
SELECT * FROM student_course_unf
--------------
+-----------+---------------+-----------------------------------------------+-------------------------------+------------+
| StudentID | StudentName   | CourseName                                    | FacultyName                   | Marks      |
+-----------+---------------+-----------------------------------------------+-------------------------------+------------+
|       101 | Alice Johnson | Database Systems, Web Development             | Dr. Smith, Dr. Brown          | 85, 92     |
|       102 | Bob Wilson    | Data Structures, Algorithms                   | Dr. Johnson, Dr. Lee          | 78, 88     |
|       103 | Carol Davis   | Database Systems, Algorithms, Web Development | Dr. Smith, Dr. Lee, Dr. Brown | 90, 92, 88 |
|       104 | David Miller  | Web Development                               | Dr. Brown                     | 95         |
|       105 | Eve Martinez  | Data Structures, Database Systems             | Dr. Johnson, Dr. Smith        | 82, 86     |
+-----------+---------------+-----------------------------------------------+-------------------------------+------------+
```

### FIRST NORMAL FORM (1NF)

- Atomic values only - each cell contains a single value
- Remove repeating groups by creating separate rows
- Primary key: (StudentID, CourseName)
- Still contains partial dependencies (StudentName depends only on StudentID, not on CourseName)

### Anomalies in 1NF:

### Insertion Anomaly

- Cannot insert a student without assigning a course
- Cannot insert a course without linking it to a student

### Update Anomaly

- StudentName is repeated → updating a student name requires multiple row updates
- FacultyName repeated for each course instance

### Deletion Anomaly

- Deleting a student’s only course record removes student data entirely
- Deleting last enrollment of a course may remove course + faculty info

```
--------------
SELECT * FROM student_course_1nf
--------------

+-----------+---------------+------------------+-------------+-------+
| StudentID | StudentName   | CourseName       | FacultyName | Marks |
+-----------+---------------+------------------+-------------+-------+
|       101 | Alice Johnson | Database Systems | Dr. Smith   |    85 |
|       101 | Alice Johnson | Web Development  | Dr. Brown   |    92 |
|       102 | Bob Wilson    | Algorithms       | Dr. Lee     |    88 |
|       102 | Bob Wilson    | Data Structures  | Dr. Johnson |    78 |
|       103 | Carol Davis   | Algorithms       | Dr. Lee     |    92 |
|       103 | Carol Davis   | Database Systems | Dr. Smith   |    90 |
|       103 | Carol Davis   | Web Development  | Dr. Brown   |    88 |
|       104 | David Miller  | Web Development  | Dr. Brown   |    95 |
|       105 | Eve Martinez  | Data Structures  | Dr. Johnson |    82 |
|       105 | Eve Martinez  | Database Systems | Dr. Smith   |    86 |
+-----------+---------------+------------------+-------------+-------+
--------------
```

### SECOND NORMAL FORM (2NF)

- Remove partial dependencies
- Non-key attributes must depend on the ENTIRE primary key, not just part of it
- Separated into 3 tables: Student, Course, Enrollment
- StudentName now only in Student table (depends on StudentID)
- FacultyName now only in Course table (depends on CourseName)
- Enrollment table references both via foreign keys

### Anomalies in 2NF:

### Insertion Anomaly

- Cannot insert enrollment without valid student and course
- But now:
  - Can insert student independently
  - Can insert course independently

### Update Anomaly

- FacultyName still stored in Course table
- If faculty changes for a course → update in one place (better than 1NF, but still coupled)

### Deletion Anomaly

- Deleting a course removes its faculty info
- Faculty cannot exist independently

```
--------------
SELECT 'Student Table' AS TableName
--------------

+---------------+
| TableName     |
+---------------+
| Student Table |
+---------------+
--------------
SELECT * FROM student_2nf
--------------

+-----------+---------------+
| StudentID | StudentName   |
+-----------+---------------+
|       101 | Alice Johnson |
|       102 | Bob Wilson    |
|       103 | Carol Davis   |
|       104 | David Miller  |
|       105 | Eve Martinez  |
+-----------+---------------+
--------------
SELECT 'Course Table' AS TableName
--------------

+--------------+
| TableName    |
+--------------+
| Course Table |
+--------------+
--------------
SELECT * FROM course_2nf
--------------

+------------------+-------------+
| CourseName       | FacultyName |
+------------------+-------------+
| Algorithms       | Dr. Lee     |
| Data Structures  | Dr. Johnson |
| Database Systems | Dr. Smith   |
| Web Development  | Dr. Brown   |
+------------------+-------------+
--------------
SELECT 'Enrollment Table' AS TableName
--------------

+------------------+
| TableName        |
+------------------+
| Enrollment Table |
+------------------+
--------------
SELECT * FROM enrollment_2nf
--------------

+-----------+------------------+-------+
| StudentID | CourseName       | Marks |
+-----------+------------------+-------+
|       101 | Database Systems |    85 |
|       101 | Web Development  |    92 |
|       102 | Algorithms       |    88 |
|       102 | Data Structures  |    78 |
|       103 | Algorithms       |    92 |
|       103 | Database Systems |    90 |
|       103 | Web Development  |    88 |
|       104 | Web Development  |    95 |
|       105 | Data Structures  |    82 |
|       105 | Database Systems |    86 |
+-----------+------------------+-------+
--------------
```

### THIRD NORMAL FORM (3NF)

- Remove transitive dependencies
- Non-key attributes must not depend on other non-key attributes
- FacultyName removed from Course table - now independent Faculty table
- Faculty is independent entity, Course depends on Faculty via ForeignKey
- All non-key attributes depend ONLY on primary key
- Complete BCNF design with no redundancy or anomalies

```
--------------
SELECT 'Student Table' AS TableName
--------------

+---------------+
| TableName     |
+---------------+
| Student Table |
+---------------+
--------------
SELECT * FROM student_3nf
--------------

+-----------+---------------+
| StudentID | StudentName   |
+-----------+---------------+
|       101 | Alice Johnson |
|       102 | Bob Wilson    |
|       103 | Carol Davis   |
|       104 | David Miller  |
|       105 | Eve Martinez  |
+-----------+---------------+
--------------
SELECT 'Faculty Table' AS TableName
--------------

+---------------+
| TableName     |
+---------------+
| Faculty Table |
+---------------+
--------------
SELECT * FROM faculty_3nf
--------------

+-----------+-------------+
| FacultyID | FacultyName |
+-----------+-------------+
|         1 | Dr. Smith   |
|         2 | Dr. Brown   |
|         3 | Dr. Johnson |
|         4 | Dr. Lee     |
+-----------+-------------+
--------------
SELECT 'Course Table' AS TableName
--------------

+--------------+
| TableName    |
+--------------+
| Course Table |
+--------------+
--------------
SELECT * FROM course_3nf
--------------

+----------+------------------+-----------+
| CourseID | CourseName       | FacultyID |
+----------+------------------+-----------+
|      201 | Database Systems |         1 |
|      202 | Web Development  |         2 |
|      203 | Data Structures  |         3 |
|      204 | Algorithms       |         4 |
+----------+------------------+-----------+
--------------
SELECT 'Enrollment with Faculty Information' AS TableName
--------------

+-------------------------------------+
| TableName                           |
+-------------------------------------+
| Enrollment with Faculty Information |
+-------------------------------------+
--------------
SELECT e.StudentID, s.StudentName, e.CourseID, c.CourseName, f.FacultyName, e.Marks
FROM enrollment_3nf e
JOIN student_3nf s ON e.StudentID = s.StudentID
JOIN course_3nf c ON e.CourseID = c.CourseID
JOIN faculty_3nf f ON c.FacultyID = f.FacultyID
--------------

+-----------+---------------+----------+------------------+-------------+-------+
| StudentID | StudentName   | CourseID | CourseName       | FacultyName | Marks |
+-----------+---------------+----------+------------------+-------------+-------+
|       101 | Alice Johnson |      201 | Database Systems | Dr. Smith   |    85 |
|       103 | Carol Davis   |      201 | Database Systems | Dr. Smith   |    90 |
|       105 | Eve Martinez  |      201 | Database Systems | Dr. Smith   |    86 |
|       101 | Alice Johnson |      202 | Web Development  | Dr. Brown   |    92 |
|       103 | Carol Davis   |      202 | Web Development  | Dr. Brown   |    88 |
|       104 | David Miller  |      202 | Web Development  | Dr. Brown   |    95 |
|       102 | Bob Wilson    |      203 | Data Structures  | Dr. Johnson |    78 |
|       105 | Eve Martinez  |      203 | Data Structures  | Dr. Johnson |    82 |
|       102 | Bob Wilson    |      204 | Algorithms       | Dr. Lee     |    88 |
|       103 | Carol Davis   |      204 | Algorithms       | Dr. Lee     |    92 |
+-----------+---------------+----------+------------------+-------------+-------+
--------------
```

## MySQL Scripts used

## Insertion script

```
::::::::::::::
final_db/inserts.sql
::::::::::::::

USE academic_management;


INSERT INTO DEPARTMENT (DepartmentName) VALUES
('Computer Science'),
('Information Technology'),
('Electronics and Communication'),
('Electrical Engineering'),
('Mechanical Engineering');


INSERT INTO PROGRAM (ProgramName, Level, DurationYears, DepartmentID) VALUES
('Bachelor of Technology in CSE', 'Undergraduate', 4, 1),
('Bachelor of Technology in IT', 'Undergraduate', 4, 2),
('Master of Technology in CSE', 'Postgraduate', 2, 1),
('Bachelor of Technology in ECE', 'Undergraduate', 4, 3),
('Bachelor of Technology in EE', 'Undergraduate', 4, 4);


INSERT INTO PERSON (FirstName, LastName, Email, DOB, Age) VALUES
('Rajesh', 'Kumar', 'rajesh.kumar@university.edu', '2003-05-15', 21),
('Priya', 'Singh', 'priya.singh@university.edu', '2003-08-22', 20),
('Arjun', 'Patel', 'arjun.patel@university.edu', '2003-11-10', 20),
('Neha', 'Gupta', 'neha.gupta@university.edu', '2004-02-18', 20),
('Vikram', 'Sharma', 'vikram.sharma@university.edu', '2003-07-25', 21),
('Dr. Amit', 'Nair', 'amit.nair@university.edu', '1975-03-12', 49),
('Dr. Sneha', 'Verma', 'sneha.verma@university.edu', '1978-06-20', 46),
('Dr. Rahul', 'Das', 'rahul.das@university.edu', '1980-09-08', 44),
('Prof. Anjali', 'Iyer', 'anjali.iyer@university.edu', '1977-12-15', 47),
('Prof. Anil', 'Bhat', 'anil.bhat@university.edu', '1982-01-30', 42);


INSERT INTO PERSON_PHONE (PersonID, PhoneNumber) VALUES
(1, '+91-9876543210'),
(1, '+91-8765432109'),
(2, '+91-9765432109'),
(3, '+91-9654321098'),
(4, '+91-9543210987'),
(5, '+91-9432109876'),
(6, '+91-9876543200'),
(7, '+91-9876543201'),
(8, '+91-9876543202'),
(9, '+91-9876543203'),
(10, '+91-9876543204');


INSERT INTO STUDENT (PersonID, RollNo, AdmissionYear, ProgramID) VALUES
(1, 'CSE001', 2021, 1),
(2, 'IT001', 2021, 2),
(3, 'CSE002', 2021, 1),
(4, 'ECE001', 2021, 4),
(5, 'EE001', 2021, 5);


INSERT INTO FACULTY (PersonID, EmployeeCode, Designation, DepartmentID) VALUES
(6, 'FAC001', 'Assistant Professor', 1),
(7, 'FAC002', 'Associate Professor', 2),
(8, 'FAC003', 'Assistant Professor', 1),
(9, 'FAC004', 'Professor', 3),
(10, 'FAC005', 'Associate Professor', 4);


INSERT INTO COURSE (CourseTitle, Credits, ProgramID) VALUES
('Data Structures', 4, 1),
('Database Management Systems', 4, 1),
('Web Development', 3, 1),
('Algorithms', 4, 1),
('Operating Systems', 3, 1),
('Information Security', 4, 2),
('Network Administration', 3, 2),
('Software Engineering', 4, 2),
('Digital Electronics', 4, 4),
('Microprocessors', 3, 4);


INSERT INTO THEORY_COURSE (CourseID, LectureHours) VALUES
(1, 45),
(2, 48),
(3, 36),
(4, 45),
(5, 36),
(6, 45),
(7, 36),
(8, 48);


INSERT INTO LAB_COURSE (CourseID, LabHours) VALUES
(9, 30),
(10, 24);


INSERT INTO SEMESTER (SemesterName, StartDate, EndDate, Duration) VALUES
('Spring 2024', '2024-01-15', '2024-05-15', 16),
('Summer 2024', '2024-06-01', '2024-07-31', 8),
('Fall 2024', '2024-08-15', '2024-12-15', 16);


INSERT INTO COURSE_OFFERING (CourseID, SemesterID, Section, MaxSeats) VALUES
(1, 1, 'A', 50),
(1, 1, 'B', 50),
(2, 1, 'A', 40),
(3, 1, 'A', 60),
(4, 1, 'A', 45),
(5, 1, 'A', 40),
(6, 1, 'A', 45),
(7, 1, 'A', 50),
(8, 1, 'A', 50),
(9, 1, 'A', 40),
(10, 1, 'A', 35),
(1, 3, 'A', 50),
(2, 3, 'A', 40),
(4, 3, 'A', 45),
(8, 3, 'A', 50);


INSERT INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status) VALUES
(1, 1, '2024-01-15', 'Active'),
(1, 2, '2024-01-15', 'Active'),
(1, 4, '2024-01-15', 'Active'),
(2, 8, '2024-01-15', 'Active'),
(2, 7, '2024-01-15', 'Active'),
(3, 5, '2024-01-15', 'Active'),
(3, 3, '2024-01-15', 'Active'),
(4, 9, '2024-01-15', 'Active'),
(5, 10, '2024-01-15', 'Active'),
(1, 12, '2024-08-15', 'Active'),
(2, 14, '2024-08-15', 'Active');


INSERT INTO TEACHING_ASSIGNMENT (FacultyID, OfferingID, Role, AssignedHours) VALUES
(6, 1, 'Instructor', 45),
(6, 2, 'Instructor', 45),
(8, 4, 'Instructor', 36),
(7, 8, 'Instructor', 48),
(7, 7, 'Instructor', 36),
(6, 5, 'Instructor', 45),
(8, 3, 'Instructor', 36),
(9, 9, 'Instructor', 30),
(10, 10, 'Instructor', 24),
(6, 12, 'Instructor', 45),
(7, 14, 'Instructor', 45);


INSERT INTO ASSESSMENT (Title, OfferingID) VALUES
('Quiz 1 - Data Structures', 1),
('Mid-term Exam - DBMS', 3),
('Assignment 1 - Web Dev', 4),
('Final Exam - Algorithms', 5),
('Mid-term Exam - Operating Systems', 6),
('Quiz 1 - InfoSec', 8),
('Lab Project - Software Engineering', 8),
('Mid-term Exam - Digital Electronics', 9),
('Lab Assessment - Microprocessors', 10);


INSERT INTO QUIZ (AssessmentID) VALUES (1), (6);


INSERT INTO ASSIGNMENT (AssessmentID) VALUES (3), (7);


INSERT INTO EXAM (AssessmentID) VALUES (2), (4), (5), (8), (9);


INSERT INTO ASSESSMENT_ITEM (AssessmentID, ItemName, MaxMarks, Weightage) VALUES
(1, 'Multiple Choice', 20, 50.0),
(1, 'Short Answer', 20, 50.0),
(2, 'Problem Solving', 50, 60.0),
(2, 'Theoretical', 30, 40.0),
(3, 'Code Quality', 50, 50.0),
(3, 'Functionality', 50, 50.0),
(4, 'Algorithms', 60, 70.0),
(4, 'Implementation', 40, 30.0),
(5, 'Conceptual', 40, 50.0),
(5, 'Practical', 40, 50.0),
(6, 'MCQ', 25, 100.0),
(7, 'Code Implementation', 100, 70.0),
(7, 'Documentation', 30, 30.0),
(8, 'Circuit Design', 50, 50.0),
(8, 'Theory', 50, 50.0),
(9, 'Practical Work', 100, 100.0);


INSERT INTO ATTEMPTS (StudentID, AssessmentID, MarksObtained, Grade, PassFail) VALUES
(1, 1, 18, 'A', 'Pass'),
(1, 2, 42, 'B+', 'Pass'),
(1, 4, 55, 'A', 'Pass'),
(2, 6, 22, 'A+', 'Pass'),
(2, 7, 85, 'A', 'Pass'),
(3, 3, 45, 'B', 'Pass'),
(3, 5, 68, 'A', 'Pass'),
(4, 8, 48, 'B+', 'Pass'),
(5, 9, 92, 'A+', 'Pass'),
(1, 3, 38, 'B', 'Pass'),
(2, 8, 42, 'B+', 'Pass');
```

## Queries Script

### EXPLANATION - 6 Simple Queries:

1. All Student Enrollments
   - Shows which students are enrolled in which courses
   - Displays course section and semester

2. Course Enrollment Summary
   - Lists all courses with current enrollments vs max seats
   - Helps identify full or under-enrolled courses

3. Student Assessment Results
   - Shows each student's marks, grades, and pass/fail status
   - Joins assessment results across all courses

4. Faculty Teaching Assignments
   - Lists faculty members with courses they teach
   - Includes department and designation

5. Students Who Passed by Course
   - Counts how many students passed each course
   - Groups by course offering

6. Student Count by Program
   - Shows total students enrolled in each program
   - Displays department name

```
::::::::::::::
final_db/queries.sql
::::::::::::::

--------------
SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, co.Section, sem.SemesterName, e.Status
FROM ENROLLMENT e
JOIN STUDENT s ON e.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
ORDER BY s.RollNo
--------------

+--------+-----------+----------+-----------------------------+---------+--------------+--------+
| RollNo | FirstName | LastName | CourseTitle                 | Section | SemesterName | Status |
+--------+-----------+----------+-----------------------------+---------+--------------+--------+
| CSE001 | Rajesh    | Kumar    | Data Structures             | A       | Spring 2024  | Active |
| CSE001 | Rajesh    | Kumar    | Data Structures             | B       | Spring 2024  | Active |
| CSE001 | Rajesh    | Kumar    | Web Development             | A       | Spring 2024  | Active |
| CSE001 | Rajesh    | Kumar    | Data Structures             | A       | Fall 2024    | Active |
| CSE002 | Arjun     | Patel    | Database Management Systems | A       | Spring 2024  | Active |
| CSE002 | Arjun     | Patel    | Algorithms                  | A       | Spring 2024  | Active |
| ECE001 | Neha      | Gupta    | Software Engineering        | A       | Spring 2024  | Active |
| EE001  | Vikram    | Sharma   | Digital Electronics         | A       | Spring 2024  | Active |
| IT001  | Priya     | Singh    | Information Security        | A       | Spring 2024  | Active |
| IT001  | Priya     | Singh    | Network Administration      | A       | Spring 2024  | Active |
| IT001  | Priya     | Singh    | Algorithms                  | A       | Fall 2024    | Active |
+--------+-----------+----------+-----------------------------+---------+--------------+--------+
--------------
SELECT c.CourseTitle, co.Section, sem.SemesterName, COUNT(e.StudentID) AS Enrolled, co.MaxSeats
FROM COURSE_OFFERING co
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
LEFT JOIN ENROLLMENT e ON co.OfferingID = e.OfferingID AND e.Status = 'Active'
GROUP BY co.OfferingID, c.CourseTitle, co.Section, sem.SemesterName, co.MaxSeats
ORDER BY c.CourseTitle
--------------

+-----------------------------+---------+--------------+----------+----------+
| CourseTitle                 | Section | SemesterName | Enrolled | MaxSeats |
+-----------------------------+---------+--------------+----------+----------+
| Algorithms                  | A       | Spring 2024  |        1 |       45 |
| Algorithms                  | A       | Fall 2024    |        1 |       45 |
| Data Structures             | A       | Spring 2024  |        1 |       50 |
| Data Structures             | B       | Spring 2024  |        1 |       50 |
| Data Structures             | A       | Fall 2024    |        1 |       50 |
| Database Management Systems | A       | Spring 2024  |        1 |       40 |
| Database Management Systems | A       | Fall 2024    |        0 |       40 |
| Digital Electronics         | A       | Spring 2024  |        1 |       40 |
| Information Security        | A       | Spring 2024  |        1 |       45 |
| Microprocessors             | A       | Spring 2024  |        0 |       35 |
| Network Administration      | A       | Spring 2024  |        1 |       50 |
| Operating Systems           | A       | Spring 2024  |        0 |       40 |
| Software Engineering        | A       | Spring 2024  |        1 |       50 |
| Software Engineering        | A       | Fall 2024    |        0 |       50 |
| Web Development             | A       | Spring 2024  |        1 |       60 |
+-----------------------------+---------+--------------+----------+----------+
--------------
SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, att.MarksObtained, att.Grade, att.PassFail
FROM ATTEMPTS att
JOIN STUDENT s ON att.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
ORDER BY s.RollNo
--------------

+--------+-----------+----------+-----------------------------+---------------+-------+----------+
| RollNo | FirstName | LastName | CourseTitle                 | MarksObtained | Grade | PassFail |
+--------+-----------+----------+-----------------------------+---------------+-------+----------+
| CSE001 | Rajesh    | Kumar    | Data Structures             |            18 | A     | Pass     |
| CSE001 | Rajesh    | Kumar    | Database Management Systems |            42 | B+    | Pass     |
| CSE001 | Rajesh    | Kumar    | Web Development             |            38 | B     | Pass     |
| CSE001 | Rajesh    | Kumar    | Algorithms                  |            55 | A     | Pass     |
| CSE002 | Arjun     | Patel    | Web Development             |            45 | B     | Pass     |
| CSE002 | Arjun     | Patel    | Operating Systems           |            68 | A     | Pass     |
| ECE001 | Neha      | Gupta    | Software Engineering        |            48 | B+    | Pass     |
| EE001  | Vikram    | Sharma   | Digital Electronics         |            92 | A+    | Pass     |
| IT001  | Priya     | Singh    | Network Administration      |            22 | A+    | Pass     |
| IT001  | Priya     | Singh    | Network Administration      |            85 | A     | Pass     |
| IT001  | Priya     | Singh    | Software Engineering        |            42 | B+    | Pass     |
+--------+-----------+----------+-----------------------------+---------------+-------+----------+
--------------
SELECT p.FirstName, p.LastName, f.EmployeeCode, f.Designation, c.CourseTitle, d.DepartmentName
FROM TEACHING_ASSIGNMENT ta
JOIN FACULTY f ON ta.FacultyID = f.PersonID
JOIN PERSON p ON f.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON ta.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN DEPARTMENT d ON f.DepartmentID = d.DepartmentID
ORDER BY p.LastName
--------------

+--------------+----------+--------------+---------------------+-----------------------------+-------------------------------+
| FirstName    | LastName | EmployeeCode | Designation         | CourseTitle                 | DepartmentName                |
+--------------+----------+--------------+---------------------+-----------------------------+-------------------------------+
| Prof. Anil   | Bhat     | FAC005       | Associate Professor | Digital Electronics         | Electrical Engineering        |
| Dr. Rahul    | Das      | FAC003       | Assistant Professor | Database Management Systems | Computer Science              |
| Dr. Rahul    | Das      | FAC003       | Assistant Professor | Web Development             | Computer Science              |
| Prof. Anjali | Iyer     | FAC004       | Professor           | Software Engineering        | Electronics and Communication |
| Dr. Amit     | Nair     | FAC001       | Assistant Professor | Data Structures             | Computer Science              |
| Dr. Amit     | Nair     | FAC001       | Assistant Professor | Data Structures             | Computer Science              |
| Dr. Amit     | Nair     | FAC001       | Assistant Professor | Algorithms                  | Computer Science              |
| Dr. Amit     | Nair     | FAC001       | Assistant Professor | Data Structures             | Computer Science              |
| Dr. Sneha    | Verma    | FAC002       | Associate Professor | Information Security        | Information Technology        |
| Dr. Sneha    | Verma    | FAC002       | Associate Professor | Network Administration      | Information Technology        |
| Dr. Sneha    | Verma    | FAC002       | Associate Professor | Algorithms                  | Information Technology        |
+--------------+----------+--------------+---------------------+-----------------------------+-------------------------------+
--------------
SELECT c.CourseTitle, co.Section, COUNT(DISTINCT att.StudentID) AS StudentsPassed
FROM ATTEMPTS att
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
WHERE att.PassFail = 'Pass'
GROUP BY co.OfferingID, c.CourseTitle, co.Section
ORDER BY c.CourseTitle
--------------

+-----------------------------+---------+----------------+
| CourseTitle                 | Section | StudentsPassed |
+-----------------------------+---------+----------------+
| Algorithms                  | A       |              1 |
| Data Structures             | A       |              1 |
| Database Management Systems | A       |              1 |
| Digital Electronics         | A       |              1 |
| Network Administration      | A       |              1 |
| Operating Systems           | A       |              1 |
| Software Engineering        | A       |              2 |
| Web Development             | A       |              2 |
+-----------------------------+---------+----------------+
--------------
SELECT prog.ProgramName, d.DepartmentName, COUNT(DISTINCT s.PersonID) AS TotalStudents
FROM STUDENT s
JOIN PROGRAM prog ON s.ProgramID = prog.ProgramID
JOIN DEPARTMENT d ON prog.DepartmentID = d.DepartmentID
GROUP BY prog.ProgramID, prog.ProgramName, d.DepartmentName
ORDER BY prog.ProgramName
--------------

+-------------------------------+-------------------------------+---------------+
| ProgramName                   | DepartmentName                | TotalStudents |
+-------------------------------+-------------------------------+---------------+
| Bachelor of Technology in CSE | Computer Science              |             2 |
| Bachelor of Technology in ECE | Electronics and Communication |             1 |
| Bachelor of Technology in EE  | Electrical Engineering        |             1 |
| Bachelor of Technology in IT  | Information Technology        |             1 |
+-------------------------------+-------------------------------+---------------+
*/
```

## Table Creation Script

```
::::::::::::::
final_db/schema.sql
::::::::::::::

CREATE TABLE PERSON (
    PersonID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    DOB DATE NOT NULL,
    Age INT,
    CHECK (Age >= 0)
);

CREATE TABLE PERSON_PHONE (
    PersonID INT NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL,
    PRIMARY KEY (PersonID, PhoneNumber),
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE
);

CREATE TABLE DEPARTMENT (
    DepartmentID INT PRIMARY KEY AUTO_INCREMENT,
    DepartmentName VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE PROGRAM (
    ProgramID INT PRIMARY KEY AUTO_INCREMENT,
    ProgramName VARCHAR(100) NOT NULL,
    Level VARCHAR(50) NOT NULL,
    DurationYears INT NOT NULL,
    DepartmentID INT NOT NULL,
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID) ON DELETE RESTRICT,
    UNIQUE KEY (ProgramName, DepartmentID)
);

CREATE TABLE STUDENT (
    PersonID INT PRIMARY KEY,
    RollNo VARCHAR(20) NOT NULL UNIQUE,
    AdmissionYear INT NOT NULL,
    ProgramID INT NOT NULL,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID) ON DELETE RESTRICT
);

CREATE TABLE FACULTY (
    PersonID INT PRIMARY KEY,
    EmployeeCode VARCHAR(20) NOT NULL UNIQUE,
    Designation VARCHAR(50) NOT NULL,
    DepartmentID INT NOT NULL,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID) ON DELETE RESTRICT
);


CREATE TABLE COURSE (
    CourseID INT PRIMARY KEY AUTO_INCREMENT,
    CourseTitle VARCHAR(100) NOT NULL,
    Credits INT NOT NULL,
    ProgramID INT NOT NULL,
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID) ON DELETE RESTRICT,
    CHECK (Credits > 0),
    UNIQUE KEY (CourseTitle, ProgramID)
);

CREATE TABLE THEORY_COURSE (
    CourseID INT PRIMARY KEY,
    LectureHours INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID) ON DELETE CASCADE,
    CHECK (LectureHours > 0)
);

CREATE TABLE LAB_COURSE (
    CourseID INT PRIMARY KEY,
    LabHours INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID) ON DELETE CASCADE,
    CHECK (LabHours > 0)
);

CREATE TABLE SEMESTER (
    SemesterID INT PRIMARY KEY AUTO_INCREMENT,
    SemesterName VARCHAR(50) NOT NULL UNIQUE,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Duration INT NOT NULL,
    CHECK (Duration > 0 AND EndDate > StartDate)
);

CREATE TABLE COURSE_OFFERING (
    OfferingID INT PRIMARY KEY AUTO_INCREMENT,
    CourseID INT NOT NULL,
    SemesterID INT NOT NULL,
    Section VARCHAR(5) NOT NULL,
    MaxSeats INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID) ON DELETE RESTRICT,
    FOREIGN KEY (SemesterID) REFERENCES SEMESTER(SemesterID) ON DELETE RESTRICT,
    CHECK (MaxSeats > 0),
    UNIQUE KEY (CourseID, SemesterID, Section)
);


CREATE TABLE ENROLLMENT (
    StudentID INT NOT NULL,
    OfferingID INT NOT NULL,
    EnrollmentDate DATE NOT NULL,
    Status VARCHAR(20) NOT NULL,
    PRIMARY KEY (StudentID, OfferingID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE,
    CHECK (Status IN ('Active', 'Completed', 'Dropped', 'Inactive'))
);

CREATE TABLE TEACHING_ASSIGNMENT (
    FacultyID INT NOT NULL,
    OfferingID INT NOT NULL,
    Role VARCHAR(50) NOT NULL,
    AssignedHours INT NOT NULL,
    PRIMARY KEY (FacultyID, OfferingID),
    FOREIGN KEY (FacultyID) REFERENCES FACULTY(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE,
    CHECK (AssignedHours > 0)
);


CREATE TABLE ASSESSMENT (
    AssessmentID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(100) NOT NULL,
    OfferingID INT NOT NULL,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE,
    UNIQUE KEY (Title, OfferingID)
);

CREATE TABLE QUIZ (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);

CREATE TABLE ASSIGNMENT (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);

CREATE TABLE EXAM (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);

CREATE TABLE ASSESSMENT_ITEM (
    AssessmentID INT NOT NULL,
    ItemName VARCHAR(100) NOT NULL,
    MaxMarks INT NOT NULL,
    Weightage DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (AssessmentID, ItemName),
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE,
    CHECK (MaxMarks > 0 AND Weightage > 0 AND Weightage <= 100)
);

CREATE TABLE ATTEMPTS (
    StudentID INT NOT NULL,
    AssessmentID INT NOT NULL,
    MarksObtained INT NOT NULL,
    Grade CHAR(2),
    PassFail VARCHAR(10),
    PRIMARY KEY (StudentID, AssessmentID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE,
    CHECK (MarksObtained >= 0 AND Grade IN ('A+', 'A', 'B+', 'B', 'C+', 'C', 'D', 'F') OR Grade IS NULL),
    CHECK (PassFail IN ('Pass', 'Fail') OR PassFail IS NULL)
);
```

## Function Creation Scripts

```
::::::::::::::
final_db/functions.sql
::::::::::::::

DELIMITER //
CREATE FUNCTION calculate_gpa(p_student_id INT)
RETURNS DECIMAL(3,2)
DETERMINISTIC READS SQL DATA
BEGIN
  RETURN IFNULL((SELECT ROUND(AVG(CASE WHEN Grade='A+' THEN 4.0 WHEN Grade='A' THEN 4.0 WHEN Grade='B+' THEN 3.5 WHEN Grade='B' THEN 3.0 WHEN Grade='C+' THEN 2.5 WHEN Grade='C' THEN 2.0 WHEN Grade='D' THEN 1.0 ELSE 0 END), 2) FROM ATTEMPTS WHERE StudentID = p_student_id AND Grade IS NOT NULL), 0);
END //


CREATE FUNCTION count_enrollments(p_offering_id INT)
RETURNS INT
DETERMINISTIC READS SQL DATA
RETURN IFNULL((SELECT COUNT(*) FROM ENROLLMENT WHERE OfferingID = p_offering_id AND Status='Active'), 0) //
DELIMITER ;
```

## Trigger Creation Script

```
::::::::::::::
final_db/triggers.sql
::::::::::::::

DELIMITER //

CREATE TRIGGER prevent_enrollment_if_full
BEFORE INSERT ON ENROLLMENT
FOR EACH ROW
BEGIN
  IF (SELECT COUNT(*) FROM ENROLLMENT WHERE OfferingID = NEW.OfferingID) >=
     (SELECT MaxSeats FROM COURSE_OFFERING WHERE OfferingID = NEW.OfferingID) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Course full';
  END IF;
END //

CREATE TRIGGER compute_pass_fail_on_insert
BEFORE INSERT ON ATTEMPTS
FOR EACH ROW
BEGIN
  SET NEW.PassFail = IF(NEW.MarksObtained >= 40, 'Pass', 'Fail');
END //

DELIMITER ;
```

## Procedure Creation Script

```
::::::::::::::
final_db/procedures.sql
::::::::::::::

DELIMITER //
CREATE PROCEDURE enroll_student(IN p_student_id INT, IN p_offering_id INT)
BEGIN
  INSERT IGNORE INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status)
  VALUES (p_student_id, p_offering_id, CURDATE(), 'Active');
END //


CREATE PROCEDURE get_student_results(IN p_student_id INT)
BEGIN
  SELECT p.FirstName, p.LastName, s.RollNo, c.CourseTitle, a.Title, att.MarksObtained, att.Grade, att.PassFail
  FROM STUDENT s JOIN PERSON p ON s.PersonID = p.PersonID
  JOIN ENROLLMENT e ON s.PersonID = e.StudentID
  JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
  JOIN COURSE c ON co.CourseID = c.CourseID
  LEFT JOIN ASSESSMENT a ON co.OfferingID = a.OfferingID
  LEFT JOIN ATTEMPTS att ON s.PersonID = att.StudentID AND a.AssessmentID = att.AssessmentID
  WHERE s.PersonID = p_student_id;
END //
DELIMITER ;
```
