CREATE TABLE student_2nf (
    StudentID INT PRIMARY KEY,
    StudentName VARCHAR(100)
);

CREATE TABLE course_2nf (
    CourseName VARCHAR(100) PRIMARY KEY,
    FacultyName VARCHAR(100)
);

CREATE TABLE enrollment_2nf (
    StudentID INT,
    CourseName VARCHAR(100),
    Marks INT,
    PRIMARY KEY (StudentID, CourseName),
    FOREIGN KEY (StudentID) REFERENCES student_2nf(StudentID),
    FOREIGN KEY (CourseName) REFERENCES course_2nf(CourseName)
);

INSERT INTO student_2nf VALUES
(101, 'Alice Johnson'),
(102, 'Bob Wilson'),
(103, 'Carol Davis'),
(104, 'David Miller'),
(105, 'Eve Martinez');

INSERT INTO course_2nf VALUES
('Database Systems', 'Dr. Smith'),
('Web Development', 'Dr. Brown'),
('Data Structures', 'Dr. Johnson'),
('Algorithms', 'Dr. Lee');

INSERT INTO enrollment_2nf (StudentID, CourseName, Marks) VALUES
(101, 'Database Systems', 85),
(101, 'Web Development', 92),
(102, 'Data Structures', 78),
(102, 'Algorithms', 88),
(103, 'Database Systems', 90),
(103, 'Algorithms', 92),
(103, 'Web Development', 88),
(104, 'Web Development', 95),
(105, 'Data Structures', 82),
(105, 'Database Systems', 86);

SELECT 'Student Table' AS TableName;
SELECT * FROM student_2nf;
SELECT 'Course Table' AS TableName;
SELECT * FROM course_2nf;
SELECT 'Enrollment Table' AS TableName;
SELECT * FROM enrollment_2nf;

/*
SECOND NORMAL FORM (2NF)
- Remove partial dependencies
- Non-key attributes must depend on the ENTIRE primary key, not just part of it
- Separated into 3 tables: Student, Course, Enrollment
- StudentName now only in Student table (depends on StudentID)
- FacultyName now only in Course table (depends on CourseName)
- Enrollment table references both via foreign keys
*/

/*test*/
