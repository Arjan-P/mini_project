DELIMITER //
CREATE PROCEDURE enroll_student(IN p_student_id INT, IN p_offering_id INT)
BEGIN
  INSERT IGNORE INTO ENROLLMENT (StudentID, OfferingID, EnrollmentDate, Status)
  VALUES (p_student_id, p_offering_id, CURDATE(), 'Active');
END //


CREATE PROCEDURE get_student_results(IN p_student_id INT)
BEGIN
  SELECT p.FirstName, p.LastName, s.RollNo, c.CourseTitle, a.Title, att.MarksObtained, att.Grade
  FROM STUDENT s JOIN PERSON p ON s.PersonID = p.PersonID
  JOIN ENROLLMENT e ON s.PersonID = e.StudentID
  JOIN COURSE_OFFERING co ON e.OfferingID = co.OfferingID
  JOIN COURSE c ON co.CourseID = c.CourseID
  LEFT JOIN ASSESSMENT a ON co.OfferingID = a.OfferingID
  LEFT JOIN ATTEMPTS att ON s.PersonID = att.StudentID AND a.AssessmentID = att.AssessmentID
  WHERE s.PersonID = p_student_id;
END //
DELIMITER ;
