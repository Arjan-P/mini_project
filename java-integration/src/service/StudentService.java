package service;

import database.DBConnection;
import java.sql.*;
import java.time.LocalDate;

/*
 Student Service Implementation
 Implements StudentInterface with JDBC operations
 Handles INSERT, UPDATE, DELETE, SELECT, Stored Procedures, and Functions
*/
public class StudentService implements StudentInterface {

    @Override
    public int insertStudent(String firstName, String lastName, String email, String dob) {
        validateEmail(email);
        validateDOB(dob);

        String insertSQL = "INSERT INTO PERSON (FirstName, LastName, Email, DOB) VALUES (?, ?, ?, ?)";
        int personID = -1;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(dob));

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
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

    @Override
    public boolean updateStudent(int personID, String firstName, String lastName, String email, String dob) {
        if ((firstName == null || firstName.isEmpty()) &&
                (lastName == null || lastName.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (dob == null || dob.isEmpty())) {
            throw new StudentServiceException("At least one field must be provided for update");
        }

        if (email != null && !email.isEmpty()) {
            validateEmail(email);
        }
        if (dob != null && !dob.isEmpty()) {
            validateDOB(dob);
        }

        StringBuilder updateSQL = new StringBuilder("UPDATE PERSON SET ");
        int paramCount = 0;

        if (firstName != null && !firstName.isEmpty()) {
            updateSQL.append("FirstName = ?");
            paramCount++;
        }

        if (lastName != null && !lastName.isEmpty()) {
            if (paramCount > 0)
                updateSQL.append(", ");
            updateSQL.append("LastName = ?");
            paramCount++;
        }

        if (email != null && !email.isEmpty()) {
            if (paramCount > 0)
                updateSQL.append(", ");
            updateSQL.append("Email = ?");
            paramCount++;
        }

        if (dob != null && !dob.isEmpty()) {
            if (paramCount > 0)
                updateSQL.append(", ");
            updateSQL.append("DOB = ?");
            paramCount++;
        }

        updateSQL.append(" WHERE PersonID = ?");

        boolean success = false;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(updateSQL.toString())) {

            int paramIndex = 1;
            if (firstName != null && !firstName.isEmpty()) {
                pstmt.setString(paramIndex++, firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                pstmt.setString(paramIndex++, lastName);
            }
            if (email != null && !email.isEmpty()) {
                pstmt.setString(paramIndex++, email);
            }
            if (dob != null && !dob.isEmpty()) {
                pstmt.setDate(paramIndex++, Date.valueOf(dob));
            }
            pstmt.setInt(paramIndex, personID);

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
    public String viewAllStudents() {
        String selectSQL = "SELECT PersonID, FirstName, LastName, Email, DOB FROM PERSON";
        StringBuilder result = new StringBuilder();

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectSQL)) {

            result.append("\n").append("=".repeat(80)).append("\n");
            result.append("STUDENT LIST\n");
            result.append("=".repeat(80)).append("\n");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                int id = rs.getInt("PersonID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String dob = rs.getString("DOB");

                result.append(String.format("ID: %-4d | Name: %-20s | Email: %-25s | DOB: %s%n",
                        id, firstName + " " + lastName, email, dob));
            }

            if (!hasRecords) {
                result.append("✗ No students found!\n");
            }
            result.append("=".repeat(80)).append("\n");

        } catch (SQLException e) {
            System.err.println("ERROR: Failed to retrieve students!");
            result.append("✗ Error retrieving students: ").append(e.getMessage());
        }

        return result.toString();
    }

    /**
     * VALIDATION: Check email format
     */
    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new StudentServiceException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new StudentServiceException("Invalid email format. Expected: example@domain.com");
        }
    }

    /**
     * VALIDATION: Check DOB format and range
     */
    private void validateDOB(String dob) {
        if (dob == null || dob.isEmpty()) {
            throw new StudentServiceException("Date of Birth cannot be empty");
        }
        try {
            LocalDate birthDate = LocalDate.parse(dob);
            LocalDate today = LocalDate.now();

            if (birthDate.isAfter(today)) {
                throw new StudentServiceException("Date of Birth cannot be in the future");
            }

            int age = today.getYear() - birthDate.getYear();
            if (today.getMonthValue() < birthDate.getMonthValue() ||
                    (today.getMonthValue() == birthDate.getMonthValue()
                            && today.getDayOfMonth() < birthDate.getDayOfMonth())) {
                age--;
            }

            if (age < 15 || age > 70) {
                throw new StudentServiceException("Age must be between 15 and 70 years");
            }
        } catch (java.time.format.DateTimeParseException e) {
            throw new StudentServiceException("Invalid date format. Expected: YYYY-MM-DD");
        }
    }
}
