-- Seed the tables with test data
-- 1. Base Tables (No Foreign Keys)
INSERT INTO PERSON (PersonID, FirstName, LastName, Email, DOB) VALUES 
(1, 'Alice', 'Smith', 'alice.s@university.edu', '2004-05-12'),
(2, 'Bob', 'Johnson', 'bob.j@university.edu', '2003-11-23'),
(101, 'Sarah', 'Miller', 's.miller@university.edu', '1980-08-15'),
(102, 'David', 'Wilson', 'd.wilson@university.edu', '1975-03-22');

INSERT INTO DEPARTMENT (DepartmentID, DepartmentName) VALUES 
(1, 'Computer Science'),
(2, 'Mathematics');

INSERT INTO SEMESTER (SemesterID, SemesterName, StartDate, EndDate) VALUES 
(1, 'Spring 2026', '2026-01-15', '2026-05-20'),
(2, 'Fall 2026', '2026-08-25', '2026-12-15');

-- 2. Tables dependent on Person or Department
INSERT INTO PERSON_PHONE (PersonID, PhoneNumber) VALUES 
(1, '555-0101'),
(1, '555-0102'),
(101, '555-9999');

INSERT INTO PROGRAM (ProgramID, ProgramName, Level, DurationYears, DepartmentID) VALUES 
(1, 'B.Tech CS', 'Undergraduate', 4, 1),
(2, 'M.Sc Math', 'Graduate', 2, 2);

-- 3. Student, Faculty, and Course
INSERT INTO STUDENT (PersonID, RollNo, AdmissionYear, ProgramID) VALUES 
(1, 'CS24-001', 2024, 1),
(2, 'CS24-002', 2024, 1);

INSERT INTO FACULTY (PersonID, EmployeeCode, Designation, DepartmentID) VALUES 
(101, 'EMP001', 'Professor', 1),
(102, 'EMP002', 'Associate Professor', 2);

INSERT INTO COURSE (CourseID, CourseTitle, Credits, ProgramID) VALUES 
(10, 'Database Systems', 4, 1),
(11, 'Advanced Calculus', 3, 2),
(12, 'Python Lab', 2, 1);

-- 4. Course Subtypes
INSERT INTO THEORY_COURSE (CourseID, LectureHours) VALUES 
(10, 3),
(11, 3);

INSERT INTO LAB_COURSE (CourseID, LabHours) VALUES 
(12, 4);

-- 5. Offerings and Assignments
INSERT INTO COURSE_OFFERING (OfferingID, CourseID, SemesterID, Section, MaxSeats) VALUES 
(500, 10, 1, 'A', 60),
(501, 12, 1, 'L1', 30);

INSERT INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status) VALUES 
(1, 500, '2026-01-10', 'Enrolled'),
(2, 500, '2026-01-10', 'Enrolled'),
(1, 501, '2026-01-11', 'Enrolled');

INSERT INTO TEACHING_ASSIGNMENT (FacultyID, OfferingID, Role, AssignedHours) VALUES 
(101, 500, 'Lead Instructor', 3),
(101, 501, 'Lab Supervisor', 2);

-- 6. Assessments and Results
INSERT INTO ASSESSMENT (AssessmentID, Title, OfferingID) VALUES 
(1001, 'Midterm Quiz', 500),
(1002, 'Final Project', 500);

INSERT INTO QUIZ (AssessmentID) VALUES (1001);
INSERT INTO ASSIGNMENT (AssessmentID) VALUES (1002);

INSERT INTO ASSESSMENT_ITEM (AssessmentID, ItemName, MaxMarks, Weightage) VALUES 
(1001, 'Multiple Choice', 20, 10.0),
(1001, 'Short Answer', 30, 15.0);

INSERT INTO ATTEMPTS (StudentID, AssessmentID, MarksObtained) VALUES 
(1, 1001, 18.5),
(2, 1001, 15.0);