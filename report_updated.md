# Academic Management System Database - DBMS Lab Report

---

## 1. Title of the Project

**Academic Management System Database**

A normalized relational database system designed to manage comprehensive academic operations including student information, faculty assignments, course management, and assessment tracking for educational institutions.

---

## 2. About the Project

### Brief Description

The Academic Management System is a robust database solution that centralizes institutional academic data. It efficiently manages students, faculty, courses, departments, programs, semesters, enrollments, and assessment records while maintaining data integrity and minimizing redundancy through proper normalization.

### Purpose and Scope

**Purpose:**

- Provide a unified platform for academic data management
- Ensure data consistency and eliminate anomalies
- Support efficient querying and reporting for institutional operations
- Maintain referential integrity across related entities

**Scope:**

- Student information and enrollment management
- Faculty workload distribution and teaching assignments
- Course creation, offering, and capacity management
- Assessment design and student attempt tracking
- GPA calculation and academic performance analysis
- Support for multiple departments, programs, and semesters

### Technologies Used

- **Database System:** MySQL 8.0+
- **Normalization Level:** 3NF (Third Normal Form) with BCNF compliance
- **Languages:** SQL
- **Features:** Stored Procedures, User-Defined Functions, Triggers, Complex Queries

---

## 3. Anomalies in Unnormalized Form (UNF)

### Insert Anomaly

In the unnormalized form, data is stored in a single flattened table with repeating groups. New courses, faculty, or programs cannot be added independently without including student enrollment records. Similarly, a new student cannot be added without course enrollment and assessment data, creating artificial dependencies that should not exist.

### Update Anomaly

If a student's name needs to be updated, the change must be made in every row where that student appears. A course title change requires updates across all enrollment and assessment records containing that course. This multiplicity of updates increases the risk of inconsistency and data corruption.

### Delete Anomaly

Removing a student's record inadvertently deletes all associated course, faculty, and assessment information. Deleting the last enrollment in a course removes course metadata. Deleting a faculty member removes all teaching assignment and course offering details that should be independent.

```
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+--------------------+--------------+-----------------+---------------+
| StudentID | StudentName   | Email          | ProgramID | ProgramName | Level | DurationYears | DepartmentID | DepartmentName   | CourseID | CourseTitle | Credits | OfferingID | SemesterName | Section | FacultyID | FacultyName        | AssessmentID | AssessmentTitle | MarksObtained |
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+--------------------+--------------+-----------------+---------------+
|       101 | Alice Johnson | alice@mail.com |         1 | BTech CSE   | UG    |             4 |           10 | Computer Science | 201,202  | DBMS, OS    | 4,3     | 301,302    | Sem1         | A       | 401,402   | Dr. Smith, Dr. Lee | 501,502      | Midterm, Quiz   | 85,18         |
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+--------------------+--------------+-----------------+---------------+
```

---

## 4. Normalization Process (UNF → 1NF → 2NF → 3NF)

### 4.1 Unnormalized Form (UNF)

**Issues in UNF:**

```sql
CREATE TABLE university_unf (
    StudentID INT,
    StudentName VARCHAR(100),
    Email VARCHAR(100),
    ProgramID INT,
    ProgramName VARCHAR(100),
    Level VARCHAR(50),
    DurationYears INT,
    DepartmentID INT,
    DepartmentName VARCHAR(100),
    CourseID VARCHAR(200),
    CourseTitle VARCHAR(200),
    Credits VARCHAR(50),
    OfferingID VARCHAR(200),
    SemesterName VARCHAR(100),
    Section VARCHAR(10),
    FacultyID VARCHAR(200),
    FacultyName VARCHAR(200),
    AssessmentID VARCHAR(200),
    AssessmentTitle VARCHAR(200),
    MarksObtained VARCHAR(200)
);

INSERT INTO university_unf VALUES
(101, 'Alice Johnson', 'alice@mail.com',
 1, 'BTech CSE', 'UG', 4,
 10, 'Computer Science',
 '201,202', 'DBMS, OS', '4,3',
 '301,302', 'Sem1', 'A',
 '401,402', 'Dr. Smith, Dr. Lee',
 '501,502', 'Midterm, Quiz',
 '85,18');
```

**Key Problems:**

- All data in a single table with significant redundancy
- Repeating groups: CourseID, FacultyID, AssessmentID, and MarksObtained contain comma-separated values
- No atomicity of data values
- Severe insertion, update, and deletion anomalies

---

### 4.2 First Normal Form (1NF)

**Rule Applied:**

All attribute values must be atomic (single, indivisible values). Repeating groups must be eliminated by converting them into separate rows with a composite primary key.

**Changes Made:**

- Removed comma-separated values from CourseID, CourseTitle, FacultyID, FacultyName, AssessmentID, AssessmentTitle, and MarksObtained
- Established composite primary key: (StudentID, CourseID, OfferingID, AssessmentID)
- Each row now represents a single student-course-offering-assessment combination

**Implementation:**

```sql
CREATE TABLE university_1nf (
    StudentID INT,
    StudentName VARCHAR(100),
    Email VARCHAR(100),
    ProgramID INT,
    ProgramName VARCHAR(100),
    Level VARCHAR(50),
    DurationYears INT,
    DepartmentID INT,
    DepartmentName VARCHAR(100),
    CourseID INT,
    CourseTitle VARCHAR(100),
    Credits INT,
    OfferingID INT,
    SemesterName VARCHAR(100),
    Section VARCHAR(10),
    FacultyID INT,
    FacultyName VARCHAR(100),
    AssessmentID INT,
    AssessmentTitle VARCHAR(100),
    MarksObtained INT,
    PRIMARY KEY (StudentID, CourseID, OfferingID, AssessmentID)
);

INSERT INTO university_1nf VALUES
(101, 'Alice Johnson', 'alice@mail.com',
 1, 'BTech CSE', 'UG', 4,
 10, 'Computer Science',
 201, 'DBMS', 4,
 301, 'Sem1', 'A',
 401, 'Dr. Smith',
 501, 'Midterm',
 85),

(101, 'Alice Johnson', 'alice@mail.com',
 1, 'BTech CSE', 'UG', 4,
 10, 'Computer Science',
 202, 'OS', 3,
 302, 'Sem1', 'A',
 402, 'Dr. Lee',
 502, 'Quiz',
 18);
```

```
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+-------------+--------------+-----------------+---------------+
| StudentID | StudentName   | Email          | ProgramID | ProgramName | Level | DurationYears | DepartmentID | DepartmentName   | CourseID | CourseTitle | Credits | OfferingID | SemesterName | Section | FacultyID | FacultyName | AssessmentID | AssessmentTitle | MarksObtained |
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+-------------+--------------+-----------------+---------------+
|       101 | Alice Johnson | alice@mail.com |         1 | BTech CSE   | UG    |             4 |           10 | Computer Science |      201 | DBMS        |       4 |        301 | Sem1         | A       |       401 | Dr. Smith   |          501 | Midterm         |            85 |
|       101 | Alice Johnson | alice@mail.com |         1 | BTech CSE   | UG    |             4 |           10 | Computer Science |      202 | OS          |       3 |        302 | Sem1         | A       |       402 | Dr. Lee     |          502 | Quiz            |            18 |
+-----------+---------------+----------------+-----------+-------------+-------+---------------+--------------+------------------+----------+-------------+---------+------------+--------------+---------+-----------+-------------+--------------+-----------------+---------------+
```

---

### 4.3 Second Normal Form (2NF)

**Rule Applied:**

All non-key attributes must depend on the entire primary key (not just part of it). Partial dependencies must be eliminated by decomposing the table.

**Identification of Partial Dependencies:**

