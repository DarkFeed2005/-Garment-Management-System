import os

# Set target directory to current working directory
project_root = "."

# Directory structure definition
directories = [
    "src/main/java/com/garment/config",
    "src/main/java/com/garment/controller",
    "src/main/java/com/garment/dao",
    "src/main/java/com/garment/model",
    "src/main/java/com/garment/service",
    "src/main/java/com/garment/ui/panels",
    "src/main/java/com/garment/util",
    "src/main/resources"
]

# Create directories
for directory in directories:
    path = os.path.join(project_root, directory)
    os.makedirs(path, exist_ok=True)

# File Contents Definition
files = {
    "pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.garment</groupId>
    <artifactId>garment-management-system</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.3.0</version>
        </dependency>
        <dependency>
            <groupId>com.formdev</groupId>
            <artifactId>flatlaf</artifactId>
            <version>3.4</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.garment.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
""",

    "src/main/java/com/garment/Main.java": """package com.garment;

import com.formdev.flatlaf.FlatDarkLaf;
import com.garment.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}
""",

    "src/main/java/com/garment/config/DatabaseConnection.java": """package com.garment.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/garment_management";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
""",

    "src/main/java/com/garment/dao/UserDAO.java": """package com.garment.dao;

import com.garment.config.DatabaseConnection;
import com.garment.model.User;

import java.sql.*;

public class UserDAO {
    public User authenticate(String username, String password) {
        String query = "SELECT user_id, username, first_name, last_name, user_type, account_status " +
                       "FROM users WHERE username = ? AND password_hash = SHA2(?, 256)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (!"ACTIVE".equals(rs.getString("account_status"))) {
                    return null;
                }
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
""",

    "src/main/java/com/garment/model/User.java": """package com.garment.model;

public class User {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String userType;

    public User(int userId, String username, String firstName, String lastName, String userType) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getUserType() { return userType; }
}
""",

    "src/main/java/com/garment/ui/LoginFrame.java": """package com.garment.ui;

import com.garment.dao.UserDAO;
import com.garment.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UserDAO userDAO = new UserDAO();

    public LoginFrame() {
        setTitle("Garment Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> performLogin());
    }

    private void performLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        User user = userDAO.authenticate(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getFullName() + " (" + user.getUserType() + ")");
            new MainDashboard(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials or inactive account.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
""",

    "src/main/java/com/garment/ui/MainDashboard.java": """package com.garment.ui;

import com.garment.model.User;

import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {
    private User currentUser;

    public MainDashboard(User user) {
        this.currentUser = user;
        setTitle("Garment Management System - Dashboard [" + user.getUserType() + "]");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customer Orders", createDummyPanel("Customer Orders Management"));
        tabbedPane.addTab("Production Tracking", createDummyPanel("Production Orders & Stages"));
        tabbedPane.addTab("Deliveries", createDummyPanel("Deliveries & Dispatch"));
        
        if ("PRODUCT_MANAGER".equals(user.getUserType())) {
            tabbedPane.addTab("User & Staff Admin", createDummyPanel("Staff & Product Manager Operations"));
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDummyPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
"""
}

# Write files directly inside the current folder
for file_path, content in files.items():
    full_path = os.path.join(project_root, file_path)
    with open(full_path, "w", encoding="utf-8") as f:
        f.write(content)

print("Project files generated directly in the current directory!")