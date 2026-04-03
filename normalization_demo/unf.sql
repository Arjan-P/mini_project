DROP TABLE IF EXISTS student_course_unf;

CREATE TABLE student_course_unf (
    StudentID INT PRIMARY KEY,
    StudentName VARCHAR(100),
    CourseName VARCHAR(200),
    FacultyName VARCHAR(100),
    Marks VARCHAR(100)
);

INSERT INTO student_course_unf VALUES
(101, 'Alice Johnson', 'Database Systems, Web Development', 'Dr. Smith, Dr. Brown', '85, 92'),
(102, 'Bob Wilson', 'Data Structures, Algorithms', 'Dr. Johnson, Dr. Lee', '78, 88'),
(103, 'Carol Davis', 'Database Systems, Algorithms, Web Development', 'Dr. Smith, Dr. Lee, Dr. Brown', '90, 92, 88'),
(104, 'David Miller', 'Web Development', 'Dr. Brown', '95'),
(105, 'Eve Martinez', 'Data Structures, Database Systems', 'Dr. Johnson, Dr. Smith', '82, 86');

SELECT * FROM student_course_unf;

/*
UNNORMALIZED FORM (UNF)
- Shows data with repeating groups and redundancy
- StudentID, StudentName, CourseName, FacultyName, Marks stored as comma-separated values
- Demonstrates data anomalies and redundancy issues
- Multiple courses and marks in a single cell (not atomic)
*/
