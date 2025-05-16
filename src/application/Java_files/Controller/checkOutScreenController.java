package application.Java_files.Controller;



import java.io.IOException;
import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
import java.util.UUID;

import application.Java_files.Model.Order;
import application.Java_files.Model.OrderItem;
import application.Java_files.Model.shoppingCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class checkOutScreenController {

    @FXML private Label orderIdLabel;
    @FXML private TableView<OrderItem> orderSummaryTable;
    @FXML private TableColumn<OrderItem, String> titleColumn;
    @FXML private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML private TableColumn<OrderItem, Double> priceColumn;
    @FXML private Label totalPriceLabel;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField postalCodeField;
    @FXML private RadioButton creditCardRadio;
    @FXML private RadioButton paypalRadio;

    private shoppingCart shoppingCart;
    private String orderId;

    @FXML
    public void initialize() {
        orderId = generateOrderId();
        orderIdLabel.setText("Order #: " + orderId);

        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBookTitle()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getTotalQuantity()).asObject());
        priceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalCost()).asObject());
    }

    public void setShoppingCart(shoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        ObservableList<OrderItem> items = FXCollections.observableArrayList(shoppingCart.getItems());
        orderSummaryTable.setItems(items);
        totalPriceLabel.setText(String.format("Total: $%.2f", shoppingCart.getTotalCost()));
    }
    @FXML
    private void handleBackToCart() {
        // Implement logic to go back to cart view
    }

    @FXML
    private void handlePlaceOrder() {
    	 if (validateFields()) {
             Order order = createOrder();
             navigateToOrderConfirmation(order);
         }
    }

    private boolean validateFields() {
        StringBuilder errorMessage = new StringBuilder();

        if (nameField.getText().trim().isEmpty()) {
            errorMessage.append("Please enter your full name.\n");
        }

        if (addressField.getText().trim().isEmpty()) {
            errorMessage.append("Please enter your address.\n");
        }

        if (cityField.getText().trim().isEmpty()) {
            errorMessage.append("Please enter your city.\n");
        }

        if (countryField.getText().trim().isEmpty()) {
            errorMessage.append("Please enter your country.\n");
        }

        if (postalCodeField.getText().trim().isEmpty()) {
            errorMessage.append("Please enter your postal code.\n");
        }

        if (!creditCardRadio.isSelected() && !paypalRadio.isSelected()) {
            errorMessage.append("Please select a payment method.\n");
        }

        if (errorMessage.length() > 0) {
            showAlert("Validation Error", errorMessage.toString());
            return false;
        }

        return true;
    }


    private Order createOrder() {
        String shippingAddress = String.format("%s, %s, %s, %s", 
                                               addressField.getText().trim(), 
                                               cityField.getText().trim(), 
                                               countryField.getText().trim(), 
                                               postalCodeField.getText().trim());
        String paymentMethod = creditCardRadio.isSelected() ? "Credit Card" : "PayPal";
        
        return new Order(
                orderId,
                nameField.getText().trim(),
                shippingAddress,
                paymentMethod,
                shoppingCart.getItems(),
                shoppingCart.getTotalItems(),
                shoppingCart.getTotalPhysicalCopies(),
                shoppingCart.getTotalEbookCopies(),
                shoppingCart.getTotalCost(),// Pass the entire ShoppingCart object
                LocalDateTime.now()
            );
    }
    
    private void navigateToOrderConfirmation(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/application/Resources/Views/UserViews/orderConfirmationScreen.fxml"));
            Parent root = loader.load();

            orderConfirmationScreenController controller = loader.getController();
            if (controller == null) {
                throw new RuntimeException("Controller not found for orderConfirmationScreen.fxml");
            }
            controller.setOrder(order);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the order confirmation screen.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            showAlert("Error", "Controller issue: " + e.getMessage());
        }
        
    }

    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}