In 1NF:

- StudentName, Email, ProgramName depend only on StudentID (not the full composite key)
- CourseTitle, Credits depend only on CourseID
- FacultyName depends only on FacultyID
- SemesterName, Section depend only on OfferingID
- AssessmentTitle depends only on AssessmentID

**Changes Made:**

Decomposed the single 1NF table into 8 separate tables:

```sql
CREATE TABLE student_2nf (
    StudentID INT PRIMARY KEY,
    StudentName VARCHAR(100),
    Email VARCHAR(100),
    ProgramID INT
);

CREATE TABLE department_2nf (
    DepartmentID INT PRIMARY KEY,
    DepartmentName VARCHAR(100)
);

CREATE TABLE program_2nf (
    ProgramID INT PRIMARY KEY,
    ProgramName VARCHAR(100),
    Level VARCHAR(50),
    DurationYears INT,
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES department_2nf(DepartmentID)
);

CREATE TABLE course_2nf (
    CourseID INT PRIMARY KEY,
    CourseTitle VARCHAR(100),
    Credits INT,
    ProgramID INT,
    DepartmentID INT,
    FOREIGN KEY (ProgramID) REFERENCES program_2nf(ProgramID)
);

CREATE TABLE offering_2nf (
    OfferingID INT PRIMARY KEY,
    CourseID INT,
    SemesterName VARCHAR(100),
    Section VARCHAR(10),
    FOREIGN KEY (CourseID) REFERENCES course_2nf(CourseID)
);

CREATE TABLE faculty_2nf (
    FacultyID INT PRIMARY KEY,
    FacultyName VARCHAR(100)
);

CREATE TABLE assessment_2nf (
    AssessmentID INT PRIMARY KEY,
    AssessmentTitle VARCHAR(100),
    OfferingID INT,
    FOREIGN KEY (OfferingID) REFERENCES offering_2nf(OfferingID)
);

CREATE TABLE enrollment_2nf (
    StudentID INT,
    OfferingID INT,
    AssessmentID INT,
    MarksObtained INT,
    PRIMARY KEY (StudentID, OfferingID, AssessmentID),
    FOREIGN KEY (StudentID) REFERENCES student_2nf(StudentID),
    FOREIGN KEY (OfferingID) REFERENCES offering_2nf(OfferingID),
    FOREIGN KEY (AssessmentID) REFERENCES assessment_2nf(AssessmentID)
);

INSERT INTO department_2nf VALUES
(10, 'Computer Science');

INSERT INTO program_2nf VALUES
(1, 'BTech CSE', 'UG', 4, 10);

INSERT INTO student_2nf VALUES
(101, 'Alice Johnson', 'alice@mail.com', 1);

INSERT INTO course_2nf VALUES
(201, 'DBMS', 4, 1, 10),
(202, 'OS', 3, 1, 10);

INSERT INTO offering_2nf VALUES
(301, 201, 'Sem1', 'A'),
(302, 202, 'Sem1', 'A');

INSERT INTO faculty_2nf VALUES
(401, 'Dr. Smith'),
(402, 'Dr. Lee');

INSERT INTO assessment_2nf VALUES
(501, 'Midterm', 301),
(502, 'Quiz', 302);

INSERT INTO enrollment_2nf VALUES
(101, 301, 501, 85),
(101, 302, 502, 18);
```

**Result:**

- All partial dependencies eliminated
- Redundancy significantly reduced
- Data organized by business entity

```
--------------
student_2nf
--------------

+-----------+---------------+----------------+-----------+
| StudentID | StudentName   | Email          | ProgramID |
+-----------+---------------+----------------+-----------+
|       101 | Alice Johnson | alice@mail.com |         1 |
+-----------+---------------+----------------+-----------+
--------------
program_2nf
--------------

+-----------+-------------+-------+---------------+--------------+
| ProgramID | ProgramName | Level | DurationYears | DepartmentID |
+-----------+-------------+-------+---------------+--------------+
|         1 | BTech CSE   | UG    |             4 |           10 |
+-----------+-------------+-------+---------------+--------------+
--------------
offering_2nf
--------------

+------------+----------+--------------+---------+
| OfferingID | CourseID | SemesterName | Section |
+------------+----------+--------------+---------+
|        301 |      201 | Sem1         | A       |
|        302 |      202 | Sem1         | A       |
+------------+----------+--------------+---------+
--------------
faculty_2nf
--------------

+-----------+-------------+
| FacultyID | FacultyName |
+-----------+-------------+
|       401 | Dr. Smith   |
|       402 | Dr. Lee     |
+-----------+-------------+
--------------
enrollment_2nf
--------------

+-----------+------------+--------------+---------------+
| StudentID | OfferingID | AssessmentID | MarksObtained |
+-----------+------------+--------------+---------------+
|       101 |        301 |          501 |            85 |
|       101 |        302 |          502 |            18 |
+-----------+------------+--------------+---------------+
--------------
department_2nf
--------------

+--------------+------------------+
| DepartmentID | DepartmentName   |
+--------------+------------------+
|           10 | Computer Science |
+--------------+------------------+
--------------
course_2nf
--------------

+----------+-------------+---------+-----------+--------------+
| CourseID | CourseTitle | Credits | ProgramID | DepartmentID |
+----------+-------------+---------+-----------+--------------+
|      201 | DBMS        |       4 |         1 |           10 |
|      202 | OS          |       3 |         1 |           10 |
+----------+-------------+---------+-----------+--------------+
--------------
assessment_2nf
--------------

+--------------+-----------------+------------+
| AssessmentID | AssessmentTitle | OfferingID |
+--------------+-----------------+------------+
|          501 | Midterm         |        301 |
|          502 | Quiz            |        302 |
+--------------+-----------------+------------+

```

---

### 4.4 Third Normal Form (3NF)

**Rule Applied:**

All non-key attributes must depend only on the primary key. Transitive dependencies (where a non-key attribute depends on another non-key attribute) must be eliminated.

**Identification of Transitive Dependencies:**

In 2NF, the `course_2nf` table exhibits transitive dependency:

```
CourseID (Primary Key) → ProgramID → DepartmentID
```

DepartmentID is a non-key attribute that depends on another non-key attribute (ProgramID), violating 3NF.

**Changes Made:**

Removed DepartmentID from the course table, breaking the transitive dependency chain:

```sql
CREATE TABLE department_3nf (
    DepartmentID INT PRIMARY KEY,
    DepartmentName VARCHAR(100)
);

CREATE TABLE program_3nf (
    ProgramID INT PRIMARY KEY,
    ProgramName VARCHAR(100),
    Level VARCHAR(50),
    DurationYears INT,
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES department_3nf(DepartmentID)
);

CREATE TABLE course_3nf (
    CourseID INT PRIMARY KEY,
    CourseTitle VARCHAR(100),
    Credits INT,
    ProgramID INT,
    FOREIGN KEY (ProgramID) REFERENCES program_3nf(ProgramID)
);

INSERT INTO department_3nf VALUES
(10, 'Computer Science');

INSERT INTO program_3nf VALUES
(1, 'BTech CSE', 'UG', 4, 10);

INSERT INTO course_3nf VALUES
(201, 'DBMS', 4, 1),
(202, 'OS', 3, 1);
```

**Result:**

- No transitive dependencies remain
- Department information flows through Program only
- All non-key attributes depend solely on primary keys
- Clean hierarchical structure: Department → Program → Course

