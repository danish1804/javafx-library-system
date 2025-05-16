package application.Java_files.Controller;

import application.Java_files.Model.user;
import application.Java_files.Model.ReadingRoom;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class userLoginScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter your username and password.");
            return;
        }

        // Check credentials using ReadingRoom logic
        user user = ReadingRoom.authenticateUser(username, password);
        if (user != null) {
            showAlert("Success", "Login successful. Welcome, " + user.getFirstName() + "!");
            // Proceed to the next screen (e.g., user dashboard)
        } else {
            showAlert("Error", "Invalid username or password.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToRegister() {
        // Logic to navigate to the registration screen
    }
}
