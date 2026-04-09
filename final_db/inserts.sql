
USE academic_management;


INSERT INTO DEPARTMENT (DepartmentName) VALUES
('Computer Science'),
('Information Technology'),
('Electronics and Communication'),
('Electrical Engineering'),
('Mechanical Engineering');


INSERT INTO PROGRAM (ProgramName, Level, DurationYears, DepartmentID) VALUES
('Bachelor of Technology in CSE', 'Undergraduate', 4, 1),
('Bachelor of Technology in IT', 'Undergraduate', 4, 2),
('Master of Technology in CSE', 'Postgraduate', 2, 1),
('Bachelor of Technology in ECE', 'Undergraduate', 4, 3),
('Bachelor of Technology in EE', 'Undergraduate', 4, 4);


INSERT INTO PERSON (FirstName, LastName, Email, DOB) VALUES
('Rajesh', 'Kumar', 'rajesh.kumar@university.edu', '2003-05-15'),
('Priya', 'Singh', 'priya.singh@university.edu', '2003-08-22'),
('Arjun', 'Patel', 'arjun.patel@university.edu', '2003-11-10'),
('Neha', 'Gupta', 'neha.gupta@university.edu', '2004-02-18'),
('Vikram', 'Sharma', 'vikram.sharma@university.edu', '2003-07-25'),
('Dr. Amit', 'Nair', 'amit.nair@university.edu', '1975-03-12'),
('Dr. Sneha', 'Verma', 'sneha.verma@university.edu', '1978-06-20'),
('Dr. Rahul', 'Das', 'rahul.das@university.edu', '1980-09-08'),
('Prof. Anjali', 'Iyer', 'anjali.iyer@university.edu', '1977-12-15'),
('Prof. Anil', 'Bhat', 'anil.bhat@university.edu', '1982-01-30');


INSERT INTO PERSON_PHONE (PersonID, PhoneNumber) VALUES
(1, '+91-9876543210'),
(1, '+91-8765432109'),
(2, '+91-9765432109'),
(3, '+91-9654321098'),
(4, '+91-9543210987'),
(5, '+91-9432109876'),
(6, '+91-9876543200'),
(7, '+91-9876543201'),
(8, '+91-9876543202'),
(9, '+91-9876543203'),
(10, '+91-9876543204');


INSERT INTO STUDENT (PersonID, RollNo, AdmissionYear, ProgramID) VALUES
(1, 'CSE001', 2021, 1),
(2, 'IT001', 2021, 2),
(3, 'CSE002', 2021, 1),
(4, 'ECE001', 2021, 4),
(5, 'EE001', 2021, 5);


INSERT INTO FACULTY (PersonID, EmployeeCode, Designation, DepartmentID) VALUES
(6, 'FAC001', 'Assistant Professor', 1),
(7, 'FAC002', 'Associate Professor', 2),
(8, 'FAC003', 'Assistant Professor', 1),
(9, 'FAC004', 'Professor', 3),
(10, 'FAC005', 'Associate Professor', 4);


INSERT INTO COURSE (CourseTitle, Credits, ProgramID) VALUES
('Data Structures', 4, 1),
('Database Management Systems', 4, 1),
('Web Development', 3, 1),
('Algorithms', 4, 1),
('Operating Systems', 3, 1),
('Information Security', 4, 2),
('Network Administration', 3, 2),
('Software Engineering', 4, 2),
('Digital Electronics', 4, 4),
('Microprocessors', 3, 4);


INSERT INTO THEORY_COURSE (CourseID, LectureHours) VALUES
(1, 45),
(2, 48),
(3, 36),
(4, 45),
(5, 36),
(6, 45),
(7, 36),
(8, 48);


INSERT INTO LAB_COURSE (CourseID, LabHours) VALUES
(9, 30),
(10, 24);


INSERT INTO SEMESTER (SemesterName, StartDate, EndDate) VALUES
('Spring 2024', '2024-01-15', '2024-05-15'),
('Summer 2024', '2024-06-01', '2024-07-31'),
('Fall 2024', '2024-08-15', '2024-12-15');