```
--------------
program_3nf
--------------

+-----------+-------------+-------+---------------+--------------+
| ProgramID | ProgramName | Level | DurationYears | DepartmentID |
+-----------+-------------+-------+---------------+--------------+
|         1 | BTech CSE   | UG    |             4 |           10 |
+-----------+-------------+-------+---------------+--------------+
--------------
department_3nf
--------------

+--------------+------------------+
| DepartmentID | DepartmentName   |
+--------------+------------------+
|           10 | Computer Science |
+--------------+------------------+
--------------
course_3nf
--------------

+----------+-------------+---------+-----------+
| CourseID | CourseTitle | Credits | ProgramID |
+----------+-------------+---------+-----------+
|      201 | DBMS        |       4 |         1 |
|      202 | OS          |       3 |         1 |
+----------+-------------+---------+-----------+
```

---

### Boyce-Codd Normal Form (BCNF) Compliance

The final schema in `final_db` represents a database in **BCNF** form, which is a stricter form of 3NF. BCNF ensures that every determinant (attribute or set of attributes upon which another attribute depends) is a candidate key. All 18 tables in the schema have been analyzed for BCNF compliance:

**BCNF Analysis of All Tables in Final Schema:**

1. **PERSON Table:**
   - Primary Key: PersonID
   - Determinants: PersonID (PK), Email (CK)
   - Both determinants are candidate keys. ✓

2. **PERSON_PHONE Table:**
   - Primary Key: (PersonID, PhoneNumber)
   - Determinant: (PersonID, PhoneNumber) - composite PK
   - No non-key attributes; satisfies BCNF trivially. ✓

3. **DEPARTMENT Table:**
   - Primary Key: DepartmentID
   - Determinants: DepartmentID (PK), DepartmentName (CK)
   - Every determinant is a candidate key. ✓

4. **PROGRAM Table:**
   - Primary Key: ProgramID
   - Candidate Key: (ProgramName, DepartmentID)
   - Determinants: ProgramID (PK), (ProgramName, DepartmentID) (CK)
   - All non-key attributes depend only on candidate key. ✓

5. **STUDENT Table:**
   - Primary Key: PersonID
   - Candidate Key: RollNo
   - Determinants: PersonID (PK), RollNo (CK)
   - Every determinant is a candidate key. ✓

6. **FACULTY Table:**
   - Primary Key: PersonID
   - Candidate Key: EmployeeCode
   - Determinants: PersonID (PK), EmployeeCode (CK)
   - Every determinant is a candidate key. ✓

7. **COURSE Table:**
   - Primary Key: CourseID
   - Candidate Key: (CourseTitle, ProgramID)
   - Determinants: CourseID (PK), (CourseTitle, ProgramID) (CK)
   - All non-key attributes depend only on candidate keys. ✓

8. **THEORY_COURSE Table:**
   - Primary Key: CourseID
   - Only Foreign Key reference; no candidate keys
   - Single determinant is the PK. Satisfies BCNF. ✓

9. **LAB_COURSE Table:**
   - Primary Key: CourseID
   - Only Foreign Key reference; no candidate keys
   - Single determinant is the PK. Satisfies BCNF. ✓

10. **SEMESTER Table:**
    - Primary Key: SemesterID
    - Candidate Key: SemesterName
    - Determinants: SemesterID (PK), SemesterName (CK)
    - Every determinant is a candidate key. ✓

11. **COURSE_OFFERING Table:**
    - Primary Key: OfferingID
    - Candidate Key: (CourseID, SemesterID, Section)
    - Determinants: OfferingID (PK), (CourseID, SemesterID, Section) (CK)
    - MaxSeats depends only on candidate keys. ✓

12. **ENROLLMENT Table:**
    - Primary Key: (StudentID, OfferingID)
    - No additional candidate keys
    - Single composite determinant (PK). EnrollmentDate and Status depend on full composite key. ✓

13. **TEACHING_ASSIGNMENT Table:**
    - Primary Key: (FacultyID, OfferingID)
    - No additional candidate keys
    - Single composite determinant (PK). Role and AssignedHours depend on full composite key. ✓

14. **ASSESSMENT Table:**
    - Primary Key: AssessmentID
    - Candidate Key: (Title, OfferingID)
    - Determinants: AssessmentID (PK), (Title, OfferingID) (CK)
    - All non-key attributes depend only on candidate keys. ✓

15. **QUIZ Table:**
    - Primary Key: AssessmentID
    - Instance of Assessment subtype; only Foreign Key
    - No non-key attributes. Satisfies BCNF trivially. ✓

16. **ASSIGNMENT Table:**
    - Primary Key: AssessmentID
    - Instance of Assessment subtype; only Foreign Key
    - No non-key attributes. Satisfies BCNF trivially. ✓

17. **EXAM Table:**
    - Primary Key: AssessmentID
    - Instance of Assessment subtype; only Foreign Key
    - No non-key attributes. Satisfies BCNF trivially. ✓

18. **ASSESSMENT_ITEM Table:**
    - Primary Key: (AssessmentID, ItemName)
    - No additional candidate keys
    - Composite determinant (PK). MaxMarks and Weightage depend on full composite key. ✓

19. **ATTEMPTS Table:**
    - Primary Key: (StudentID, AssessmentID)
    - No additional candidate keys
    - Single composite determinant (PK). MarksObtained and Grade depend on full composite key. ✓

**BCNF Compliance Summary:**

All 18 tables in the final schema satisfy BCNF requirements:

- Every determinant in each table is a candidate key
- No hidden dependencies or violations exist
- All non-key attributes are fully dependent on one of the candidate keys
- No attribute depends on a non-key attribute
- The schema is production-ready, optimized for data integrity, and eliminates all database anomalies
- The hierarchical and entity-type decomposition (Assessment subtypes: QUIZ, ASSIGNMENT, EXAM) maintains referential integrity while satisfying BCNF

---

## 5. Functional Dependencies (Final Schema)

### PERSON Table

**Primary Key:** PersonID
**Candidate Key:** Email

- PersonID → FirstName, LastName, Email, DOB
- Email → PersonID, FirstName, LastName, DOB

### PERSON_PHONE Table

**Primary Key:** (PersonID, PhoneNumber)

- (PersonID, PhoneNumber) → ∅

### DEPARTMENT Table

**Primary Key:** DepartmentID
**Candidate Key:** DepartmentName

- DepartmentID → DepartmentName
- DepartmentName → DepartmentID

### PROGRAM Table

**Primary Key:** ProgramID
**Candidate Key:** (ProgramName, DepartmentID)

- ProgramID → ProgramName, Level, DurationYears, DepartmentID
- (ProgramName, DepartmentID) → ProgramID, Level, DurationYears

### STUDENT Table

**Primary Key:** PersonID
**Candidate Key:** RollNo

- PersonID → RollNo, AdmissionYear, ProgramID
- RollNo → PersonID, AdmissionYear, ProgramID

### FACULTY Table

**Primary Key:** PersonID
**Candidate Key:** EmployeeCode

- PersonID → EmployeeCode, Designation, DepartmentID
- EmployeeCode → PersonID, Designation, DepartmentID

### COURSE Table

**Primary Key:** CourseID
**Candidate Key:** (CourseTitle, ProgramID)

- CourseID → CourseTitle, Credits, ProgramID
- (CourseTitle, ProgramID) → CourseID, Credits

### THEORY_COURSE Table

**Primary Key:** CourseID

- CourseID → LectureHours

### LAB_COURSE Table

**Primary Key:** CourseID

- CourseID → LabHours

### SEMESTER Table

**Primary Key:** SemesterID
**Candidate Key:** SemesterName

- SemesterID → SemesterName, StartDate, EndDate
- SemesterName → SemesterID, StartDate, EndDate

### COURSE_OFFERING Table

**Primary Key:** OfferingID
**Candidate Key:** (CourseID, SemesterID, Section)

