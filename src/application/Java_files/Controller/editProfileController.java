package application.Java_files.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class editProfileController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private DatePicker dobPicker;

    private String currentUserId;

    public void initializeUserData(String firstName, String lastName, String username, LocalDate dob, String userId) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        usernameField.setText(username);
        dobPicker.setValue(dob);
        this.currentUserId = userId;
    }

    @FXML
    private void handleUpdateProfile() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        LocalDate dob = dobPicker.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || dob == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        boolean success = updateUserProfile(firstName, lastName, username, dob);

        if (success) {
            showAlert("Success", "Profile updated successfully.");
        } else {
            showAlert("Error", "Failed to update profile.");
        }
    }

    @FXML
    private void handleBackToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/userDashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to return to dashboard.");
        }
    }

    private boolean updateUserProfile(String firstName, String lastName, String username, LocalDate dob) {
        String updateQuery = "UPDATE Users SET firstName = ?, lastName = ?, username = ?, dob = ? WHERE userId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setDate(4, java.sql.Date.valueOf(dob));
            pstmt.setString(5, currentUserId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}