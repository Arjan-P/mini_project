package service;

import database.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

// Base Service Class with Protected Methods
// Protected methods can be accessed by subclasses and same package
class BaseService {

    // Protected field - accessible by subclasses
    protected Connection connection;

    // Protected method for getting database connection
    protected Connection getDBConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Protected method for validating email
    protected void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new StudentServiceException("Email cannot be empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new StudentServiceException("Invalid email format. Expected: example@domain.com");
        }
    }

    // Protected method for validating Date of Birth
    protected void validateDOB(String dob) {
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

    // Protected method for closing connection
    protected void closeDBConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Failed to close connection!");
            e.printStackTrace();
        }
    }
}