- OfferingID → CourseID, SemesterID, Section, MaxSeats
- (CourseID, SemesterID, Section) → OfferingID, MaxSeats

### ENROLLMENT Table

**Primary Key:** (StudentID, OfferingID)

- (StudentID, OfferingID) → EnrollmentDate, Status

### TEACHING_ASSIGNMENT Table

**Primary Key:** (FacultyID, OfferingID)

- (FacultyID, OfferingID) → Role, AssignedHours

### ASSESSMENT Table

**Primary Key:** AssessmentID
**Candidate Key:** (Title, OfferingID)

- AssessmentID → Title, OfferingID
- (Title, OfferingID) → AssessmentID

### QUIZ Table

**Primary Key:** AssessmentID

- AssessmentID → ∅

### ASSIGNMENT Table

**Primary Key:** AssessmentID

- AssessmentID → ∅

### EXAM Table

**Primary Key:** AssessmentID

- AssessmentID → ∅

### ASSESSMENT_ITEM Table

**Primary Key:** (AssessmentID, ItemName)

- (AssessmentID, ItemName) → MaxMarks, Weightage

### ATTEMPTS Table

**Primary Key:** (StudentID, AssessmentID)

- (StudentID, AssessmentID) → MarksObtained, Grade

---

## 6. Table Implementation (Final Schema)

### Table: PERSON

**CREATE Command:**

```sql
CREATE TABLE PERSON (
    PersonID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    DOB DATE NOT NULL
);
```

**INSERT Command:**

```sql
INSERT INTO PERSON (FirstName, LastName, Email, DOB) VALUES
('Rajesh', 'Kumar', 'rajesh.kumar@university.edu', '2003-05-15'),
('Priya', 'Singh', 'priya.singh@university.edu', '2003-08-22'),
('Arjun', 'Patel', 'arjun.patel@university.edu', '2003-11-10'),
('Neha', 'Gupta', 'neha.gupta@university.edu', '2004-02-18'),
('Vikram', 'Sharma', 'vikram.sharma@university.edu', '2003-07-25'),
('Dr. Amit', 'Nair', 'amit.nair@university.edu', '1975-03-12'),
('Dr. Sneha', 'Verma', 'sneha.verma@university.edu', '1978-06-20'),
('Dr. Rahul', 'Das', 'rahul.das@university.edu', '1980-09-08'),
('Prof. Anjali', 'Iyer', 'anjali.iyer@university.edu', '1977-12-15'),
('Prof. Anil', 'Bhat', 'anil.bhat@university.edu', '1982-01-30');
```

```
+----------+--------------+----------+------------------------------+------------+
| PersonID | FirstName    | LastName | Email                        | DOB        |
+----------+--------------+----------+------------------------------+------------+
|        1 | Rajesh       | Kumar    | rajesh.kumar@university.edu  | 2003-05-15 |
|        2 | Priya        | Singh    | priya.singh@university.edu   | 2003-08-22 |
|        3 | Arjun        | Patel    | arjun.patel@university.edu   | 2003-11-10 |
|        4 | Neha         | Gupta    | neha.gupta@university.edu    | 2004-02-18 |
|        5 | Vikram       | Sharma   | vikram.sharma@university.edu | 2003-07-25 |
|        6 | Dr. Amit     | Nair     | amit.nair@university.edu     | 1975-03-12 |
|        7 | Dr. Sneha    | Verma    | sneha.verma@university.edu   | 1978-06-20 |
|        8 | Dr. Rahul    | Das      | rahul.das@university.edu     | 1980-09-08 |
|        9 | Prof. Anjali | Iyer     | anjali.iyer@university.edu   | 1977-12-15 |
|       10 | Prof. Anil   | Bhat     | anil.bhat@university.edu     | 1982-01-30 |
+----------+--------------+----------+------------------------------+------------+
```

---

### Table: PERSON_PHONE

**CREATE Command:**

```sql
CREATE TABLE PERSON_PHONE (
    PersonID INT NOT NULL,
    PhoneNumber VARCHAR(20) NOT NULL,
    PRIMARY KEY (PersonID, PhoneNumber),
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE
);
```

**INSERT Command:**

```sql
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
```

```
+----------+----------------+
| PersonID | PhoneNumber    |
+----------+----------------+
|        1 | +91-8765432109 |
|        1 | +91-9876543210 |
|        2 | +91-9765432109 |
|        3 | +91-9654321098 |
|        4 | +91-9543210987 |
|        5 | +91-9432109876 |
|        6 | +91-9876543200 |
|        7 | +91-9876543201 |
|        8 | +91-9876543202 |
|        9 | +91-9876543203 |
|       10 | +91-9876543204 |
+----------+----------------+
```

---

### Table: DEPARTMENT

**CREATE Command:**

```sql
CREATE TABLE DEPARTMENT (
    DepartmentID INT PRIMARY KEY AUTO_INCREMENT,
    DepartmentName VARCHAR(100) NOT NULL UNIQUE
);
```

**INSERT Command:**

```sql
INSERT INTO DEPARTMENT (DepartmentName) VALUES
('Computer Science'),
('Information Technology'),
('Electronics and Communication'),
('Electrical Engineering'),
('Mechanical Engineering');
```

```
+--------------+-------------------------------+
| DepartmentID | DepartmentName                |
+--------------+-------------------------------+
|            1 | Computer Science              |
|            4 | Electrical Engineering        |
|            3 | Electronics and Communication |
|            2 | Information Technology        |
|            5 | Mechanical Engineering        |
+--------------+-------------------------------+
```

---

### Table: PROGRAM

**CREATE Command:**

```sql
CREATE TABLE PROGRAM (
    ProgramID INT PRIMARY KEY AUTO_INCREMENT,
    ProgramName VARCHAR(100) NOT NULL,
    Level VARCHAR(50) NOT NULL,
    DurationYears INT NOT NULL,
    DepartmentID INT NOT NULL,
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID) ON DELETE RESTRICT,
    UNIQUE KEY (ProgramName, DepartmentID),
    CHECK (DurationYears BETWEEN 1 AND 6)
);
```

**INSERT Command:**

```sql
INSERT INTO PROGRAM (ProgramName, Level, DurationYears, DepartmentID) VALUES
('Bachelor of Technology in CSE', 'Undergraduate', 4, 1),
('Bachelor of Technology in IT', 'Undergraduate', 4, 2),
('Master of Technology in CSE', 'Postgraduate', 2, 1),
('Bachelor of Technology in ECE', 'Undergraduate', 4, 3),
('Bachelor of Technology in EE', 'Undergraduate', 4, 4);
```

```
+-----------+-------------------------------+---------------+---------------+--------------+
| ProgramID | ProgramName                   | Level         | DurationYears | DepartmentID |
+-----------+-------------------------------+---------------+---------------+--------------+
|         1 | Bachelor of Technology in CSE | Undergraduate |             4 |            1 |
|         2 | Bachelor of Technology in IT  | Undergraduate |             4 |            2 |
|         3 | Master of Technology in CSE   | Postgraduate  |             2 |            1 |
|         4 | Bachelor of Technology in ECE | Undergraduate |             4 |            3 |
|         5 | Bachelor of Technology in EE  | Undergraduate |             4 |            4 |
+-----------+-------------------------------+---------------+---------------+--------------+
```

---

### Table: STUDENT

**CREATE Command:**

```sql
CREATE TABLE STUDENT (
    PersonID INT PRIMARY KEY,
    RollNo VARCHAR(20) NOT NULL UNIQUE,
    AdmissionYear INT NOT NULL,
    ProgramID INT NOT NULL,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID) ON DELETE RESTRICT
);
```

**INSERT Command:**

