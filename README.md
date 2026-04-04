# DBMS Academic Management System

MySQL-based Academic Management System with:

* Normalization demo (UNF → 3NF)
* Production BCNF database (24 tables)
* Java (JDBC) + Swing GUI for interaction

---

## Project Overview

This project demonstrates:

* Step-by-step database normalization
* A fully normalized academic management system (BCNF)
* Stored procedures, functions, and triggers
* Java integration using JDBC
* GUI-based interaction with database operations

---

## Project Structure

```
mini_project/
├── normalization_demo/
├── final_db/
├── java-integration/
└── README.md
```

---

## Database Setup

### 1. Create Database

```sql
CREATE DATABASE academic_management;
USE academic_management;
```

---

### 2. Normalization Demo (`normalization_demo/`)

Run SQL files in order:

* `unf.sql` → Repeating groups (unnormalized)
* `1nf.sql` → Atomic values
* `2nf.sql` → Removes partial dependencies
* `3nf.sql` → Removes transitive dependencies

This demonstrates the full normalization process.

---

### 3. Production Database (`final_db/`)

Run SQL files in order:

1. `schema.sql` → 24-table schema
2. `procedures.sql` → Stored procedures
3. `functions.sql` → Functions
4. `inserts.sql` → Sample data
5. `triggers.sql` → Constraints via triggers

---

## Data Validation & Constraints

### CHECK Constraints

* Age ≥ 0
* Credits, Hours, Seats, Marks > 0
* Weightage between 0 and 100
* Valid enums for Status, Grade, Pass/Fail

### Referential Integrity

* Foreign keys enforce valid relationships
* CASCADE DELETE removes dependent records
* RESTRICT prevents invalid deletions

---

## Testing DB Logic (CLI)

### Procedures

```sql
CALL enroll_student(1, 1);
CALL get_student_results(1);
```

### Functions

```sql
SELECT calculate_gpa(1);
SELECT count_enrollments(1);
```

### Triggers

* Prevent enrollment if course is full
* Automatically compute pass/fail

---

## Java Application Setup

### 1. JDBC Driver

Download your java jar file (or have it ready) for sql connection, and add it to this project through your ide

if you're on vs code:
first cd into java-integration/src
then do Ctrl + Shift + P → **Java: Configure Classpath**
go into libraries, add your jar file there


After the jar file is added for the project,
cd out into java-integration folder and run this:

mkdir lib
cp /path/to/jar-file lib/

---

### 2. Configure Credentials

Edit:

```
java-integration/src/database/DBConnection.java
```

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/academic_management";
private static final String USER = "your_username";
private static final String PASS = "your_password";
```

---

### 3. Run Application

```bash
cd java-integration
chmod +x run.sh
./run.sh
```
On windows:

cd java-integration
bash run.sh
---

## Java Project Structure

```
java-integration/
├── lib/
├── src/
│   ├── Main.java
│   ├── database/
│   ├── service/
│   └── ui/
```

---

## How Java Works (Under the Hood)

```
Main.java
    ↓
MainUI (Swing GUI)
    ↓
StudentService (logic)
    ↓
DBConnection (JDBC)
    ↓
MySQL Database
```

### Flow

1. User clicks a button in GUI
2. ActionListener triggers a method
3. StudentService executes SQL or procedure
4. DBConnection creates connection using DriverManager
5. Results returned via ResultSet
6. Output shown in GUI or terminal

---

## Java Features

* CRUD operations (INSERT, UPDATE, DELETE, SELECT)
* Stored Procedures:

  * enroll_student()
  * get_student_results()
* Functions:

  * calculate_gpa()

---

## GUI Usage (IMPORTANT)

### Input Fields

* Name, Email, DOB, Age
* PersonID (for update/delete)
* OfferingID (for enrollment)

---

### Correct Usage Flow

#### 1. INSERT

* Fill all fields except PersonID
* Click **INSERT**
* Note generated ID

#### 2. VIEW

* Click **VIEW**
* Output appears in terminal

#### 3. UPDATE

* Enter PersonID + new values

#### 4. DELETE

* Enter PersonID

#### 5. ENROLL

* Enter PersonID + OfferingID

#### 6. GET RESULTS

* Enter PersonID

#### 7. CALC GPA

* Enter PersonID

---

## Testing Workflow

1. **Setup database first** (run all SQL files)
2. **INSERT** a student → note the PersonID
3. **UPDATE** using that PersonID + new data
4. **VIEW ALL** → check terminal output
5. **ENROLL** with StudentID=1, OfferingID=1
6. **GET RESULTS** → check terminal output (not GUI)
7. **CALC GPA** → shows in GUI
8. **DELETE** the inserted student

### Important

* Some outputs print to **terminal/console**, not GUI (VIEW ALL, GET RESULTS)
* Find valid IDs:
  ```bash
  mysql academic_management -e "SELECT PersonID FROM STUDENT LIMIT 1;"
  ```
* Database must be fully setup before running Java app

---

## Concepts Demonstrated

* JDBC (DriverManager, PreparedStatement, CallableStatement)
* OOP (classes, interface, packages)
* Exception handling (try-catch)
* Swing GUI (event-driven programming)
* Database normalization & integrity

---

## Important Notes

* Database must be fully set up before running Java
* Some outputs appear in terminal
* Valid IDs required for operations
* Designed for academic evaluation (simple, not overengineered)

---

## Summary

This project combines:

* DBMS theory (normalization)
* Practical database design (BCNF)
* Backend logic (procedures, triggers)
* Java integration (JDBC)
* GUI interaction (Swing)

All implemented using beginner-level concepts aligned with coursework.
