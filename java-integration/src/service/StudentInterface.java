package service;

/**
 * Interface for Student Operations
 * Defines contract for CRUD and advanced operations
 */
public interface StudentInterface {

    // return personID of inserted record, -1 if failed
    int insertStudent(String firstName, String lastName, String email, String dob, int age);

    // return true if successful, false otherwise
    boolean updateStudent(int personID, String firstName, String lastName, int age);

    // return true if successful, false otherwise
    boolean deleteStudent(int personID);

    // Prints student details to console
    void viewAllStudents();

    // return true if successful, false otherwise
    boolean enrollStudent(int studentID, int offeringID);

    // Prints results to console
    void getStudentResults(int studentID);

    // return GPA value, 0 if no results
    double calculateGPA(int studentID);
}
