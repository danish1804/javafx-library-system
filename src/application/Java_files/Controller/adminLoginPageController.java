package application.Java_files.Controller;

import application.Java_files.Model.*;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class adminLoginPageController {

    @FXML
    private TextField adminUsernameField;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private Button adminLoginButton;
    
    @FXML
    private Hyperlink adminRegisterLink;
    
    @FXML
    private Hyperlink backToChoiceLink;


    private Stage stage;

    // Method to authenticate the admin using the database
    private boolean authenticateAdmin(String username, String password) {
        String query = "SELECT * FROM Admins WHERE username = ? AND password = ?";  // Basic validation (use hashed passwords in a real app)
        try (Connection conn = DatabaseManager.getConnection();  // Ensure DatabaseManager connects to your SQLite DB
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Authentication succeeds if a result is found
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Error connecting to the database.");
            return false;
        }
    }

    // Handle admin login button action
    @FXML
    private void handleAdminLogin() {
        String username = adminUsernameField.getText();
        String password = adminPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Login Error", "Please enter both username and password.");
            return;
        }

        if (authenticateAdmin(username, password)) {
            loadAdminDashboard(username, "admin@example.com", password);  // Navigate to Admin Dashboard
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    // Method to load the Admin Dashboard upon successful login
    private void loadAdminDashboard(String username, String email, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/adminDashboard.fxml"));
            Parent root = loader.load();

            // Create an Admin object using the logged-in admin's details
            admin loggedInAdmin = new admin(username, "Admin Name", email, password);

            // Get the controller for the Admin Dashboard
            adminDashboardController dashboardController = loader.getController();
            dashboardController.setAdmin(loggedInAdmin);  // Pass the admin object to the dashboard

            // Set up the stage and show the Admin Dashboard
            stage = (Stage) adminLoginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Error loading admin dashboard.");
        }
    }
    
    @FXML
    private void handleBackToChoice() {
        try {
            // Load the choice screen (e.g., main menu or selection screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/choiceScreen.fxml"));
            Parent root = loader.load();

            // Get the stage and set the new scene (Choice Screen)
            Stage stage = (Stage) backToChoiceLink.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Choice Screen");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Error loading Choice Screen.");
        }
    }
    
    @FXML
    private void handleAdminRegisterLink() {
        try {
            // Load the Admin Registration page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/adminRegistrationScreen.fxml"));
            Parent root = loader.load();

            // Get the stage and set the new scene (Admin Registration)
            Stage stage = (Stage) adminRegisterLink.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Registration");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Error loading Admin Registration screen.");
        }
    }

    // Utility method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
