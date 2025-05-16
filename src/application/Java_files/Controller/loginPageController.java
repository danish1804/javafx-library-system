package application.Java_files.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class loginPageController {

    @FXML
    private Button userLoginButton;

    @FXML
    private Button adminLoginButton;

    private Stage stage; // Reference to the current stage

    // Handle User Login Page navigation
    @FXML
    private void handleUserLoginPage() {
        try {
            // Load the user login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/application/Java_files/View/userLogin.fxml"));
            Scene userLoginScene = new Scene(loader.load());
            
            // Set the new scene for the current stage
            stage = (Stage) userLoginButton.getScene().getWindow();
            stage.setScene(userLoginScene);
            stage.setTitle("User Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Admin Login Page navigation
    @FXML
    private void handleAdminLoginPage() {
        try {
            // Load the admin login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/application/Java_files/View/adminLogin.fxml"));
            Scene adminLoginScene = new Scene(loader.load());
            
            // Set the new scene for the current stage
            stage = (Stage) adminLoginButton.getScene().getWindow();
            stage.setScene(adminLoginScene);
            stage.setTitle("Admin Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
