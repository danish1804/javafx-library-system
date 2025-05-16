package application.Java_files.Controller;

import application.Java_files.Model.user;
import application.Java_files.Model.ReadingRoom;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class userRegistrationScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private void handleRegisterUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate dob = dobPicker.getValue();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dob == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        // Save the new user to the database using the ReadingRoom logic
        user newUser = new user(username, password, firstName, lastName, dob);
        if (ReadingRoom.registerUser(newUser)) {
            showAlert("Success", "User registered successfully!");
            // Optionally, redirect to login screen
        } else {
            showAlert("Error", "Registration failed. Try a different username.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToLogin() {
        // Logic to navigate back to the login screen
    }
}
