
SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, co.Section, sem.SemesterName, e.Status
FROM ENROLLMENT e
JOIN STUDENT s ON e.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
ORDER BY s.RollNo;

SELECT c.CourseTitle, co.Section, sem.SemesterName, COUNT(e.StudentID) AS Enrolled, co.MaxSeats
FROM COURSE_OFFERING co
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN SEMESTER sem ON co.SemesterID = sem.SemesterID
LEFT JOIN ENROLLMENT e ON co.OfferingID = e.OfferingID AND e.Status = 'Active'
GROUP BY co.OfferingID, c.CourseTitle, co.Section, sem.SemesterName, co.MaxSeats
ORDER BY c.CourseTitle;

SELECT s.RollNo, p.FirstName, p.LastName, c.CourseTitle, att.MarksObtained, att.Grade, att.PassFail
FROM ATTEMPTS att
JOIN STUDENT s ON att.StudentID = s.PersonID
JOIN PERSON p ON s.PersonID = p.PersonID
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
ORDER BY s.RollNo;

SELECT p.FirstName, p.LastName, f.EmployeeCode, f.Designation, c.CourseTitle, d.DepartmentName
FROM TEACHING_ASSIGNMENT ta
JOIN FACULTY f ON ta.FacultyID = f.PersonID
JOIN PERSON p ON f.PersonID = p.PersonID
JOIN COURSE_OFFERING co ON ta.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
JOIN DEPARTMENT d ON f.DepartmentID = d.DepartmentID
ORDER BY p.LastName;

SELECT c.CourseTitle, co.Section, COUNT(DISTINCT att.StudentID) AS StudentsPassed
FROM ATTEMPTS att
JOIN ASSESSMENT a ON att.AssessmentID = a.AssessmentID
JOIN COURSE_OFFERING co ON a.OfferingID = co.OfferingID
JOIN COURSE c ON co.CourseID = c.CourseID
WHERE att.PassFail = 'Pass'
GROUP BY co.OfferingID, c.CourseTitle, co.Section
ORDER BY c.CourseTitle;

SELECT prog.ProgramName, d.DepartmentName, COUNT(DISTINCT s.PersonID) AS TotalStudents
FROM STUDENT s
JOIN PROGRAM prog ON s.ProgramID = prog.ProgramID
JOIN DEPARTMENT d ON prog.DepartmentID = d.DepartmentID
GROUP BY prog.ProgramID, prog.ProgramName, d.DepartmentName
ORDER BY prog.ProgramName;

/*
EXPLANATION - 6 Simple Queries:

1. All Student Enrollments
   - Shows which students are enrolled in which courses
   - Displays course section and semester

2. Course Enrollment Summary
   - Lists all courses with current enrollments vs max seats
   - Helps identify full or under-enrolled courses

3. Student Assessment Results
   - Shows each student's marks, grades, and pass/fail status
   - Joins assessment results across all courses

4. Faculty Teaching Assignments
   - Lists faculty members with courses they teach
   - Includes department and designation

5. Students Who Passed by Course
   - Counts how many students passed each course
   - Groups by course offering

6. Student Count by Program
   - Shows total students enrolled in each program
   - Displays department name
*/

