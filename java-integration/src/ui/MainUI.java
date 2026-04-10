package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import service.StudentService;
import service.StudentServiceException;

/*
 Main User Interface
 Simple Swing GUI for DBMS operations
 Provides buttons for INSERT, UPDATE, DELETE, SELECT, and advanced operations
 */
public class MainUI extends JFrame {

    private StudentService studentService;

    // GUI Components
    private JTextField tfFirstName, tfLastName, tfEmail, tfDOB, tfPersonID;
    private JButton btnInsert, btnUpdate, btnDelete, btnView;
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
        inputPanel.setPreferredSize(new Dimension(0, 180));
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Center panel - Output area (takes most space)
        JPanel outputPanel = createOutputPanel();
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        // Bottom panel - Buttons (single row)
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 100));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Create input fields panel
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
        panel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Input Fields");
        border.setTitleFont(new Font("Arial", Font.BOLD, 20));
        panel.setBorder(BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // First Name
        JLabel lbl1 = new JLabel("First Name:");
        lbl1.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(lbl1, gbc);
        tfFirstName = new JTextField(20);
        tfFirstName.setFont(new Font("Arial", Font.PLAIN, 25));
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        panel.add(tfFirstName, gbc);

        // Last Name
        JLabel lbl2 = new JLabel("Last Name:");
        lbl2.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(lbl2, gbc);
        tfLastName = new JTextField(20);
        tfLastName.setFont(new Font("Arial", Font.PLAIN, 25));
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        panel.add(tfLastName, gbc);

        // Email
        JLabel lbl3 = new JLabel("Email:");
        lbl3.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(lbl3, gbc);
        tfEmail = new JTextField(25);
        tfEmail.setFont(new Font("Arial", Font.PLAIN, 25));
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        panel.add(tfEmail, gbc);

        // DOB
        JLabel lbl4 = new JLabel("DOB (YYYY-MM-DD):");
        lbl4.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(lbl4, gbc);
        tfDOB = new JTextField(15);
        tfDOB.setFont(new Font("Arial", Font.PLAIN, 25));
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        panel.add(tfDOB, gbc);

        // PersonID
        JLabel lbl5 = new JLabel("PersonID:");
        lbl5.setFont(new Font("Arial", Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(lbl5, gbc);
        tfPersonID = new JTextField(10);
        tfPersonID.setFont(new Font("Arial", Font.PLAIN, 25));
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        panel.add(tfPersonID, gbc);

        return panel;
    }

    /**
     * Create buttons panel with all operations
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5, 15, 0));
        TitledBorder border = BorderFactory.createTitledBorder("Operations");
        border.setTitleFont(new Font("Arial", Font.BOLD, 18));
        panel.setBorder(BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
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
        border.setTitleFont(new Font("Arial", Font.BOLD, 18));
        panel.setBorder(BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.setBackground(Color.WHITE);

        taOutput = new JTextArea();
        taOutput.setEditable(false);
        taOutput.setFont(new Font("Monospaced", Font.PLAIN, 30));
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
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 80));
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

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty()) {
                appendOutput("✗ Please fill all fields!");
                return;
            }

            int personID = studentService.insertStudent(firstName, lastName, email, dob);
            if (personID > 0) {
                appendOutput("✓ Insert successful! PersonID: " + personID);
                clearFields();
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid input format!");
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
            String email = tfEmail.getText().trim();
            String dob = tfDOB.getText().trim();

            if (firstName.isEmpty() && lastName.isEmpty() && email.isEmpty() && dob.isEmpty()) {
                appendOutput("✗ Please enter at least one field to update!");
                return;
            }

            if (studentService.updateStudent(personID, firstName, lastName, email, dob)) {
                appendOutput("✓ Update successful!");
                clearFields();
            }

        } catch (NumberFormatException e) {
            appendOutput("✗ Invalid PersonID format!");
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
        String result = studentService.viewAllStudents();
        appendOutput(result);
    }

    /**
     * Clear all input fields
     */
    private void clearFields() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfDOB.setText("");
        tfPersonID.setText("");
    }

    /**
     * Append text to output area
     */
    private void appendOutput(String text) {
        taOutput.append(text + "\n");
        taOutput.setCaretPosition(taOutput.getDocument().getLength());
    }
}
