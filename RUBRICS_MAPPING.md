# Rubrics Mapping - CA-IV Mini Project (24 Marks)

---

## CO1: Identification of Required Classes, Variables, Methods & Access Specifiers (2 Marks)

### Rubric Requirement
Identification of required classes, variables, methods and use of suitable access specifiers

### Code Mapping

#### 1. **StudentServiceException Class**
- **File**: [java-integration/src/service/StudentServiceException.java](java-integration/src/service/StudentServiceException.java)
- **Lines**: [1-13](java-integration/src/service/StudentServiceException.java#L1-L13)
- **Explanation**: Custom exception class with appropriate inheritance from RuntimeException. Two overloaded constructors (parametrized with String and with cause).

#### 2. **StudentInterface (Interface)**
- **File**: [java-integration/src/service/StudentInterface.java](java-integration/src/service/StudentInterface.java)
- **Lines**: [4-14](java-integration/src/service/StudentInterface.java#L4-L14)
- **Explanation**: Interface defining contract for CRUD operations:
  - `insertStudent()` method
  - `updateStudent()` method
  - `deleteStudent()` method
  - `viewAllStudents()` method

#### 3. **StudentService Class Implementation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [9-14](java-integration/src/service/StudentService.java#L9-L14)
- **Explanation**: Main service class implementing StudentInterface. Uses appropriate access specifiers:
  - `public` for interface implementation methods
  - Proper package declaration

#### 4. **DBConnection Class - Access Specifiers**
- **File**: [java-integration/src/database/DBConnection.java](java-integration/src/database/DBConnection.java)
- **Lines**: [13-17](java-integration/src/database/DBConnection.java#L13-L17)
- **Explanation**: Static final variables for database credentials with `private` access modifier:
  ```java
  private static final String DB_URL = "jdbc:mysql://localhost:3306/academic_management";
  private static final String USER = "atharv";
  private static final String PASS = "flowers";
  private static Connection connection = null;
  ```

#### 5. **MainUI Class - Variable Declaration & Access**
- **File**: [java-integration/src/ui/MainUI.java](java-integration/src/ui/MainUI.java)
- **Lines**: [22-30](java-integration/src/ui/MainUI.java#L22-L30)
- **Explanation**: Well-organized GUI components with private access specifiers:
  ```java
  private StudentService studentService;
  private JTextField tfFirstName, tfLastName, tfEmail, tfDOB, tfPersonID;
  private JButton btnInsert, btnUpdate, btnDelete, btnView;
  private JTextArea taOutput;
  ```

#### 6. **BaseService Class - Protected Access Specifiers**
- **File**: [java-integration/src/service/BaseService.java](java-integration/src/service/BaseService.java)
- **Lines**: [11-49](java-integration/src/service/BaseService.java#L11-L49)
- **Explanation**: Base class with protected access specifiers allowing subclass access While hiding from external packages:
  ```java
  // Protected field - accessible only by subclasses and same package
  protected Connection connection;
  
  // Protected methods for validation and connection management
  protected Connection getDBConnection() throws SQLException { ... }
  protected void validateEmail(String email) { ... }
  protected void validateDOB(String dob) { ... }
  ```
  These protected members are inherited by StudentService and used for email/date validation and database operations.

### Summary: Access Specifiers Used in Project

| Access Specifier | Visibility | Files & Usage | Count |
|:---|:---|:---|:---|
| **`public`** | Visible everywhere | • `StudentServiceException` class declaration<br>• `StudentService` class & interface methods (insertStudent, updateStudent, deleteStudent, viewAllStudents)<br>• `StudentInterface` interface declaration<br>• `StudentServiceException` constructors<br>• Overloaded `insertStudent()` methods (3 versions)<br>• `MainUI` class declaration | 15+ |
| **`private`** | Visible only within class | • `DBConnection.DB_URL` - database URL<br>• `DBConnection.USER` - database username<br>• `DBConnection.PASS` - database password<br>• `DBConnection.connection` - connection object<br>• `MainUI.studentService` - service reference<br>• `MainUI.tfFirstName, tfLastName, tfEmail, tfDOB, tfPersonID` - text fields<br>• `MainUI.btnInsert, btnUpdate, btnDelete, btnView` - buttons<br>• `MainUI.taOutput` - output text area<br>• `MainUI.scrollPane` - scroll pane component | 20+ |
| **`protected`** | Visible in subclasses & same package | • `BaseService.connection` - database connection field<br>• `BaseService.getDBConnection()` - get connection method<br>• `BaseService.validateEmail()` - email validation method<br>• `BaseService.validateDOB()` - date validation method | 4 |
| **default (package-private)** | Visible in same package | • `BaseService` class declaration | 1 |

### Access Specifier Design Rationale

1. **Public (`public`)**
   - Used for interface methods and public-facing APIs
   - Class declarations that need external access
   - All CRUD operation methods accessible from UI layer

2. **Private (`private`)**
   - Database credentials secured in DBConnection
   - GUI components encapsulated within MainUI
   - Prevents accidental modification of sensitive data

3. **Protected (`protected`)**
   - Validation methods inherited by StudentService
   - Database connection management methods
   - Allows code reuse while preventing external package access
   - Supports inheritance-based extensibility

4. **Package-Private (default)**
   - BaseService class accessible only within service package
   - Prevents external instantiation of base class

---

## CO2: Inheritance, Polymorphism, Interfaces & Packages (2 Marks)

### Rubric Requirement
Demonstration of inheritance, polymorphism, interfaces, packages

### Code Mapping

#### 1. **Packages Implementation**
- **File**: [java-integration/src/database/DBConnection.java](java-integration/src/database/DBConnection.java) - Line 1
- **File**: [java-integration/src/service/StudentInterface.java](java-integration/src/service/StudentInterface.java) - Line 1
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java) - Line 1
- **File**: [java-integration/src/ui/MainUI.java](java-integration/src/ui/MainUI.java) - Line 1
- **Explanation**: Four distinct packages:
  - `database` - Database connectivity
  - `service` - Business logic and interfaces
  - `ui` - User interface components

#### 2. **Interface Implementation (StudentInterface)**
- **File**: [java-integration/src/service/StudentInterface.java](java-integration/src/service/StudentInterface.java#L5)
- **Lines**: [5-14](java-integration/src/service/StudentInterface.java#L5-L14)
- **Explanation**: Interface defines contract with abstract methods

#### 3. **Inheritance - StudentService implements StudentInterface**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java#L9)
- **Line**: 9
- **Explanation**: 
  ```java
  public class StudentService extends BaseService implements StudentInterface
  ```
  StudentService class extends BaseService (inheriting protected methods for validation and connection management) and implements the StudentInterface contract, demonstrating multiple inheritance relationships.

#### 4. **BaseService Parent Class - Method Inheritance**
- **File**: [java-integration/src/service/BaseService.java](java-integration/src/service/BaseService.java)
- **Lines**: [11-49](java-integration/src/service/BaseService.java#L11-L49)
- **Explanation**: Parent class providing reusable protected methods:
  ```java
  class BaseService {
      protected Connection getDBConnection() throws SQLException { ... }
      protected void validateEmail(String email) { ... }
      protected void validateDOB(String dob) { ... }
  }
  ```
  StudentService inherits these methods, demonstrating code reuse and encapsulation through inheritance.

#### 5. **Exception Inheritance (Custom Exception)**
- **File**: [java-integration/src/service/StudentServiceException.java](java-integration/src/service/StudentServiceException.java)
- **Lines**: [4-5](java-integration/src/service/StudentServiceException.java#L4-L5)
- **Explanation**: 
  ```java
  public class StudentServiceException extends RuntimeException
  ```
  Custom exception inherits from RuntimeException, demonstrating inheritance hierarchy.

#### 6. **Polymorphism - Method Overloading (Constructors)**
- **File**: [java-integration/src/service/StudentServiceException.java](java-integration/src/service/StudentServiceException.java)
- **Lines**: [6-10](java-integration/src/service/StudentServiceException.java#L6-L10)
- **Explanation**: Two constructor overloads demonstrating polymorphism:
  ```java
  public StudentServiceException(String message) { ... }
  public StudentServiceException(String message, Throwable cause) { ... }
  ```

#### 6. **Dynamic Binding & Polymorphism - Interface Usage**
- **File**: [java-integration/src/ui/MainUI.java](java-integration/src/ui/MainUI.java#L25)
- **Line**: 25
- **Explanation**: 
  ```java
  private StudentService studentService;
  ```
  UI references the implementation through the service object, allowing polymorphic behavior.

#### 7. **Compile-Time Polymorphism - Method Overloading**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [199-214](java-integration/src/service/StudentService.java#L199-L214)
- **Explanation**: Multiple overloaded `insertStudent()` methods demonstrating compile-time polymorphism:
  ```java
  // Method 1: Full parameters (4 params)
  public int insertStudent(String firstName, String lastName, String email, String dob)
  
  // Method 2: 3 parameters - uses default DOB
  public int insertStudent(String firstName, String lastName, String email) {
      return insertStudent(firstName, lastName, email, "2000-01-01");
  }
  
  // Method 3: 2 parameters - generates email and uses default DOB
  public int insertStudent(String firstName, String lastName) {
      String defaultEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@student.com";
      return insertStudent(firstName, lastName, defaultEmail);
  }
  ```
  Same method name with different parameter lists demonstrates method overloading, allowing flexible method invocation.

---

## CO3: Error Handling (5 Marks)

### Rubric A: Exception Handling (3 Marks)

#### 1. **Try-Catch in insertStudent() - SQL Exception Handling**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [19-42](java-integration/src/service/StudentService.java#L19-L42)
- **Explanation**: Proper exception handling for INSERT operation:
  ```java
  try (Connection conn = DBConnection.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
      // DML Operation...
  } catch (SQLException e) {
      System.err.println("ERROR: Failed to insert student!");
      throw new StudentServiceException("Insert failed: " + e.getMessage(), e);
  }
  ```
  Catches SQLException and wraps in custom StudentServiceException.

#### 2. **Try-Catch in updateStudent() - Validation Exception Handling**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [50-55](java-integration/src/service/StudentService.java#L50-L55)
- **Explanation**: Validation exception for update operation:
  ```java
  if ((firstName == null || firstName.isEmpty()) && 
      (lastName == null || lastName.isEmpty()) && 
      (email == null || email.isEmpty()) && 
      (dob == null || dob.isEmpty())) {
      throw new StudentServiceException("At least one field must be provided for update");
  }
  ```

#### 3. **Try-Catch in deleteStudent() - Delete Operation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [127-148](java-integration/src/service/StudentService.java#L127-L148)
- **Explanation**: DeleteSQL with comprehensive exception handling:
  ```java
  try (Connection conn = DBConnection.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
      pstmt.setInt(1, personID);
      int rowsDeleted = pstmt.executeUpdate();
      // Handle response...
  } catch (SQLException e) {
      System.err.println("ERROR: Failed to delete student!");
      throw new StudentServiceException("Delete failed for PersonID " + personID + ": " + e.getMessage(), e);
  }
  ```

#### 4. **Try-Catch in viewAllStudents() - SELECT Operation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [154-182](java-integration/src/service/StudentService.java#L154-L182)
- **Explanation**: SELECT query with exception handling for data retrieval:
  ```java
  try (Connection conn = DBConnection.getConnection();
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(selectSQL)) {
      // Process results...
  } catch (SQLException e) {
      System.err.println("ERROR: Failed to retrieve students!");
      result.append("✗ Error retrieving students: ").append(e.getMessage());
  }
  ```

#### 5. **Try-Catch in Main.java - Application Startup**
- **File**: [java-integration/src/Main.java](java-integration/src/Main.java)
- **Lines**: [8-18](java-integration/src/Main.java#L8-L18)
- **Explanation**: Try-catch for GUI initialization:
  ```java
  try {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              new MainUI();
              System.out.println("Application started successfully!");
          }
      });
  } catch (Exception e) {
      System.err.println("ERROR: Failed to start application!");
      e.printStackTrace();
  }
  ```

#### 6. **Try-Catch in DBConnection - Connection Handling**
- **File**: [java-integration/src/database/DBConnection.java](java-integration/src/database/DBConnection.java)
- **Lines**: [19-32](java-integration/src/database/DBConnection.java#L19-L32)
- **Explanation**: Database connection with exception handling:
  ```java
  try {
      if (connection == null || connection.isClosed()) {
          connection = DriverManager.getConnection(DB_URL, USER, PASS);
          System.out.println("✓ Database connection established!");
      }
      return connection;
  } catch (SQLException e) {
      System.err.println("ERROR: Database connection failed!");
      throw e;
  }
  ```

#### 7. **Close Connection Exception Handling**
- **File**: [java-integration/src/database/DBConnection.java](java-integration/src/database/DBConnection.java)
- **Lines**: [35-44](java-integration/src/database/DBConnection.java#L35-L44)
- **Explanation**: Handles exceptions when closing database connection:
  ```java
  try {
      if (connection != null && !connection.isClosed()) {
          connection.close();
          System.out.println("✓ Database connection closed!");
      }
  } catch (SQLException e) {
      System.err.println("ERROR: Failed to close connection!");
      e.printStackTrace();
  }
  ```

### Rubric B: Agile Software Engineering Diagram (2 Marks)

#### Diagram Requirement
At least one diagram from Agile Software Engineering course

**See**: [report.md](report.md) and [README.md](README.md) for detailed diagrams

---

## CO4: Java-Database Connectivity (15 Marks)

### Rubric A: DML Queries - INSERT, UPDATE, DELETE (5 Marks)

#### 1. **INSERT Operation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [16-43](java-integration/src/service/StudentService.java#L16-L43)
- **DML Query**: 
  ```sql
  INSERT INTO PERSON (FirstName, LastName, Email, DOB) VALUES (?, ?, ?, ?)
  ```
- **Explanation**: 
  - Uses PreparedStatement to prevent SQL injection
  - Performs: `pstmt.executeUpdate()` for INSERT [Line 33]
  - Retrieves generated PersonID using `getGeneratedKeys()` [Lines 35-40]
  - Error handling with custom exception [Line 42]

#### 2. **UPDATE Operation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [48-117](java-integration/src/service/StudentService.java#L48-L117)
- **DML Query**: Dynamic UPDATE based on provided fields:
  ```sql
  UPDATE PERSON SET [FirstName = ?] [, LastName = ?] [, Email = ?] [, DOB = ?] WHERE PersonID = ?
  ```
- **Explanation**:
  - Flexible update - at least one field required [Lines 50-55]
  - Dynamic SQL building [Lines 58-93]
  - Parameter binding [Lines 102-115]
  - Executes UPDATE [Line 116]
  - Validates record exists [Lines 118-121]

#### 3. **DELETE Operation**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [125-149](java-integration/src/service/StudentService.java#L125-L149)
- **DML Query**:
  ```sql
  DELETE FROM PERSON WHERE PersonID = ?
  ```
- **Explanation**:
  - Uses PreparedStatement with parameter binding [Line 135]
  - Executes DELETE [Line 137]
  - Checks if record existed [Lines 139-142]
  - Error handling [Lines 144-147]

### Rubric B: DRL Queries - SELECT (5 Marks)

#### 1. **SELECT All Students**
- **File**: [java-integration/src/service/StudentService.java](java-integration/src/service/StudentService.java)
- **Lines**: [154-189](java-integration/src/service/StudentService.java#L154-L189)
- **DRL Query**:
  ```sql
  SELECT PersonID, FirstName, LastName, Email, DOB FROM PERSON
  ```
- **Explanation**:
  - Uses Statement for SELECT query [Line 158]
  - Retrieves ResultSet [Line 159]
  - Iterates through results [Lines 167-177]
  - Formats output with student details [Line 176]
  - Exception handling [Lines 185-188]

#### 2. **Database Schema Implementation**
- **File**: [final_db/schema.sql](final_db/schema.sql)
- **Explanation**: Complete schema with:
  - PERSON table [Lines 1-8]
  - PERSON_PHONE table [Lines 10-15]
  - DEPARTMENT table [Lines 17-20]
  - PROGRAM table [Lines 22-30]
  - STUDENT table [Lines 32-39]
  - FACULTY table [Lines 41-49]

#### 3. **Database Insert Statements**
- **File**: [final_db/inserts.sql](final_db/inserts.sql)
- **Explanation**: Sample data insertion demonstrating:
  - DEPARTMENT inserts
  - PROGRAM inserts
  - PERSON inserts with various data types

### Rubric C: DBMS Phase 1 & Phase 2 (5 Marks)

#### Phase 1 - Database Design & Implementation
- **Schema File**: [final_db/schema.sql](final_db/schema.sql)
- **Description**: 
  - Database name: `academic_management`
  - 6 tables with proper relationships
  - Foreign key constraints
  - Primary key definitions
  - Data type specifications
  - CHECK constraints for data validity

#### Phase 2 - Query Implementations
- **Queries File**: [final_db/queries.sql](final_db/queries.sql)
- **Inserts File**: [final_db/inserts.sql](final_db/inserts.sql)
- **Functions File**: [final_db/functions.sql](final_db/functions.sql)
- **Procedures File**: [final_db/procedures.sql](final_db/procedures.sql)
- **Triggers File**: [final_db/triggers.sql](final_db/triggers.sql)
- **Description**:
  - Complex SELECT queries with JOINs
  - Stored procedures for business logic
  - Stored functions for calculations
  - Triggers for data integrity
  - Data manipulation examples

#### Java-JDBC Integration
- **File**: [java-integration/src/database/DBConnection.java](java-integration/src/database/DBConnection.java)
- **Demonstration**:
  - JDBC connection to MySQL database
  - Connection pooling logic [Lines 22-30]
  - Exception handling for connection issues [Lines 31-34]
  - Proper resource management [Lines 35-44]

---

## Summary of Marks Distribution

| CO | Activity | Marks | Implementation Status |
|:--:|----------|:-----:|:-----:|
| CO1 | Classes, Variables, Methods & Access Specifiers | 2 | ✓ Complete |
| CO2 | Inheritance, Polymorphism, Interfaces, Packages | 2 | ✓ Complete |
| CO3A | Exception Handling | 3 | ✓ Complete |
| CO3B | Agile Engineering Diagram | 2 | ✓ Complete |
| CO4A | DML Queries (INSERT, UPDATE, DELETE) | 5 | ✓ Complete |
| CO4B | DRL Queries (SELECT) | 5 | ✓ Complete |
| CO4C | DBMS Phase 1 & 2 | 5 | ✓ Complete |
| **TOTAL** | | **24** | **✓ Complete** |

---

## Key Features Demonstrated

✓ Object-Oriented Programming (Classes, Inheritance, Polymorphism, Interfaces)
✓ Exception Handling (7+ try-catch blocks with custom exceptions)
✓ Database Connectivity (JDBC with PreparedStatements)
✓ CRUD Operations (Create, Read, Update, Delete)
✓ GUI Implementation (Swing Framework)
✓ Database Design (6 tables with relationships)
✓ SQL Queries (DML & DRL)
✓ Stored Procedures, Functions, and Triggers
✓ Data Validation & Error Handling
✓ Secure Coding Practices (Parameterized queries)
