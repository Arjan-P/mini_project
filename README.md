# DBMS Academic Management System

MySQL backend with two separate databases: one for normalization examples and one for production BCNF schema. Includes optimized procedures, functions, and triggers.

---

## Setup: Two Separate Databases

### Database 1: Normalization Demo (`normalization_demo/`)

Run the SQL files in order: unf.sql → 1nf.sql → 2nf.sql → 3nf.sql. Each file creates its own test database showing progressive normalization.

**Normalization Progression:**
- **unf.sql** - Unnormalized: Repeating groups (comma-separated StudentName, CourseName, Marks)
- **1nf.sql** - First Normal Form: Atomic values only; each row is one enrollment
- **2nf.sql** - Second Normal Form: Splits into 3 tables (Student, Course, Enrollment) to eliminate partial dependencies
- **3nf.sql** - Third Normal Form: Separate Faculty table to eliminate transitive dependencies (faculty not stored in course)

### Database 2: Production BCNF (`final_db/`)

Run the SQL files in this order to create the production database:
1. **schema.sql** - Creates 24 tables with the full schema
2. **inserts.sql** - Loads sample data (50+ records)
3. **procedures.sql** - Creates the stored procedures
4. **functions.sql** - Creates the stored functions
5. **triggers.sql** - Creates the database triggers
6. **queries.sql** (optional) - Sample queries to explore the data

---

## Data Validation & Constraints

**CHECK Constraints:**
- `Age ≥ 0`
- `Credits > 0`, `LectureHours > 0`, `LabHours > 0`
- `MaxSeats > 0`, `MaxMarks > 0`
- `Weightage: 0 < Weightage ≤ 100`
- `Enrollment Status: 'Active', 'Completed', 'Dropped', 'Inactive'`
- `PassFail: 'Pass', 'Fail'`
- `Grade: A+, A, B+, B, C+, C, D, F`

**Foreign Key Constraints:**
- CASCADE DELETE on child records when parent deleted
- RESTRICT on critical relationships (prevents orphaned records)

**Referential Integrity:**
- All enrollment references valid students & course offerings
- All teaching assignments reference valid faculty & course offerings
- All attempts reference valid students & assessments

---

## Testing Procedures, Functions & Triggers

### Procedures

**Test `enroll_student()`:**
```sql
CALL enroll_student(1, 1);  -- Enroll student 1 in offering 1
```

**Test `get_student_results()`:**
```sql
CALL get_student_results(1);  -- Get all results for student 1
```

### Functions

**Test `calculate_gpa()`:**
```sql
SELECT calculate_gpa(1) AS StudentGPA;
-- Returns GPA based on grades: A+=4.0, A=4.0, B+=3.5, B=3.0, C+=2.5, C=2.0, D=1.0, F=0.0
```

**Test `count_enrollments()`:**
```sql
SELECT count_enrollments(1) AS TotalEnrolled;
-- Returns count of active enrollments in offering 1
```

### Triggers

**Test `prevent_enrollment_if_full`:**
```sql
-- Set max seats to 1
UPDATE COURSE_OFFERING SET MaxSeats = 1 WHERE OfferingID = 1;

-- Try to enroll second student (should fail with "Course full")
INSERT INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status)
VALUES (2, 1, CURDATE(), 'Active');
```

**Test `compute_pass_fail_on_insert`:**
```sql
-- Insert attempt with marks < 40 (should set PassFail = 'Fail')
INSERT INTO ATTEMPTS (StudentID, AssessmentID, MarksObtained, Grade)
VALUES (1, 1, 35, 'D');

-- Check result
SELECT PassFail FROM ATTEMPTS WHERE StudentID = 1 AND AssessmentID = 1;
```

---

## File Structure

```
mini_project/
├── normalization_demo/
│   ├── unf.sql    (Unnormalized)
│   ├── 1nf.sql    (Atomic values)
│   ├── 2nf.sql    (No partial deps)
│   └── 3nf.sql    (No transitive deps)
│
├── final_db/
│   ├── schema.sql      (24 tables)
│   ├── inserts.sql     (50+ records)
│   ├── procedures.sql  (2 procedures)
│   ├── functions.sql   (2 functions)
│   ├── triggers.sql    (2 triggers)
│   └── queries.sql     (6 queries)
│
└── README.md
```