```sql
INSERT INTO STUDENT (PersonID, RollNo, AdmissionYear, ProgramID) VALUES
(1, 'CSE001', 2021, 1),
(2, 'IT001', 2021, 2),
(3, 'CSE002', 2021, 1),
(4, 'ECE001', 2021, 4),
(5, 'EE001', 2021, 5);
```

```
+----------+--------+---------------+-----------+
| PersonID | RollNo | AdmissionYear | ProgramID |
+----------+--------+---------------+-----------+
|        1 | CSE001 |          2021 |         1 |
|        2 | IT001  |          2021 |         2 |
|        3 | CSE002 |          2021 |         1 |
|        4 | ECE001 |          2021 |         4 |
|        5 | EE001  |          2021 |         5 |
+----------+--------+---------------+-----------+
```

---

### Table: FACULTY

**CREATE Command:**

```sql
CREATE TABLE FACULTY (
    PersonID INT PRIMARY KEY,
    EmployeeCode VARCHAR(20) NOT NULL UNIQUE,
    Designation VARCHAR(50) NOT NULL,
    DepartmentID INT NOT NULL,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID) ON DELETE RESTRICT
);
```

**INSERT Command:**

```sql
INSERT INTO FACULTY (PersonID, EmployeeCode, Designation, DepartmentID) VALUES
(6, 'FAC001', 'Assistant Professor', 1),
(7, 'FAC002', 'Associate Professor', 2),
(8, 'FAC003', 'Assistant Professor', 1),
(9, 'FAC004', 'Professor', 3),
(10, 'FAC005', 'Associate Professor', 4);
```

```
+----------+--------------+---------------------+--------------+
| PersonID | EmployeeCode | Designation         | DepartmentID |
+----------+--------------+---------------------+--------------+
|        6 | FAC001       | Assistant Professor |            1 |
|        7 | FAC002       | Associate Professor |            2 |
|        8 | FAC003       | Assistant Professor |            1 |
|        9 | FAC004       | Professor           |            3 |
|       10 | FAC005       | Associate Professor |            4 |
+----------+--------------+---------------------+--------------+

```

---

### Table: COURSE

**CREATE Command:**

```sql
CREATE TABLE COURSE (
    CourseID INT PRIMARY KEY AUTO_INCREMENT,
    CourseTitle VARCHAR(100) NOT NULL,
    Credits INT NOT NULL,
    ProgramID INT NOT NULL,
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID) ON DELETE RESTRICT,
    CHECK (Credits BETWEEN 1 AND 6),
    UNIQUE KEY (CourseTitle, ProgramID)
);
```

**INSERT Command:**

```sql
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
```

```
+----------+-----------------------------+---------+-----------+
| CourseID | CourseTitle                 | Credits | ProgramID |
+----------+-----------------------------+---------+-----------+
|        1 | Data Structures             |       4 |         1 |
|        2 | Database Management Systems |       4 |         1 |
|        3 | Web Development             |       3 |         1 |
|        4 | Algorithms                  |       4 |         1 |
|        5 | Operating Systems           |       3 |         1 |
|        6 | Information Security        |       4 |         2 |
|        7 | Network Administration      |       3 |         2 |
|        8 | Software Engineering        |       4 |         2 |
|        9 | Digital Electronics         |       4 |         4 |
|       10 | Microprocessors             |       3 |         4 |
+----------+-----------------------------+---------+-----------+

```

---

### Table: THEORY_COURSE

**CREATE Command:**

```sql
CREATE TABLE THEORY_COURSE (
    CourseID INT PRIMARY KEY,
    LectureHours INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID) ON DELETE CASCADE,
    CHECK (LectureHours > 0)
);
```

**INSERT Command:**

```sql
INSERT INTO THEORY_COURSE (CourseID, LectureHours) VALUES
(1, 45),
(2, 48),
(3, 36),
(4, 45),
(5, 36),
(6, 45),
(7, 36),
(8, 48);
```

```
+----------+--------------+
| CourseID | LectureHours |
+----------+--------------+
|        1 |           45 |
|        2 |           48 |
|        3 |           36 |
|        4 |           45 |
|        5 |           36 |
|        6 |           45 |
|        7 |           36 |
|        8 |           48 |
+----------+--------------+

```

---

### Table: LAB_COURSE

**CREATE Command:**

```sql
CREATE TABLE LAB_COURSE (
    CourseID INT PRIMARY KEY,
    LabHours INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID) ON DELETE CASCADE,
    CHECK (LabHours > 0)
);
```

**INSERT Command:**

```sql
INSERT INTO LAB_COURSE (CourseID, LabHours) VALUES
(9, 30),
(10, 24);
```

```
+----------+----------+
| CourseID | LabHours |
+----------+----------+
|        9 |       30 |
|       10 |       24 |
+----------+----------+
```

---

### Table: SEMESTER

**CREATE Command:**

```sql
CREATE TABLE SEMESTER (
    SemesterID INT PRIMARY KEY AUTO_INCREMENT,
    SemesterName VARCHAR(50) NOT NULL UNIQUE,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    CHECK (EndDate > StartDate)
);
```

**INSERT Command:**

```sql
INSERT INTO SEMESTER (SemesterName, StartDate, EndDate) VALUES
('Spring 2024', '2024-01-15', '2024-05-15'),
('Summer 2024', '2024-06-01', '2024-07-31'),
('Fall 2024', '2024-08-15', '2024-12-15');
```

```
+------------+--------------+------------+------------+
| SemesterID | SemesterName | StartDate  | EndDate    |
+------------+--------------+------------+------------+
|          1 | Spring 2024  | 2024-01-15 | 2024-05-15 |
|          2 | Summer 2024  | 2024-06-01 | 2024-07-31 |
|          3 | Fall 2024    | 2024-08-15 | 2024-12-15 |
+------------+--------------+------------+------------+
```

---

### Table: COURSE_OFFERING

**CREATE Command:**

```sql
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
```

**INSERT Command:**

```sql
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
```

```
+------------+----------+------------+---------+----------+
| OfferingID | CourseID | SemesterID | Section | MaxSeats |
+------------+----------+------------+---------+----------+
|          1 |        1 |          1 | A       |       50 |
|          2 |        1 |          1 | B       |       50 |
|          3 |        2 |          1 | A       |       40 |
|          4 |        3 |          1 | A       |       60 |
|          5 |        4 |          1 | A       |       45 |
|          6 |        5 |          1 | A       |       40 |
|          7 |        6 |          1 | A       |       45 |
|          8 |        7 |          1 | A       |       50 |
|          9 |        8 |          1 | A       |       50 |
|         10 |        9 |          1 | A       |       40 |
|         11 |       10 |          1 | A       |       35 |
|         12 |        1 |          3 | A       |       50 |
|         13 |        2 |          3 | A       |       40 |
|         14 |        4 |          3 | A       |       45 |
|         15 |        8 |          3 | A       |       50 |
+------------+----------+------------+---------+----------+
```

---

### Table: ENROLLMENT

**CREATE Command:**

```sql
CREATE TABLE ENROLLMENT (
    StudentID INT NOT NULL,
    OfferingID INT NOT NULL,
    EnrollmentDate DATE NOT NULL,
    Status ENUM('Active', 'Completed', 'Dropped', 'Inactive') NOT NULL,
    PRIMARY KEY (StudentID, OfferingID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE
);
```

**INSERT Command:**

```sql
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
```

