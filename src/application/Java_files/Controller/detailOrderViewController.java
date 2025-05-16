package application.Java_files.Controller;

import application.Java_files.Model.Order;
import application.Java_files.Model.OrderItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class detailOrderViewController {

    @FXML private Label orderNumberLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label shippingAddressLabel;
    @FXML private Label paymentMethodLabel;
    @FXML private TableView<OrderItem> orderedItemsTable;
    @FXML private TableColumn<OrderItem, String> titleColumn;
    @FXML private TableColumn<OrderItem, Integer> physicalCopiesColumn;
    @FXML private TableColumn<OrderItem, Integer> ebookCopiesColumn;
    @FXML private TableColumn<OrderItem, Double> priceColumn;
    @FXML private Label totalAmountLabel;

    private Order order;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBookTitle()));
        physicalCopiesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPhysicalCopies()).asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getEbookCopies()).asObject());
        priceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalCost()).asObject());
    }

    public void setOrder(Order order) {
        this.order = order;
        displayOrderDetails();
    }

    private void displayOrderDetails() {
        if (order != null) {
            orderNumberLabel.setText(order.getOrderId());
            orderDateLabel.setText(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            customerNameLabel.setText(order.getCustomerName());
            shippingAddressLabel.setText(order.getShippingAddress());
            paymentMethodLabel.setText(order.getPaymentMethod());

            orderedItemsTable.setItems(FXCollections.observableArrayList(order.getOrderItems()));
            totalAmountLabel.setText(String.format("$%.2f", order.getTotalCost()));
        }
    }

    @FXML
    private void handlePrintOrder() {
        // Implement print functionality
        System.out.println("Printing order: " + order.getOrderId());
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
            showAlert("Navigation Error", "Unable to return to dashboard. Please try again.");
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