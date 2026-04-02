CREATE TABLE student_3nf (
    StudentID INT PRIMARY KEY,
    StudentName VARCHAR(100)
);

CREATE TABLE faculty_3nf (
    FacultyID INT PRIMARY KEY,
    FacultyName VARCHAR(100)
);

CREATE TABLE course_3nf (
    CourseID INT PRIMARY KEY,
    CourseName VARCHAR(100),
    FacultyID INT,
    FOREIGN KEY (FacultyID) REFERENCES faculty_3nf(FacultyID)
);

CREATE TABLE enrollment_3nf (
    StudentID INT,
    CourseID INT,
    Marks INT,
    PRIMARY KEY (StudentID, CourseID),
    FOREIGN KEY (StudentID) REFERENCES student_3nf(StudentID),
    FOREIGN KEY (CourseID) REFERENCES course_3nf(CourseID)
);

INSERT INTO student_3nf VALUES
(101, 'Alice Johnson'),
(102, 'Bob Wilson'),
(103, 'Carol Davis'),
(104, 'David Miller'),
(105, 'Eve Martinez');

INSERT INTO faculty_3nf VALUES
(1, 'Dr. Smith'),
(2, 'Dr. Brown'),
(3, 'Dr. Johnson'),
(4, 'Dr. Lee');

INSERT INTO course_3nf (CourseID, CourseName, FacultyID) VALUES
(201, 'Database Systems', 1),
(202, 'Web Development', 2),
(203, 'Data Structures', 3),
(204, 'Algorithms', 4);

INSERT INTO enrollment_3nf (StudentID, CourseID, Marks) VALUES
(101, 201, 85),
(101, 202, 92),
(102, 203, 78),
(102, 204, 88),
(103, 201, 90),
(103, 204, 92),
(103, 202, 88),
(104, 202, 95),
(105, 203, 82),
(105, 201, 86);

SELECT 'Student Table' AS TableName;
SELECT * FROM student_3nf;
SELECT 'Faculty Table' AS TableName;
SELECT * FROM faculty_3nf;
SELECT 'Course Table' AS TableName;
SELECT * FROM course_3nf;
SELECT 'Enrollment with Faculty Information' AS TableName;
SELECT e.StudentID, s.StudentName, e.CourseID, c.CourseName, f.FacultyName, e.Marks
FROM enrollment_3nf e
JOIN student_3nf s ON e.StudentID = s.StudentID
JOIN course_3nf c ON e.CourseID = c.CourseID
JOIN faculty_3nf f ON c.FacultyID = f.FacultyID;

/*
THIRD NORMAL FORM (3NF)
- Remove transitive dependencies
- Non-key attributes must not depend on other non-key attributes
- FacultyName removed from Course table - now independent Faculty table
- Faculty is independent entity, Course depends on Faculty via ForeignKey
- All non-key attributes depend ONLY on primary key
- Complete BCNF design with no redundancy or anomalies
*/