```
+-----------+------------+----------------+--------+
| StudentID | OfferingID | EnrollmentDate | Status |
+-----------+------------+----------------+--------+
|         1 |          1 | 2024-01-15     | Active |
|         1 |          2 | 2024-01-15     | Active |
|         1 |          4 | 2024-01-15     | Active |
|         1 |         12 | 2024-08-15     | Active |
|         2 |          7 | 2024-01-15     | Active |
|         2 |          8 | 2024-01-15     | Active |
|         2 |         14 | 2024-08-15     | Active |
|         3 |          3 | 2024-01-15     | Active |
|         3 |          5 | 2024-01-15     | Active |
|         4 |          9 | 2024-01-15     | Active |
|         5 |         10 | 2024-01-15     | Active |
+-----------+------------+----------------+--------+
```

---

### Table: TEACHING_ASSIGNMENT

**CREATE Command:**

```sql
CREATE TABLE TEACHING_ASSIGNMENT (
    FacultyID INT NOT NULL,
    OfferingID INT NOT NULL,
    Role ENUM('Instructor', 'Teaching Assistant', 'Lab Assistant') NOT NULL,
    AssignedHours INT NOT NULL,
    PRIMARY KEY (FacultyID, OfferingID),
    FOREIGN KEY (FacultyID) REFERENCES FACULTY(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE,
    CHECK (AssignedHours > 0)
);
```

**INSERT Command:**

```sql
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
```

```
+-----------+------------+------------+---------------+
| FacultyID | OfferingID | Role       | AssignedHours |
+-----------+------------+------------+---------------+
|         6 |          1 | Instructor |            45 |
|         6 |          2 | Instructor |            45 |
|         6 |          5 | Instructor |            45 |
|         6 |         12 | Instructor |            45 |
|         7 |          7 | Instructor |            36 |
|         7 |          8 | Instructor |            48 |
|         7 |         14 | Instructor |            45 |
|         8 |          3 | Instructor |            36 |
|         8 |          4 | Instructor |            36 |
|         9 |          9 | Instructor |            30 |
|        10 |         10 | Instructor |            24 |
+-----------+------------+------------+---------------+
```

---

### Table: ASSESSMENT

**CREATE Command:**

```sql
CREATE TABLE ASSESSMENT (
    AssessmentID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(100) NOT NULL,
    OfferingID INT NOT NULL,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID) ON DELETE CASCADE,
    UNIQUE KEY (Title, OfferingID)
);
```

**INSERT Command:**

```sql
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
```

```
+--------------+-------------------------------------+------------+
| AssessmentID | Title                               | OfferingID |
+--------------+-------------------------------------+------------+
|            3 | Assignment 1 - Web Dev              |          4 |
|            4 | Final Exam - Algorithms             |          5 |
|            9 | Lab Assessment - Microprocessors    |         10 |
|            7 | Lab Project - Software Engineering  |          8 |
|            2 | Mid-term Exam - DBMS                |          3 |
|            8 | Mid-term Exam - Digital Electronics |          9 |
|            5 | Mid-term Exam - Operating Systems   |          6 |
|            1 | Quiz 1 - Data Structures            |          1 |
|            6 | Quiz 1 - InfoSec                    |          8 |
+--------------+-------------------------------------+------------+
```

---

### Table: QUIZ

**CREATE Command:**

```sql
CREATE TABLE QUIZ (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);
```

**INSERT Command:**

```sql
INSERT INTO QUIZ (AssessmentID) VALUES (1), (6);
```

```
+--------------+
| AssessmentID |
+--------------+
|            1 |
|            6 |
+--------------+
```

---

### Table: ASSIGNMENT

**CREATE Command:**

```sql
CREATE TABLE ASSIGNMENT (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);
```

**INSERT Command:**

```sql
INSERT INTO ASSIGNMENT (AssessmentID) VALUES (3), (7);
```

```
+--------------+
| AssessmentID |
+--------------+
|            3 |
|            7 |
+--------------+
```

---

### Table: EXAM

**CREATE Command:**

```sql
CREATE TABLE EXAM (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE
);
```

**INSERT Command:**

```sql
INSERT INTO EXAM (AssessmentID) VALUES (2), (4), (5), (8), (9);
```

```
+--------------+
| AssessmentID |
+--------------+
|            2 |
|            4 |
|            5 |
|            8 |
|            9 |
+--------------+
```

---

### Table: ASSESSMENT_ITEM

**CREATE Command:**

```sql
CREATE TABLE ASSESSMENT_ITEM (
    AssessmentID INT NOT NULL,
    ItemName VARCHAR(100) NOT NULL,
    MaxMarks INT NOT NULL,
    Weightage DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (AssessmentID, ItemName),
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE,
    CHECK (MaxMarks > 0 AND Weightage > 0 AND Weightage <= 100)
);
```

**INSERT Command:**

```sql
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
```

```
+--------------+---------------------+----------+-----------+
| AssessmentID | ItemName            | MaxMarks | Weightage |
+--------------+---------------------+----------+-----------+
|            1 | Multiple Choice     |       20 |     50.00 |
|            1 | Short Answer        |       20 |     50.00 |
|            2 | Problem Solving     |       50 |     60.00 |
|            2 | Theoretical         |       30 |     40.00 |
|            3 | Code Quality        |       50 |     50.00 |
|            3 | Functionality       |       50 |     50.00 |
|            4 | Algorithms          |       60 |     70.00 |
|            4 | Implementation      |       40 |     30.00 |
|            5 | Conceptual          |       40 |     50.00 |
|            5 | Practical           |       40 |     50.00 |
|            6 | MCQ                 |       25 |    100.00 |
|            7 | Code Implementation |      100 |     70.00 |
|            7 | Documentation       |       30 |     30.00 |
|            8 | Circuit Design      |       50 |     50.00 |
|            8 | Theory              |       50 |     50.00 |
|            9 | Practical Work      |      100 |    100.00 |
+--------------+---------------------+----------+-----------+
```

---

### Table: ATTEMPTS

**CREATE Command:**

```sql
CREATE TABLE ATTEMPTS (
    StudentID INT NOT NULL,
    AssessmentID INT NOT NULL,
    MarksObtained INT NOT NULL,
    Grade ENUM('A+', 'A', 'B+', 'B', 'C+', 'C', 'D', 'F'),
    PRIMARY KEY (StudentID, AssessmentID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID) ON DELETE CASCADE,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID) ON DELETE CASCADE,
    CHECK (MarksObtained >= 0)
);
```

**INSERT Command:**

```sql
INSERT INTO ATTEMPTS (StudentID, AssessmentID, MarksObtained, Grade) VALUES
(1, 1, 18, 'A'),
(1, 2, 42, 'B+'),
(1, 4, 55, 'A'),
(2, 6, 22, 'A+'),
(2, 7, 85, 'A'),
(3, 3, 45, 'B'),
(3, 5, 68, 'A'),
(4, 8, 48, 'B+'),
(5, 9, 92, 'A+'),
(1, 3, 38, 'B'),
(2, 8, 42, 'B+');
```

```
+-----------+--------------+---------------+-------+
| StudentID | AssessmentID | MarksObtained | Grade |
+-----------+--------------+---------------+-------+
|         1 |            1 |            18 | A     |
|         1 |            2 |            42 | B+    |
|         1 |            3 |            38 | B     |
|         1 |            4 |            55 | A     |
|         2 |            6 |            22 | A+    |
|         2 |            7 |            85 | A     |
|         2 |            8 |            42 | B+    |
|         3 |            3 |            45 | B     |
|         3 |            5 |            68 | A     |
|         4 |            8 |            48 | B+    |
|         5 |            9 |            92 | A+    |
+-----------+--------------+---------------+-------+
```

---

## 7. Functions

### Function: calculate_gpa

**Definition:**

