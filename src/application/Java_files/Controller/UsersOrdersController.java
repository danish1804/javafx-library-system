package com.readingroom.controller;

import com.readingroom.model.User;
import com.readingroom.model.Order;
import com.readingroom.service.OrderManagementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserOrdersController {

    @FXML private Label titleLabel;
    @FXML private TableView<Order> orderTableView;
    @FXML private TableColumn<Order, String> orderIdColumn;
    @FXML private TableColumn<Order, String> orderDateColumn;
    @FXML private TableColumn<Order, Double> totalCostColumn;
    @FXML private TableColumn<Order, String> orderStatusColumn;

    private User user;
    private OrderManagementService orderService;

    @FXML
    private void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return cellData.getValue().getOrderDate().format(formatter);
        });
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
    }

    public void setUser(User user) {
        this.user = user;
        titleLabel.setText("Orders for " + user.getUsername());
    }

    public void setOrderService(OrderManagementService orderService) {
        this.orderService = orderService;
    }

    public void loadOrders() {
        List<Order> userOrders = orderService.getOrdersByUserId(user.getUserId());
        orderTableView.setItems(FXCollections.observableArrayList(userOrders));
    }

    @FXML
    private void handleViewOrderDetails() {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to view details.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/OrderDetailsView.fxml"));
            Parent root = loader.load();
            
            OrderDetailsController controller = loader.getController();
            controller.setOrder(selectedOrder);
            controller.setOrderService(orderService);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Order Details - " + selectedOrder.getOrderId());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load order details view.");
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}