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

 /*
UNF
- Entire system flattened into one table
- Multiple values stored in single attributes (CourseID, FacultyID, Marks, etc.)
- No atomicity, heavy redundancy
- Represents raw, unstructured data before normalization
*/