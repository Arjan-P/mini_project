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