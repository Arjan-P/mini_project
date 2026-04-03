package service;

/**
 * Interface for Student Operations
 * Defines contract for CRUD and advanced operations
 */
public interface StudentInterface {

    /**
     * Insert new student person record
     * 
     * @param firstName student's first name
     * @param lastName  student's last name
     * @param email     student's email
     * @param dob       date of birth (YYYY-MM-DD format)
     * @param age       student's age
     * @return personID of inserted record, -1 if failed
     */
    int insertStudent(String firstName, String lastName, String email, String dob, int age);

    /**
     * Update student person details
     * 
     * @param personID  student's person ID
     * @param firstName new first name
     * @param lastName  new last name
     * @param age       new age
     * @return true if successful, false otherwise
     */
    boolean updateStudent(int personID, String firstName, String lastName, int age);

    /**
     * Delete student and related records
     * 
     * @param personID student's person ID
     * @return true if successful, false otherwise
     */
    boolean deleteStudent(int personID);

    /**
     * View all students
     * Prints student details to console
     */
    void viewAllStudents();

    /**
     * Enroll student in a course offering
     * 
     * @param studentID  student's person ID
     * @param offeringID course offering ID
     * @return true if successful, false otherwise
     */
    boolean enrollStudent(int studentID, int offeringID);

    /**
     * Get student results
     * 
     * @param studentID student's person ID
     *                  Prints results to console
     */
    void getStudentResults(int studentID);

    /**
     * Calculate GPA for student
     * 
     * @param studentID student's person ID
     * @return GPA value, 0 if no results
     */
    double calculateGPA(int studentID);
}