INSERT INTO COURSE_OFFERING (CourseID, SemesterID, Section, MaxSeats) VALUES
(1, 1, 'A', 50),
(1, 1, 'B', 50),
(2, 1, 'A', 40),
(3, 1, 'A', 60),
(4, 1, 'A', 45),
(5, 1, 'A', 40),
(6, 1, 'A', 45),
(7, 1, 'A', 50),
(8, 1, 'A', 50),
(9, 1, 'A', 40),
(10, 1, 'A', 35),
(1, 3, 'A', 50),
(2, 3, 'A', 40),
(4, 3, 'A', 45),
(8, 3, 'A', 50);


INSERT INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status) VALUES
(1, 1, '2024-01-15', 'Active'),
(1, 2, '2024-01-15', 'Active'),
(1, 4, '2024-01-15', 'Active'),
(2, 8, '2024-01-15', 'Active'),
(2, 7, '2024-01-15', 'Active'),
(3, 5, '2024-01-15', 'Active'),
(3, 3, '2024-01-15', 'Active'),
(4, 9, '2024-01-15', 'Active'),
(5, 10, '2024-01-15', 'Active'),
(1, 12, '2024-08-15', 'Active'),
(2, 14, '2024-08-15', 'Active');


INSERT INTO TEACHING_ASSIGNMENT (FacultyID, OfferingID, Role, AssignedHours) VALUES
(6, 1, 'Instructor', 45),
(6, 2, 'Instructor', 45),
(8, 4, 'Instructor', 36),
(7, 8, 'Instructor', 48),
(7, 7, 'Instructor', 36),
(6, 5, 'Instructor', 45),
(8, 3, 'Instructor', 36),
(9, 9, 'Instructor', 30),
(10, 10, 'Instructor', 24),
(6, 12, 'Instructor', 45),
(7, 14, 'Instructor', 45);


INSERT INTO ASSESSMENT (Title, OfferingID) VALUES
('Quiz 1 - Data Structures', 1),
('Mid-term Exam - DBMS', 3),
('Assignment 1 - Web Dev', 4),
('Final Exam - Algorithms', 5),
('Mid-term Exam - Operating Systems', 6),
('Quiz 1 - InfoSec', 8),
('Lab Project - Software Engineering', 8),
('Mid-term Exam - Digital Electronics', 9),
('Lab Assessment - Microprocessors', 10);


INSERT INTO QUIZ (AssessmentID) VALUES (1), (6);


INSERT INTO ASSIGNMENT (AssessmentID) VALUES (3), (7);


INSERT INTO EXAM (AssessmentID) VALUES (2), (4), (5), (8), (9);


INSERT INTO ASSESSMENT_ITEM (AssessmentID, ItemName, MaxMarks, Weightage) VALUES
(1, 'Multiple Choice', 20, 50.0),
(1, 'Short Answer', 20, 50.0),
(2, 'Problem Solving', 50, 60.0),
(2, 'Theoretical', 30, 40.0),
(3, 'Code Quality', 50, 50.0),
(3, 'Functionality', 50, 50.0),
(4, 'Algorithms', 60, 70.0),
(4, 'Implementation', 40, 30.0),
(5, 'Conceptual', 40, 50.0),
(5, 'Practical', 40, 50.0),
(6, 'MCQ', 25, 100.0),
(7, 'Code Implementation', 100, 70.0),
(7, 'Documentation', 30, 30.0),
(8, 'Circuit Design', 50, 50.0),
(8, 'Theory', 50, 50.0),
(9, 'Practical Work', 100, 100.0);


INSERT INTO ATTEMPTS (StudentID, AssessmentID, MarksObtained, Grade) VALUES
(1, 1, 18, 'A'),
(1, 2, 42, 'B+'),
(1, 4, 55, 'A'),
(2, 6, 22, 'A+'),
(2, 7, 85, 'A'),
(3, 3, 45, 'B'),
(3, 5, 68, 'A'),
(4, 8, 48, 'B+'),
(5, 9, 92, 'A+'),
(1, 3, 38, 'B'),
(2, 8, 42, 'B+');

