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


/*
THIRD NORMAL FORM (3NF)

✔ Removed transitive dependency:
  CourseID → ProgramID → DepartmentID

✔ Fix:
  - Removed DepartmentID from COURSE
  - Department now depends ONLY on Program

✔ Now:
  - All non-key attributes depend ONLY on primary key
  - No attribute depends on another non-key attribute

✔ Structure preserved from 2NF:
  No redesign, only refinement

✔ Matches final schema hierarchy:
  Department → Program → Course → Offering → Enrollment → Assessment

✔ Ready for BCNF:
  - All determinants are candidate keys
  - No hidden dependencies remain
*/