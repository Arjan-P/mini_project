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
