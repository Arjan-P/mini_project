DROP TABLE IF EXISTS student_course_1nf;

CREATE TABLE student_course_1nf (
    StudentID INT,
    StudentName VARCHAR(100),
    CourseName VARCHAR(100),
    FacultyName VARCHAR(100),
    Marks INT,
    PRIMARY KEY (StudentID, CourseName)
);

INSERT INTO student_course_1nf (StudentID, StudentName, CourseName, FacultyName, Marks) VALUES
(101, 'Alice Johnson', 'Database Systems', 'Dr. Smith', 85),
(101, 'Alice Johnson', 'Web Development', 'Dr. Brown', 92),
(102, 'Bob Wilson', 'Data Structures', 'Dr. Johnson', 78),
(102, 'Bob Wilson', 'Algorithms', 'Dr. Lee', 88),
(103, 'Carol Davis', 'Database Systems', 'Dr. Smith', 90),
(103, 'Carol Davis', 'Algorithms', 'Dr. Lee', 92),
(103, 'Carol Davis', 'Web Development', 'Dr. Brown', 88),
(104, 'David Miller', 'Web Development', 'Dr. Brown', 95),
(105, 'Eve Martinez', 'Data Structures', 'Dr. Johnson', 82),
(105, 'Eve Martinez', 'Database Systems', 'Dr. Smith', 86);

SELECT * FROM student_course_1nf;

/*
FIRST NORMAL FORM (1NF)
- Atomic values only - each cell contains a single value
- Remove repeating groups by creating separate rows
- Primary key: (StudentID, CourseName)
- Still contains partial dependencies (StudentName depends only on StudentID, not on CourseName)
*/
