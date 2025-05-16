package application.Java_files.Controller;

import application.Java_files.Model.Order;
import application.Java_files.Model.OrderItem;
import application.Java_files.DAO.OrderDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class orderConfirmationScreenController {

    @FXML private Label orderNumberLabel;
    @FXML private Label orderDateLabel;
    @FXML private TableView<OrderItem> orderSummaryTable;
    @FXML private TableColumn<OrderItem, String> titleColumn;
    @FXML private TableColumn<OrderItem, Integer> physicalCopiesColumn;
    @FXML private TableColumn<OrderItem, Integer> ebookCopiesColumn;
    @FXML private TableColumn<OrderItem, Double> priceColumn;
    @FXML private Label totalPriceLabel;
    @FXML private Button confirmOrderButton;
    @FXML private Button returnToDashboardButton;

    private Order order;
    private OrderDAO orderDAO;

    @FXML
    public void initialize() {
        // Initialize the table columns
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBookTitle()));
        physicalCopiesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPhysicalCopies()).asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getEbookCopies()).asObject());
        priceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalCost()).asObject());
        
        orderDAO = new OrderDAO();
    }

    public void setOrder(Order order) {
        this.order = order;
        displayOrderDetails();
    }

    private void displayOrderDetails() {
        orderNumberLabel.setText("Order #: " + order.getOrderId());
        orderDateLabel.setText("Date: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        orderSummaryTable.setItems(FXCollections.observableArrayList(order.getOrderItems()));
        totalPriceLabel.setText(String.format("Total: $%.2f", order.getTotalCost()));
    }

    @FXML
    private void handleConfirmOrder() {
        boolean success = orderDAO.saveOrder(order);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Order Confirmed", "Your order has been successfully placed and saved.");
            navigateToThankYouScreen();
        } else {
            showAlert(Alert.AlertType.ERROR, "Order Error", "There was an error processing your order. Please try again.");
        }
    }

    @FXML
    private void handleReturnToDashboard() {
        // Ask for confirmation before discarding the order
        boolean confirm = showConfirmationAlert("Cancel Order", "Are you sure you want to cancel this order and return to the dashboard?");
        if (confirm) {
            navigateToDashboard();
        }
    }

    private void navigateToThankYouScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/ThankYouScreen.fxml"));
            Parent thankYouRoot = loader.load();
            Scene thankYouScene = new Scene(thankYouRoot);
            
            Stage currentStage = (Stage) orderNumberLabel.getScene().getWindow();
            currentStage.setScene(thankYouScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the thank you screen. Your order has been placed successfully.");
        }
    }

    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            Scene dashboardScene = new Scene(dashboardRoot);
            
            Stage currentStage = (Stage) orderNumberLabel.getScene().getWindow();
            currentStage.setScene(dashboardScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the dashboard. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        return alert.showAndWait().orElse(buttonTypeNo) == buttonTypeYes;
    }

    // Optional: Method to update the UI elements (e.g., disable buttons after order confirmation)
    private void updateUIAfterConfirmation() {
        confirmOrderButton.setDisable(true);
        returnToDashboardButton.setText("Return to Dashboard");
    }

    // Optional: Method to refresh the order summary (in case of any changes)
    public void refreshOrderSummary() {
        if (order != null) {
            displayOrderDetails();
        }
    }
}