package application.Java_files.Controller;

import application.Java_files.Model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class thankYouScreenController {

    @FXML private Label orderNumberLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label totalItemsLabel;
    @FXML private Label totalAmountLabel;

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
        displayOrderInfo();
    }

    private void displayOrderInfo() {
        if (order != null) {
            orderNumberLabel.setText("Order Number: " + order.getOrderId());
            orderDateLabel.setText("Order Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            totalItemsLabel.setText("Total Items: " + order.getTotalItems());
            totalAmountLabel.setText(String.format("Total Amount: $%.2f", order.getTotalCost()));
        }
    }

    @FXML
    private void handleViewOrderDetails() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/detailOrderView.fxml"));
            Parent root = loader.load();
            
            detailOrderViewController controller = loader.getController();
            controller.setOrder(order);
            
            Stage stage = new Stage();
            stage.setTitle("Order Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Unable to load order details. Please try again.");
        }
    }

    @FXML
    private void handleReturnToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            Scene dashboardScene = new Scene(dashboardRoot);
            
            Stage currentStage = (Stage) orderNumberLabel.getScene().getWindow();
            currentStage.setScene(dashboardScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an alert)
        }
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}