```sql
CREATE FUNCTION calculate_gpa(p_student_id INT)
RETURNS DECIMAL(3,2)
DETERMINISTIC READS SQL DATA
BEGIN
  RETURN IFNULL((SELECT ROUND(AVG(CASE WHEN Grade='A+' THEN 4.0 WHEN Grade='A' THEN 4.0 WHEN Grade='B+' THEN 3.5 WHEN Grade='B' THEN 3.0 WHEN Grade='C+' THEN 2.5 WHEN Grade='C' THEN 2.0 WHEN Grade='D' THEN 1.0 ELSE 0 END), 2) FROM ATTEMPTS WHERE StudentID = p_student_id AND Grade IS NOT NULL), 0);
END
```

**Explanation:**

Calculates the cumulative GPA of a student based on their assessment grades. Grade values are mapped to standard GPA points (A+ = 4.0, B+ = 3.5, B = 3.0, C+ = 2.5, C = 2.0, D = 1.0). The function returns the average GPA rounded to 2 decimal places, or 0 if no grades have been recorded.

```
--------------
SELECT STUDENT.*, calculate_gpa(PersonID) AS GPA FROM STUDENT
--------------

+----------+--------+---------------+-----------+------+
| PersonID | RollNo | AdmissionYear | ProgramID | GPA  |
+----------+--------+---------------+-----------+------+
|        1 | CSE001 |          2021 |         1 | 3.63 |
|        2 | IT001  |          2021 |         2 | 3.83 |
|        3 | CSE002 |          2021 |         1 | 3.50 |
|        4 | ECE001 |          2021 |         4 | 3.50 |
|        5 | EE001  |          2021 |         5 | 4.00 |
+----------+--------+---------------+-----------+------+

```

---

### Function: count_enrollments

**Definition:**

```sql
CREATE FUNCTION count_enrollments(p_offering_id INT)
RETURNS INT
DETERMINISTIC READS SQL DATA
RETURN IFNULL((SELECT COUNT(*) FROM ENROLLMENT WHERE OfferingID = p_offering_id AND Status='Active'), 0)
```

**Explanation:**

Returns the count of active enrollments in a specific course offering. This function is useful for checking course capacity and enrollment statistics.

```
--------------
SELECT COURSE_OFFERING.*, count_enrollments(OfferingID) AS CourseEnrollments FROM COURSE_OFFERING
--------------

+------------+----------+------------+---------+----------+-------------------+
| OfferingID | CourseID | SemesterID | Section | MaxSeats | CourseEnrollments |
+------------+----------+------------+---------+----------+-------------------+
|          1 |        1 |          1 | A       |       50 |                 1 |
|          2 |        1 |          1 | B       |       50 |                 1 |
|          3 |        2 |          1 | A       |       40 |                 1 |
|          4 |        3 |          1 | A       |       60 |                 1 |
|          5 |        4 |          1 | A       |       45 |                 1 |
|          6 |        5 |          1 | A       |       40 |                 0 |
|          7 |        6 |          1 | A       |       45 |                 1 |
|          8 |        7 |          1 | A       |       50 |                 1 |
|          9 |        8 |          1 | A       |       50 |                 1 |
|         10 |        9 |          1 | A       |       40 |                 1 |
|         11 |       10 |          1 | A       |       35 |                 0 |
|         12 |        1 |          3 | A       |       50 |                 1 |
|         13 |        2 |          3 | A       |       40 |                 0 |
|         14 |        4 |          3 | A       |       45 |                 1 |
|         15 |        8 |          3 | A       |       50 |                 0 |
+------------+----------+------------+---------+----------+-------------------+
```

---

### Function: get_student_enrollment_count

**Definition:**

```sql
CREATE FUNCTION get_student_enrollment_count(p_student_id INT)
RETURNS INT
DETERMINISTIC READS SQL DATA
RETURN (SELECT COUNT(*) FROM ENROLLMENT WHERE StudentID = p_student_id AND Status='Active')
```

**Explanation:**

Returns the number of active courses a student is currently enrolled in. Used to track student workload and enrollment status.

```
+----------+--------+---------------+-----------+-------------+
| PersonID | RollNo | AdmissionYear | ProgramID | ENROLLMENTS |
+----------+--------+---------------+-----------+-------------+
|        1 | CSE001 |          2021 |         1 |           4 |
|        2 | IT001  |          2021 |         2 |           3 |
|        3 | CSE002 |          2021 |         1 |           2 |
|        4 | ECE001 |          2021 |         4 |           1 |
|        5 | EE001  |          2021 |         5 |           1 |
+----------+--------+---------------+-----------+-------------+
```

---

### Function: get_faculty_teaching_load

**Definition:**

```sql
CREATE FUNCTION get_faculty_teaching_load(p_faculty_id INT)
RETURNS INT
DETERMINISTIC READS SQL DATA
RETURN (SELECT COUNT(DISTINCT OfferingID) FROM TEACHING_ASSIGNMENT WHERE FacultyID = p_faculty_id)
```

**Explanation:**

Returns the count of distinct course offerings a faculty member is assigned to teach. Helps in workload management and resource allocation.

```
+----------+--------------+---------------------+--------------+------+
| PersonID | EmployeeCode | Designation         | DepartmentID | LOAD |
+----------+--------------+---------------------+--------------+------+
|        6 | FAC001       | Assistant Professor |            1 |    4 |
|        7 | FAC002       | Associate Professor |            2 |    3 |
|        8 | FAC003       | Assistant Professor |            1 |    2 |
|        9 | FAC004       | Professor           |            3 |    1 |
|       10 | FAC005       | Associate Professor |            4 |    1 |
+----------+--------------+---------------------+--------------+------+
```

---

## 8. Stored Procedures

### Procedure: enroll_student

**Definition:**

```sql
CREATE PROCEDURE enroll_student(IN p_student_id INT, IN p_offering_id INT)
BEGIN
  INSERT IGNORE INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status)
  VALUES (p_student_id, p_offering_id, CURDATE(), 'Active');
END
```

**Explanation:**

Enrolls a student in a course offering. Uses INSERT IGNORE to prevent duplicate enrollments. Sets enrollment date to the current date and marks the enrollment status as 'Active'. Triggers are automatically enforced to prevent over-enrollment and duplicate records.

[Insert Procedure Output Screenshot Here]

---

### Procedure: get_student_results

**Definition:**

```sql
CREATE PROCEDURE get_student_results(IN p_student_id INT)
BEGIN
  SELECT p.FirstName, p.LastName, s.RollNo, c.CourseTitle, a.Title, att.MarksObtained, att.Grade
  FROM STUDENT s JOIN PERSON p ON s.PersonID = p.PersonID
  JOIN ENROLLMENT e ON s.PersonID = e.StudentID
  JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
  JOIN COURSE c ON co.CourseID = c.CourseID
  LEFT JOIN ASSESSMENT a ON co.OfferingID = a.OfferingID
  LEFT JOIN ATTEMPTS att ON s.PersonID = att.StudentID AND a.AssessmentID = att.AssessmentID
  WHERE s.PersonID = p_student_id;
END
```

**Explanation:**

Retrieves comprehensive academic results for a specific student. Returns student name, roll number, enrolled courses, assessment titles, marks obtained, and grades. Uses LEFT JOIN to include assessments even if the student hasn't attempted them yet.

```
--------------
CALL get_student_results(1)
--------------

+-----------+----------+--------+-----------------+--------------------------+---------------+-------+
| FirstName | LastName | RollNo | CourseTitle     | Title                    | MarksObtained | Grade |
+-----------+----------+--------+-----------------+--------------------------+---------------+-------+
| Rajesh    | Kumar    | CSE001 | Data Structures | Quiz 1 - Data Structures |            18 | A     |
| Rajesh    | Kumar    | CSE001 | Data Structures | NULL                     |          NULL | NULL  |
| Rajesh    | Kumar    | CSE001 | Web Development | Assignment 1 - Web Dev   |            38 | B     |
| Rajesh    | Kumar    | CSE001 | Data Structures | NULL                     |          NULL | NULL  |
+-----------+----------+--------+-----------------+--------------------------+---------------+-------+
```

