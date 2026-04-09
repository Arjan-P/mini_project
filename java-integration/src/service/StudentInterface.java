package service;

// Interface for Student Operations - Defines contract for CRUD operations

public interface StudentInterface {

    // return personID of inserted record, -1 if failed
    int insertStudent(String firstName, String lastName, String email, String dob);

    // return true if successful, false otherwise (at least one field required)
    boolean updateStudent(int personID, String firstName, String lastName, String email, String dob);

    // return true if successful, false otherwise
    boolean deleteStudent(int personID);

    // Returns formatted student details as string
    String viewAllStudents();
}
