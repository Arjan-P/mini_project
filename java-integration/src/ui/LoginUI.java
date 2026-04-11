package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/*
 * Simple Login Page for Academic Management System
 * Provides username and password authentication
 */
public class LoginUI extends JFrame {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private JLabel lblMessage;

    // Simple hardcoded credentials for demo (can be extended to database later)
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password";

    // Callback for successful login
    private Runnable onLoginSuccess;

    public LoginUI(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
        initializeUI();
    }

    private void initializeUI() {
        // Frame setup
        setTitle("Academic Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel titleLabel = new JLabel("Academic Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(245, 245, 245));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        loginPanel.add(lblUsername, gbc);

        // Username field
        tfUsername = new JTextField(20);
        tfUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        tfUsername.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        loginPanel.add(tfUsername, gbc);

        // Password label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        loginPanel.add(lblPassword, gbc);

        // Password field
        pfPassword = new JPasswordField(20);
        pfPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        pfPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        loginPanel.add(pfPassword, gbc);

        // Message label
        lblMessage = new JLabel(" ");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMessage.setForeground(new Color(192, 57, 43));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(lblMessage, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(100, 40));
        btnLogin.addActionListener(e -> handleLogin());

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBackground(new Color(149, 165, 166));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        btnExit.setPreferredSize(new Dimension(100, 40));
        btnExit.addActionListener(e -> System.exit(0));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(buttonPanel, gbc);

        // Add panels to main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Allow Enter key to login
        pfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        // Focus on username field
        tfUsername.requestFocus();

        setVisible(true);
    }

    private void handleLogin() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        // Simple authentication (can be extended to database)
        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("Please enter both username and password!");
            return;
        }

        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            // Login successful
            lblMessage.setText("");
            dispose(); // Close login window

            // Execute callback to launch main UI
            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        } else {
            // Login failed
            lblMessage.setText("Invalid username or password!");
            pfPassword.setText("");
            tfUsername.requestFocus();
        }
    }
}
