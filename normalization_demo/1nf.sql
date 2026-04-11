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


 /*
1NF
- All attributes are atomic (no comma-separated values)
- Repeating groups removed into rows
- Still massive redundancy:
  Student, Program, Department repeated
- Partial + transitive dependencies exist
*/