package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import service.StudentService;
import service.StudentServiceException;

/**
 * Main User Interface
 * Simple Swing GUI for DBMS operations
 * Provides buttons for INSERT, UPDATE, DELETE, SELECT, and advanced operations
 */
public class MainUI extends JFrame {

    private StudentService studentService;

    // GUI Components
    private JTextField tfFirstName, tfLastName, tfEmail, tfDOB, tfAge, tfPersonID, tfOfferingID;
    private JButton btnInsert, btnUpdate, btnDelete, btnView, btnEnroll, btnResults, btnGPA;
    private JTextArea taOutput;
    private JScrollPane scrollPane;

    public MainUI() {
        // Initialize service
        studentService = new StudentService();

        // Frame setup
        setTitle("Academic Management System - DBMS Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel - Input fields (fixed height)
        JPanel inputPanel = createInputPanel();
        inputPanel.setPreferredSize(new Dimension(0, 120));
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Center panel - Output area (takes most space)
        JPanel outputPanel = createOutputPanel();
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        // Bottom panel - Buttons (single row)
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Create input fields panel
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        panel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Input Fields");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        panel.setBorder(border);

        // Labels and TextFields
        JLabel lbl1 = new JLabel("First Name:");
        lbl1.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl1);
        tfFirstName = new JTextField(20);
        tfFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
        tfFirstName.setPreferredSize(new Dimension(220, 35));
        panel.add(tfFirstName);

        JLabel lbl2 = new JLabel("Last Name:");
        lbl2.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl2);
        tfLastName = new JTextField(20);
        tfLastName.setFont(new Font("Arial", Font.PLAIN, 16));
        tfLastName.setPreferredSize(new Dimension(220, 35));
        panel.add(tfLastName);

