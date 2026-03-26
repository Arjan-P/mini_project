-- Initialize tables from schema
-- 
--The 3NF Normalized Schema
-- PERSON(PersonID PK, FirstName, LastName, Email, DOB)

-- PERSON_PHONE(PersonID PK/FK, PhoneNumber PK)

-- STUDENT(PersonID PK/FK, RollNo, AdmissionYear, ProgramID FK)

-- FACULTY(PersonID PK/FK, EmployeeCode, Designation, DepartmentID FK)

-- DEPARTMENT(DepartmentID PK, DepartmentName)

-- PROGRAM(ProgramID PK, ProgramName, Level, DurationYears, DepartmentID FK)

-- COURSE(CourseID PK, CourseTitle, Credits, ProgramID FK)

-- THEORY_COURSE(CourseID PK/FK, LectureHours)

-- LAB_COURSE(CourseID PK/FK, LabHours)

-- SEMESTER(SemesterID PK, SemesterName, StartDate, EndDate)

-- COURSE_OFFERING(OfferingID PK, CourseID FK, SemesterID FK, Section, MaxSeats)

-- ENROLLMENT(StudentID PK/FK, OfferingID PK/FK, EnrollmentDate, Status)

-- TEACHING_ASSIGNMENT(FacultyID PK/FK, OfferingID PK/FK, Role, AssignedHours)

-- ASSESSMENT(AssessmentID PK, Title, OfferingID FK)

-- QUIZ(AssessmentID PK/FK)

-- ASSIGNMENT(AssessmentID PK/FK)

-- EXAM(AssessmentID PK/FK)

-- ASSESSMENT_ITEM(AssessmentID PK/FK, ItemName PK, MaxMarks, Weightage)

-- ATTEMPTS(StudentID PK/FK, AssessmentID PK/FK, MarksObtained)
-- 1. Independent/Parent Tables
CREATE TABLE PERSON (
    PersonID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    DOB DATE
);

CREATE TABLE DEPARTMENT (
    DepartmentID INT PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL
);

CREATE TABLE SEMESTER (
    SemesterID INT PRIMARY KEY,
    SemesterName VARCHAR(50),
    StartDate DATE,
    EndDate DATE
);

-- 2. Sub-types and Dependent Tables
CREATE TABLE PERSON_PHONE (
    PersonID INT,
    PhoneNumber VARCHAR(15),
    PRIMARY KEY (PersonID, PhoneNumber),
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID) ON DELETE CASCADE
);

CREATE TABLE FACULTY (
    PersonID INT PRIMARY KEY,
    EmployeeCode VARCHAR(20) UNIQUE,
    Designation VARCHAR(50),
    DepartmentID INT,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID),
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID)
);

CREATE TABLE PROGRAM (
    ProgramID INT PRIMARY KEY,
    ProgramName VARCHAR(100),
    Level VARCHAR(50), -- e.g., Undergraduate, Graduate
    DurationYears INT,
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES DEPARTMENT(DepartmentID)
);

CREATE TABLE STUDENT (
    PersonID INT PRIMARY KEY,
    RollNo VARCHAR(20) UNIQUE,
    AdmissionYear INT,
    ProgramID INT,
    FOREIGN KEY (PersonID) REFERENCES PERSON(PersonID),
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID)
);

-- 3. Course Hierarchy
CREATE TABLE COURSE (
    CourseID INT PRIMARY KEY,
    CourseTitle VARCHAR(100),
    Credits INT,
    ProgramID INT,
    FOREIGN KEY (ProgramID) REFERENCES PROGRAM(ProgramID)
);

CREATE TABLE THEORY_COURSE (
    CourseID INT PRIMARY KEY,
    LectureHours INT,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID)
);

CREATE TABLE LAB_COURSE (
    CourseID INT PRIMARY KEY,
    LabHours INT,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID)
);

-- 4. Academic Operations
CREATE TABLE COURSE_OFFERING (
    OfferingID INT PRIMARY KEY,
    CourseID INT,
    SemesterID INT,
    Section VARCHAR(5),
    MaxSeats INT,
    FOREIGN KEY (CourseID) REFERENCES COURSE(CourseID),
    FOREIGN KEY (SemesterID) REFERENCES SEMESTER(SemesterID)
);

CREATE TABLE ENROLLMENT (
    StudentID INT,
    OfferingID INT,
    EnrollmentDate DATE,
    Status VARCHAR(20), -- e.g., Enrolled, Dropped, Completed
    PRIMARY KEY (StudentID, OfferingID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID),
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID)
);

CREATE TABLE TEACHING_ASSIGNMENT (
    FacultyID INT,
    OfferingID INT,
    Role VARCHAR(50), -- e.g., Primary Instructor, TA
    AssignedHours INT,
    PRIMARY KEY (FacultyID, OfferingID),
    FOREIGN KEY (FacultyID) REFERENCES FACULTY(PersonID),
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID)
);

-- 5. Assessments
CREATE TABLE ASSESSMENT (
    AssessmentID INT PRIMARY KEY,
    Title VARCHAR(100),
    OfferingID INT,
    FOREIGN KEY (OfferingID) REFERENCES COURSE_OFFERING(OfferingID)
);

CREATE TABLE QUIZ (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID)
);

CREATE TABLE ASSIGNMENT (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID)
);

CREATE TABLE EXAM (
    AssessmentID INT PRIMARY KEY,
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID)
);

CREATE TABLE ASSESSMENT_ITEM (
    AssessmentID INT,
    ItemName VARCHAR(100),
    MaxMarks DECIMAL(5,2),
    Weightage DECIMAL(5,2),
    PRIMARY KEY (AssessmentID, ItemName),
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID)
);

CREATE TABLE ATTEMPTS (
    StudentID INT,
    AssessmentID INT,
    MarksObtained DECIMAL(5,2),
    PRIMARY KEY (StudentID, AssessmentID),
    FOREIGN KEY (StudentID) REFERENCES STUDENT(PersonID),
    FOREIGN KEY (AssessmentID) REFERENCES ASSESSMENT(AssessmentID)
);