package com.readingroom.controller;

import com.readingroom.model.Order;
import com.readingroom.model.OrderItem;
import com.readingroom.service.OrderManagementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class OrderDetailsController {

    @FXML private Label orderIdLabel;
    @FXML private Label customerNameLabel;
    @FXML private Label shippingAddressLabel;
    @FXML private Label paymentMethodLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label orderStatusLabel;
    @FXML private TableView<OrderItem> orderItemsTable;
    @FXML private TableColumn<OrderItem, String> bookTitleColumn;
    @FXML private TableColumn<OrderItem, Integer> physicalCopiesColumn;
    @FXML private TableColumn<OrderItem, Integer> ebookCopiesColumn;
    @FXML private TableColumn<OrderItem, Double> physicalPriceColumn;
    @FXML private TableColumn<OrderItem, Double> ebookPriceColumn;
    @FXML private TableColumn<OrderItem, Double> totalItemCostColumn;
    @FXML private Label totalPhysicalCopiesLabel;
    @FXML private Label totalEbookCopiesLabel;
    @FXML private Label totalCostLabel;

    private Order order;
    private OrderManagementService orderService;

    public void setOrder(Order order) {
        this.order = order;
        displayOrderDetails();
    }

    public void setOrderService(OrderManagementService orderService) {
        this.orderService = orderService;
    }

    @FXML
    private void initialize() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));
        ebookCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("ebookCopies"));
        physicalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("physicalPrice"));
        ebookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ebookPrice"));
        totalItemCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
    }

    private void displayOrderDetails() {
        orderIdLabel.setText(order.getOrderId());
        customerNameLabel.setText(order.getCustomerName());
        shippingAddressLabel.setText(order.getShippingAddress());
        paymentMethodLabel.setText(order.getPaymentMethod());
        orderDateLabel.setText(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        orderStatusLabel.setText(order.getOrderStatus());

        orderItemsTable.setItems(FXCollections.observableArrayList(order.getOrderItems()));

        totalPhysicalCopiesLabel.setText(String.valueOf(order.getTotalPhysicalCopies()));
        totalEbookCopiesLabel.setText(String.valueOf(order.getTotalEbookCopies()));
        totalCostLabel.setText(String.format("$%.2f", order.getTotalCost()));
    }

    @FXML
    private void handleUpdateStatus() {
        String newStatus = showStatusUpdateDialog();
        if (newStatus != null) {
            order.setOrderStatus(newStatus);
            orderService.updateOrderStatus(order.getOrderId(), newStatus);
            orderStatusLabel.setText(newStatus);
        }
    }

    private String showStatusUpdateDialog() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Pending", "Processing", "Shipped", "Delivered", "Cancelled");
        dialog.setTitle("Update Order Status");
        dialog.setHeaderText("Select new status for the order");
        dialog.setContentText("Status:");

        return dialog.showAndWait().orElse(null);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) orderIdLabel.getScene().getWindow();
        stage.close();
    }
}