        JLabel lbl3 = new JLabel("Email:");
        lbl3.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl3);
        tfEmail = new JTextField(28);
        tfEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        tfEmail.setPreferredSize(new Dimension(300, 35));
        panel.add(tfEmail);

        JLabel lbl4 = new JLabel("DOB (YYYY-MM-DD):");
        lbl4.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl4);
        tfDOB = new JTextField(18);
        tfDOB.setFont(new Font("Arial", Font.PLAIN, 16));
        tfDOB.setPreferredSize(new Dimension(200, 35));
        panel.add(tfDOB);

        JLabel lbl5 = new JLabel("Age:");
        lbl5.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl5);
        tfAge = new JTextField(10);
        tfAge.setFont(new Font("Arial", Font.PLAIN, 16));
        tfAge.setPreferredSize(new Dimension(120, 35));
        panel.add(tfAge);

        JLabel lbl6 = new JLabel("PersonID:");
        lbl6.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl6);
        tfPersonID = new JTextField(12);
        tfPersonID.setFont(new Font("Arial", Font.PLAIN, 16));
        tfPersonID.setPreferredSize(new Dimension(140, 35));
        panel.add(tfPersonID);

        JLabel lbl7 = new JLabel("OfferingID:");
        lbl7.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl7);
        tfOfferingID = new JTextField(12);
        tfOfferingID.setFont(new Font("Arial", Font.PLAIN, 16));
        tfOfferingID.setPreferredSize(new Dimension(140, 35));
        panel.add(tfOfferingID);

        return panel;
    }

    /**
     * Create buttons panel with all operations
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));
        TitledBorder border = BorderFactory.createTitledBorder("Operations");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        panel.setBorder(border);
        panel.setBackground(Color.WHITE);

        // INSERT Button
        btnInsert = createButton("INSERT", new Color(76, 175, 80));
        btnInsert.addActionListener(e -> handleInsert());
        panel.add(btnInsert);

        // UPDATE Button
        btnUpdate = createButton("UPDATE", new Color(33, 150, 243));
        btnUpdate.addActionListener(e -> handleUpdate());
        panel.add(btnUpdate);

        // DELETE Button
        btnDelete = createButton("DELETE", new Color(244, 67, 54));
        btnDelete.addActionListener(e -> handleDelete());
        panel.add(btnDelete);

        // VIEW Button
        btnView = createButton("VIEW ALL", new Color(255, 152, 0));
        btnView.addActionListener(e -> handleViewAll());
        panel.add(btnView);

        // ENROLL Button
        btnEnroll = createButton("ENROLL", new Color(156, 39, 176));
        btnEnroll.addActionListener(e -> handleEnroll());
        panel.add(btnEnroll);

        // GET RESULTS Button
        btnResults = createButton("GET RESULTS", new Color(0, 150, 136));
        btnResults.addActionListener(e -> handleGetResults());
        panel.add(btnResults);

        // CALCULATE GPA Button
        btnGPA = createButton("CALC GPA", new Color(233, 30, 99));
        btnGPA.addActionListener(e -> handleCalculateGPA());
        panel.add(btnGPA);

        // CLEAR Button
        JButton btnClear = createButton("CLEAR", new Color(158, 158, 158));
        btnClear.addActionListener(e -> clearFields());
        panel.add(btnClear);

        return panel;
    }

    /**
     * Create output text area
     */
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        TitledBorder border = BorderFactory.createTitledBorder("Output");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        panel.setBorder(border);
        panel.setBackground(Color.WHITE);

        taOutput = new JTextArea();
        taOutput.setEditable(false);
        taOutput.setFont(new Font("Monospaced", Font.PLAIN, 18));
        taOutput.setBackground(new Color(30, 30, 30));
        taOutput.setForeground(new Color(0, 255, 0));
        taOutput.setMargin(new Insets(15, 15, 15, 15));
        taOutput.setLineWrap(true);
        taOutput.setWrapStyleWord(true);

        scrollPane = new JScrollPane(taOutput);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create styled button
     */
    private JButton createButton(String label, Color color) {
        JButton button = new JButton(label);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 50));
        return button;
    }

    /**
     * INSERT operation handler
     */
    private void handleInsert() {
        try {
            String firstName = tfFirstName.getText().trim();
            String lastName = tfLastName.getText().trim();
            String email = tfEmail.getText().trim();
            String dob = tfDOB.getText().trim();
            int age = Integer.parseInt(tfAge.getText().trim());

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty()) {
                appendOutput("✗ Please fill all fields!");
                return;
            }

            int personID = studentService.insertStudent(firstName, lastName, email, dob, age);
            if (personID > 0) {
                appendOutput("✓ Insert successful! PersonID: " + personID);
                clearFields();
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid age format!");
        } catch (StudentServiceException e) {
            appendOutput("✗ Error: " + e.getMessage());
        }
    }

    /**
     * UPDATE operation handler
     */
    private void handleUpdate() {
        try {
            int personID = Integer.parseInt(tfPersonID.getText().trim());
            String firstName = tfFirstName.getText().trim();
            String lastName = tfLastName.getText().trim();
            int age = Integer.parseInt(tfAge.getText().trim());

            if (firstName.isEmpty() || lastName.isEmpty()) {
                appendOutput("✗ Please fill name and age fields!");
                return;
            }

            if (studentService.updateStudent(personID, firstName, lastName, age)) {
                appendOutput("✓ Update successful!");
                clearFields();
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid PersonID or Age format!");
        } catch (StudentServiceException e) {
            appendOutput("✗ Error: " + e.getMessage());
        }
    }

    /**
     * DELETE operation handler
     */
    private void handleDelete() {
        try {
            int personID = Integer.parseInt(tfPersonID.getText().trim());

            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete StudentID " + personID + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                if (studentService.deleteStudent(personID)) {
                    appendOutput("✓ Delete successful!");
                    clearFields();
                }
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid PersonID format!");
        } catch (StudentServiceException e) {
            appendOutput("✗ Error: " + e.getMessage());
        }
    }

    /**
     * VIEW ALL operation handler
     */
    private void handleViewAll() {
        appendOutput("\n--- Retrieving all students ---");
        studentService.viewAllStudents();
        appendOutput("View operation completed. Check console for details.");
    }

    /**
     * ENROLL operation handler
     */
    private void handleEnroll() {
        try {
            int studentID = Integer.parseInt(tfPersonID.getText().trim());
            int offeringID = Integer.parseInt(tfOfferingID.getText().trim());

            if (studentService.enrollStudent(studentID, offeringID)) {
                appendOutput("✓ Enrollment successful!");
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid StudentID or OfferingID format!");
        }
    }

    /**
     * GET RESULTS operation handler
     */
    private void handleGetResults() {
        try {
            int studentID = Integer.parseInt(tfPersonID.getText().trim());
            appendOutput("\n--- Retrieving results for StudentID: " + studentID + " ---");
            studentService.getStudentResults(studentID);
            appendOutput("Results operation completed. Check console for details.");

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid StudentID format!");
        }
    }

    /**
     * CALCULATE GPA operation handler
     */
    private void handleCalculateGPA() {
        try {
            int studentID = Integer.parseInt(tfPersonID.getText().trim());
            double gpa = studentService.calculateGPA(studentID);
            appendOutput("✓ GPA for StudentID " + studentID + ": " + gpa);

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid StudentID format!");
        }
    }

    /**
     * Clear all input fields
     */
    private void clearFields() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfDOB.setText("");
        tfAge.setText("");
        tfPersonID.setText("");
        tfOfferingID.setText("");
    }

    /**
     * Append text to output area
     */
    private void appendOutput(String text) {
        taOutput.append(text + "\n");
        taOutput.setCaretPosition(taOutput.getDocument().getLength());
    }
}
