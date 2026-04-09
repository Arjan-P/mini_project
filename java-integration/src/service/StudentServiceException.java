package service;

/**
 * Custom Exception for Student Service Operations
 * Wraps database and validation errors
 */
public class StudentServiceException extends RuntimeException {

    public StudentServiceException(String message) {
        super(message);
    }

    public StudentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
