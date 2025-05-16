package application.Java_files.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;  // For handling SQL Date
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;  // For handling DatePicker value

public class adminRegistrationScreenController {

    @FXML
    private TextField adminUsernameField;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private PasswordField adminConfirmPasswordField;

    @FXML
    private TextField adminFirstNameField;

    @FXML
    private TextField adminLastNameField;

    @FXML
    private DatePicker adminDOBPicker;  // New DatePicker for DOB

    @FXML
    private Button adminRegisterButton;

    @FXML
    private Hyperlink adminBackToLoginLink;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Method to handle admin registration when the Register button is clicked
    @FXML
    private void handleAdminRegister() {
        String username = adminUsernameField.getText();
        String password = adminPasswordField.getText();
        String confirmPassword = adminConfirmPasswordField.getText();
        String firstName = adminFirstNameField.getText();
        String lastName = adminLastNameField.getText();
        LocalDate dob = adminDOBPicker.getValue();  // Get the selected DOB from DatePicker

        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob == null) {
            showAlert(AlertType.WARNING, "Registration Error", "All fields, including Date of Birth, are required.");
            return;
        }

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.WARNING, "Password Mismatch", "Passwords do not match.");
            return;
        }

        // Call the method to register the admin in the database
        if (registerAdmin(username, password, firstName, lastName, dob)) {
            showAlert(AlertType.INFORMATION, "Registration Successful", "Admin account created successfully.");
            navigateToAdminLogin();  // Navigate to the login page after successful registration
        } else {
            showAlert(AlertType.ERROR, "Registration Failed", "Unable to create admin account. Please try again.");
        }
    }

    // Method to register the admin in the database
    private boolean registerAdmin(String username, String password, String firstName, String lastName, LocalDate dob) {
        String query = "INSERT INTO Admins (username, password, firstName, lastName, dob) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);  // In a real application, password hashing should be applied here
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setDate(5, Date.valueOf(dob));  // Convert LocalDate to SQL Date

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Return true if the registration was successful

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Error registering admin: " + e.getMessage());
            return false;
        }
    }

    // Method to navigate back to the admin login page after registration
    @FXML
    private void handleAdminBackToLogin() {
        navigateToAdminLogin();  // Navigate back to the admin login screen
    }

    // Method to navigate back to the admin login page
    private void navigateToAdminLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/adminLoginPage.fxml"));
            Parent root = loader.load();
            adminLoginPageController loginController = loader.getController();
            loginController.setStage(stage);  // Pass the current stage to the login controller

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Navigation Error", "Error returning to the login page.");
        }
    }

    // Utility method to display alert messages
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
