package service;

import database.DBConnection;
import java.sql.*;

/**
 * Student Service Implementation
 * Implements StudentInterface with JDBC operations
 * Handles INSERT, UPDATE, DELETE, SELECT, Stored Procedures, and Functions
 */
public class StudentService implements StudentInterface {

    /**
     * INSERT: Add new student to PERSON table
     */
    @Override
    public int insertStudent(String firstName, String lastName, String email, String dob, int age) {
        String insertSQL = "INSERT INTO PERSON (FirstName, LastName, Email, DOB, Age) VALUES (?, ?, ?, ?, ?)";
        int personID = -1;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(dob));
            pstmt.setInt(5, age);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Get generated PersonID
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        personID = rs.getInt(1);
                        System.out.println("✓ Student inserted successfully! PersonID: " + personID);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to insert student!");
            throw new StudentServiceException("Insert failed: " + e.getMessage(), e);
        }

        return personID;
    }

    /**
     * UPDATE: Modify student details
     */
    @Override
    public boolean updateStudent(int personID, String firstName, String lastName, int age) {
        String updateSQL = "UPDATE PERSON SET FirstName = ?, LastName = ?, Age = ? WHERE PersonID = ?";
        boolean success = false;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);
            pstmt.setInt(4, personID);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✓ Student updated successfully!");
                success = true;
            } else {
                System.out.println("✗ Student not found!");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to update student!");
            throw new StudentServiceException("Update failed for PersonID " + personID + ": " + e.getMessage(), e);
        }

        return success;
    }

    /**
     * DELETE: Remove student record
     */
    @Override
    public boolean deleteStudent(int personID) {
        String deleteSQL = "DELETE FROM PERSON WHERE PersonID = ?";
        boolean success = false;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

            pstmt.setInt(1, personID);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✓ Student deleted successfully!");
                success = true;
            } else {
                System.out.println("✗ Student not found!");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to delete student!");
            throw new StudentServiceException("Delete failed for PersonID " + personID + ": " + e.getMessage(), e);
        }

        return success;
    }

    /**
     * SELECT: View all students
     */
    @Override
    public void viewAllStudents() {
        String selectSQL = "SELECT PersonID, FirstName, LastName, Email, Age FROM PERSON LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\n" + "=".repeat(70));
            System.out.println("STUDENT LIST");
            System.out.println("=".repeat(70));

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                int id = rs.getInt("PersonID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                int age = rs.getInt("Age");

                System.out.printf("ID: %-4d | Name: %-20s | Email: %-30s | Age: %d%n",
                        id, firstName + " " + lastName, email, age);
            }

            if (!hasRecords) {
                System.out.println("✗ No students found!");
            }
            System.out.println("=".repeat(70) + "\n");

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to retrieve students!");
            e.printStackTrace();
        }
    }

    /**
     * STORED PROCEDURE: Enroll student in course offering
     */
    @Override
    public boolean enrollStudent(int studentID, int offeringID) {
        String procedureCall = "{CALL enroll_student(?, ?)}";
        boolean success = false;

        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(procedureCall)) {

            cstmt.setInt(1, studentID);
            cstmt.setInt(2, offeringID);

            cstmt.execute();
            System.out.println("✓ Student enrolled successfully!");
            success = true;

        } catch (SQLException e) {
            System.err.println("ERROR: Enrollment failed!");
            e.printStackTrace();
        }
        return success;
    }

    /**
     * STORED PROCEDURE: Get student results
     */
    @Override
    public void getStudentResults(int studentID) {
        String procedureCall = "{CALL get_student_results(?)}";

        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(procedureCall)) {

            cstmt.setInt(1, studentID);

            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.println("\n" + "=".repeat(100));
                System.out.println("STUDENT RESULTS");
                System.out.println("=".repeat(100));

                boolean hasRecords = false;
                while (rs.next()) {
                    hasRecords = true;
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String rollNo = rs.getString("RollNo");
                    String courseTitle = rs.getString("CourseTitle");
                    String assessmentTitle = rs.getString("Title");
                    int marks = rs.getInt("MarksObtained");
                    String grade = rs.getString("Grade");

                    System.out.printf(
                            "Student: %s %s (Roll: %s) | Course: %s | Assessment: %s | Marks: %d | Grade: %s%n",
                            firstName, lastName, rollNo, courseTitle, assessmentTitle, marks, grade);
                }

                if (!hasRecords) {
                    System.out.println("✗ No results found for student!");
                }
                System.out.println("=".repeat(100) + "\n");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to retrieve results!");
            e.printStackTrace();
        }
    }

    /**
     * FUNCTION: Calculate student GPA
     */
    @Override
    public double calculateGPA(int studentID) {
        String functionCall = "{? = CALL calculate_gpa(?)}";
        double gpa = 0.0;

        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(functionCall)) {

            cstmt.registerOutParameter(1, Types.DECIMAL);
            cstmt.setInt(2, studentID);

            cstmt.execute();
            gpa = cstmt.getDouble(1);

            System.out.println("✓ GPA calculated successfully! GPA: " + gpa);

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to calculate GPA!");
            e.printStackTrace();
        }

        return gpa;
    }
}