---

## 9. Triggers

### Trigger: prevent_enrollment_if_full

**Definition:**

```sql
CREATE TRIGGER prevent_enrollment_if_full
BEFORE INSERT ON ENROLLMENT
FOR EACH ROW
BEGIN
  IF (SELECT COUNT(*) FROM ENROLLMENT WHERE OfferingID = NEW.OfferingID) >=
     (SELECT MaxSeats FROM COURSE_OFFERING WHERE OfferingID = NEW.OfferingID) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Course full';
  END IF;
END
```

**Explanation:**

Enforces course capacity constraint. Before inserting an enrollment record, checks if the current enrollment count has reached the maximum seat capacity. If the course is full, raises an error and prevents the enrollment.

[Insert Trigger Output Screenshot Here]

---

### Trigger: prevent_duplicate_enrollment

**Definition:**

```sql
CREATE TRIGGER prevent_duplicate_enrollment
BEFORE INSERT ON ENROLLMENT
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT 1 FROM ENROLLMENT WHERE StudentID = NEW.StudentID AND OfferingID = NEW.OfferingID) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Student already enrolled in this course';
  END IF;
END
```

**Explanation:**

Prevents duplicate enrollments. Before inserting an enrollment record, verifies that the student is not already enrolled in the same course offering. If a duplicate enrollment is detected, raises an error and rejects the operation.

[Insert Trigger Output Screenshot Here]

---

## 10. Queries

### Query 1: Student Enrollment Summary

**SQL:**

```sql
SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, co.Section, sem.SemesterName, e.Status
FROM ENROLLMENT e
JOIN STUDENT s ON e.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
ORDER BY s.RollNo;
```

**Explanation:**

Displays all active student enrollments with associated course information. Shows student roll number, name, course title, section, semester, and enrollment status. Helps track which students are enrolled in which courses across different semesters.

```
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
```

---

### Query 2: Course Enrollment Statistics

**SQL:**

```sql
SELECT c.CourseTitle, co.Section, sem.SemesterName, COUNT(e.StudentID) AS Enrolled, co.MaxSeats
FROM COURSE_OFFERING co
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
LEFT JOIN ENROLLMENT e ON co.OfferingID = e.OfferingID AND e.Status = 'Active'
GROUP BY co.OfferingID, c.CourseTitle, co.Section, sem.SemesterName, co.MaxSeats
ORDER BY c.CourseTitle;
```

**Explanation:**

Provides enrollment capacity analysis for each course offering. Shows course title, section, semester, current enrollment count, and maximum seat capacity. Includes offerings with zero enrollments using LEFT JOIN. Helps identify over-enrolled, under-enrolled, and fully-subscribed courses.

```
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
```

---

### Query 3: Student Assessment Performance

**SQL:**

```sql
SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, att.MarksObtained, att.Grade
FROM ATTEMPTS att
JOIN STUDENT s ON att.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
ORDER BY s.RollNo;
```

**Explanation:**

Retrieves individual student assessment results including marks obtained and assigned grades. Correlates assessment performance with course information. Enables performance tracking and grade analysis for academic planning and student feedback.

```
+--------+-----------+----------+-----------------------------+---------------+-------+
| RollNo | FirstName | LastName | CourseTitle                 | MarksObtained | Grade |
+--------+-----------+----------+-----------------------------+---------------+-------+
| CSE001 | Rajesh    | Kumar    | Data Structures             |            18 | A     |
| CSE001 | Rajesh    | Kumar    | Database Management Systems |            42 | B+    |
| CSE001 | Rajesh    | Kumar    | Web Development             |            38 | B     |
| CSE001 | Rajesh    | Kumar    | Algorithms                  |            55 | A     |
| CSE002 | Arjun     | Patel    | Web Development             |            45 | B     |
| CSE002 | Arjun     | Patel    | Operating Systems           |            68 | A     |
| ECE001 | Neha      | Gupta    | Software Engineering        |            48 | B+    |
| EE001  | Vikram    | Sharma   | Digital Electronics         |            92 | A+    |
| IT001  | Priya     | Singh    | Network Administration      |            22 | A+    |
| IT001  | Priya     | Singh    | Network Administration      |            85 | A     |
| IT001  | Priya     | Singh    | Software Engineering        |            42 | B+    |
+--------+-----------+----------+-----------------------------+---------------+-------+
```

---

**SQL:**

```sql
SELECT p.FirstName, p.LastName, f.EmployeeCode, f.Designation, c.CourseTitle, d.DepartmentName
FROM TEACHING_ASSIGNMENT ta
JOIN FACULTY f ON ta.FacultyID = f.PersonID
JOIN PERSON p ON f.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON ta.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN DEPARTMENT d ON f.DepartmentID = d.DepartmentID
ORDER BY p.LastName;
```

**Explanation:**

Lists faculty members with their assigned courses, employee codes, designations, and departments. Provides administrative insight into faculty workload distribution across course offerings. Useful for course scheduling, workload balancing, and resource allocation.

```
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
```

---

### Query 5: Student Pass Rate Analysis by Course

**SQL:**

```sql
SELECT c.CourseTitle, co.Section, COUNT(DISTINCT att.StudentID) AS StudentsPassed
FROM ATTEMPTS att
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
WHERE att.MarksObtained >= 40
GROUP BY co.OfferingID, c.CourseTitle, co.Section
ORDER BY c.CourseTitle;
```

**Explanation:**

Counts the number of students who achieved a passing score (40 marks or above) in each course offering. Results are grouped by course and section to show pass rate patterns across different course sections. Supports academic quality assessment and course difficulty analysis.

```
+-----------------------------+---------+----------------+
| CourseTitle                 | Section | StudentsPassed |
+-----------------------------+---------+----------------+
| Algorithms                  | A       |              1 |
| Database Management Systems | A       |              1 |
| Digital Electronics         | A       |              1 |
| Network Administration      | A       |              1 |
| Operating Systems           | A       |              1 |
| Software Engineering        | A       |              2 |
| Web Development             | A       |              1 |
+-----------------------------+---------+----------------+
```

---

### Query 6: Program Student Distribution

**SQL:**

```sql
SELECT prog.ProgramName, d.DepartmentName, COUNT(DISTINCT s.PersonID) AS TotalStudents
FROM STUDENT s
JOIN PROGRAM prog ON s.ProgramID = prog.ProgramID
JOIN DEPARTMENT d ON prog.DepartmentID = d.DepartmentID
GROUP BY prog.ProgramID, prog.ProgramName, d.DepartmentName
ORDER BY prog.ProgramName;
```

**Explanation:**

Aggregates total student enrollment across academic programs, grouped by program and department. Shows program headcounts and active enrollment rates. Supports institutional planning, resource allocation decisions, and program-level analytics.

```
+-------------------------------+-------------------------------+---------------+
| ProgramName                   | DepartmentName                | TotalStudents |
+-------------------------------+-------------------------------+---------------+
| Bachelor of Technology in CSE | Computer Science              |             2 |
| Bachelor of Technology in ECE | Electronics and Communication |             1 |
| Bachelor of Technology in EE  | Electrical Engineering        |             1 |
| Bachelor of Technology in IT  | Information Technology        |             1 |
+-------------------------------+-------------------------------+---------------+
```

---

## End of Report
